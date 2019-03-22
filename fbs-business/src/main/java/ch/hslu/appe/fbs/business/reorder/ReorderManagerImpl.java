package ch.hslu.appe.fbs.business.reorder;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationManager;
import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.business.item.ItemManagerFactory;
import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistor;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistorFactory;
import ch.hslu.appe.fbs.model.db.Reorder;
import ch.hslu.appe.fbs.wrapper.ItemWrapper;
import ch.hslu.appe.fbs.wrapper.ReorderWrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public final class ReorderManagerImpl implements ReorderManager {

    private static final Object LOCK = new Object();

    private final ReorderPersistor reorderPersistor;
    private final ReorderWrapper reorderWrapper;
    private final ItemManager itemManager;
    private final ItemWrapper itemWrapper;

    public ReorderManagerImpl() {
        this.reorderPersistor = ReorderPersistorFactory.createReorderPersistor();
        this.reorderWrapper = new ReorderWrapper();
        this.itemManager = ItemManagerFactory.getItemManager();
        this.itemWrapper = new ItemWrapper();
    }

    @Override
    public void markReorderAsDelivered(final int reorderId, final UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.MARK_REORDER_DELIVERED);
        synchronized (LOCK) {
            Optional<Reorder> optionalReorder = this.reorderPersistor.getById(reorderId);
            if (optionalReorder.isPresent()) {
                final Reorder reorder = optionalReorder.get();
                reorder.setDelivered(new Timestamp(new Date().getTime()));
                this.itemManager.refillItemStock(this.itemWrapper.dtoFromEntity(reorder.getItemByItemId()),
                        reorder.getQuantity());
                this.reorderPersistor.save(reorder);
            }
        }
    }

    @Override
    public List<ReorderDTO> getAllReorders(final UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ALL_REORDERS);
        synchronized (LOCK) {
            List<ReorderDTO> reorders = new ArrayList<>();
            this.reorderPersistor.getAll().forEach(reorder -> reorders.add(this.reorderWrapper.dtoFromEntity(reorder)));
            return reorders;
        }
    }


}
