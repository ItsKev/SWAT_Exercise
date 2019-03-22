package ch.hslu.appe.fbs.business.item;

import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.business.stock.StockException;

import java.util.List;

public interface ItemManager {

    /**
     * Get all items
     *
     * @param userDTO The user which initiated this action
     * @return all items in the database
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    List<ItemDTO> getAllItems(UserDTO userDTO) throws UserNotAuthorisedException;

    /**
     * Updates the item stock
     *
     * @param itemDTO  The item whose stock needs to be updated
     * @param quantity The quantity specified in the corresponding order
     */
    void updateItemStock(ItemDTO itemDTO, int quantity);

    /**
     * Updates the item's minimum local stock (minLocalStock)
     *
     * @param itemDTO          The item whose minLocalStock needs to be updated
     * @param newMinLocalStock The new size of the minLocalStock
     */
    void updateMinLocalStock(ItemDTO itemDTO, int newMinLocalStock);

    /**
     * Get local (available) stock of item
     *
     * @param id The id of the item whose local (available) stock needs to be returned
     * @return The local (available) stock of the specified item
     * @throws IllegalArgumentException If the specified id doesn't represent a valid item
     */
    int getAvailableItemQuantity(int id);

    /**
     * Refills the item stock
     *
     * @param itemDTO  The item whose stock needs to be refilled
     * @param quantity The specified quantity
     */
    void refillItemStock(ItemDTO itemDTO, int quantity);

    /***
     * Get item by id.
     * @param itemId The item
     * @return The item
     */
    ItemDTO getItem(final int itemId);

    /**
     * Reorders an Item
     * @param itemId Id of the Item
     * @param quantity Quantity to Reorder
     */
    public void reorderItem(int itemId, int quantity) throws StockException;
}
