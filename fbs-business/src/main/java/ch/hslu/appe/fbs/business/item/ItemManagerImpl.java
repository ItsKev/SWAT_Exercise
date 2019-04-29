package ch.hslu.appe.fbs.business.item;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationManager;
import ch.hslu.appe.fbs.business.logger.Logger;
import ch.hslu.appe.fbs.business.stock.Stock;
import ch.hslu.appe.fbs.business.stock.StockException;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.item.ItemPersistor;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistor;
import ch.hslu.appe.fbs.model.db.Item;
import ch.hslu.appe.fbs.model.db.Reorder;
import ch.hslu.appe.fbs.wrapper.ItemWrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ItemManagerImpl implements ItemManager {

    private static final ReentrantLock lock = new ReentrantLock();

    private static final String ITEM_NOT_FOUND = "Item not found!";

    private final ItemPersistor itemPersistor;
    private final ItemWrapper itemWrapper;
    private final ReorderPersistor reorderPersistor;
    private final Stock centralStock;

    private final AuthorisationManager authorisationManager;

    public ItemManagerImpl(ItemPersistor itemPersistor, ReorderPersistor reorderPersistor, Stock centralStock) {
        this.itemPersistor = itemPersistor;
        this.reorderPersistor = reorderPersistor;
        this.itemWrapper = new ItemWrapper();
        this.centralStock = centralStock;
        this.authorisationManager = new AuthorisationManager();
    }


    @Override
    public List<ItemDTO> getAllItems(UserDTO userDTO) throws UserNotAuthorisedException {
        authorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ALL_ITEMS);
        List<ItemDTO> itemDTOS = new ArrayList<>();
        lock.lock();
        try {
            this.itemPersistor.getAllItems().forEach(item -> itemDTOS.add(itemWrapper.dtoFromEntity(item)));
        } finally {
            lock.unlock();
        }
        return itemDTOS;
    }

    @Override
    public void updateItemStock(ItemDTO itemDTO, int quantity) {
        final Item item = this.itemWrapper.entityFromDTO(itemDTO);
        int stock = item.getLocalStock() - quantity;
        int virtualStock = item.getVirtualLocalStock() - quantity;
        if (stock < 0) {
            stock = 0;
        }
        item.setLocalStock(stock);
        item.setVirtualLocalStock(virtualStock);
        lock.lock();
        try {
            this.itemPersistor.updateStock(item);
            reorderCheck(item.getId());
        } finally {
            lock.unlock();
        }
        Logger.logInfo("", "Updated Item Stock of Item: " + item.getArtNr() + " to " + item.getLocalStock());
    }

    @Override
    public void updateMinLocalStock(ItemDTO itemDTO, int newMinLocalStock) {
        if (newMinLocalStock < 1) {
            throw new IllegalArgumentException("minimum local stock has to be >= 1");
        }
        final Item item = this.itemWrapper.entityFromDTO(itemDTO);
        item.setMinLocalStock(newMinLocalStock);
        lock.lock();
        try {
            this.itemPersistor.updateStock(item);
            Logger.logInfo("", "Updated MinLocalStock of Item: " + item.getArtNr() + " to " + item.getMinLocalStock());
            if (newMinLocalStock > item.getLocalStock()) {
                reorderCheck(item.getId());
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getAvailableItemQuantity(int id) {
        Optional<Item> item = Optional.empty();
        lock.lock();
        try {
            item = this.itemPersistor.getItemById(id);
        } finally {
            lock.unlock();
        }
        if (item.isPresent()) {
            return item.get().getLocalStock();
        }
        throw new IllegalArgumentException(ITEM_NOT_FOUND);
    }

    @Override
    public void refillItemStock(ItemDTO itemDTO, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity has to be >= 1");
        }
        lock.lock();
        try {
            Optional<Item> itemById = this.itemPersistor.getItemById(itemDTO.getId());
            if (itemById.isPresent()) {
                final Item itemFromDB = itemById.get();
                final int virtualStock = itemFromDB.getVirtualLocalStock() + quantity;
                int stock = 0;
                if (virtualStock >= 0) {
                    stock = virtualStock;
                }
                itemFromDB.setLocalStock(stock);
                itemFromDB.setVirtualLocalStock(virtualStock);
                this.itemPersistor.updateStock(itemFromDB);
                Logger.logInfo("", "Refilled Item Stock of Item: " + itemDTO.getArtNr() + " to " + itemFromDB.getLocalStock());
            } else {
                throw new IllegalArgumentException(ITEM_NOT_FOUND);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ItemDTO getItem(int itemId) {
        Optional<Item> itemById = this.itemPersistor.getItemById(itemId);
        if (itemById.isPresent()) {
            return this.itemWrapper.dtoFromEntity(itemById.get());
        }
        throw new IllegalArgumentException(ITEM_NOT_FOUND);
    }

    @Override
    public void reorderItem(int itemId, int quantity) throws StockException {
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity has to be >= 1");
        }
        lock.lock();
        try {
            final Optional<Item> item = this.itemPersistor.getItemById(itemId);
            if (item.isPresent()) {
                final Timestamp now = new Timestamp(new Date().getTime());
                final Reorder reorder = new Reorder();
                reorder.setItemId(itemId);
                reorder.setQuantity(quantity);
                reorder.setReorderDate(now);
                this.reorderPersistor.save(reorder);
                this.centralStock.orderItem(item.get().getArtNr(), quantity);
            }
        } finally {
            lock.unlock();
        }
    }

    private void reorderCheck(int itemId) {
        Optional<Item> optItem = this.itemPersistor.getItemById(itemId);
        if (optItem.isPresent()) {
            Item item = optItem.get();
            List<Reorder> reorders = reorderPersistor.getByItemId(itemId);
            int reorderedQuantity = 0;
            for (Reorder reorder : reorders) {
                if (reorder.getDelivered() == null) {
                    reorderedQuantity += reorder.getQuantity();
                }
            }
            int reorderQuantity = item.getMinLocalStock() - (item.getVirtualLocalStock() + reorderedQuantity);
            if (reorderQuantity > 0) {
                try {
                    this.reorderItem(itemId, reorderQuantity);
                } catch (StockException e) {
                    Logger.logError("Unknown User", e.getMessage());
                }
            }
        }
    }
}
