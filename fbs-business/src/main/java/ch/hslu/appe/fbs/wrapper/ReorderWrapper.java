package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.model.db.Reorder;

public final class ReorderWrapper implements Wrappable<Reorder, ReorderDTO> {
    private final ItemWrapper itemWrapper;

    public ReorderWrapper() {
        this.itemWrapper = new ItemWrapper();
    }

    @Override
    public ReorderDTO dtoFromEntity(Reorder reorder) {
        return new ReorderDTO(
                reorder.getId(),
                itemWrapper.dtoFromEntity(reorder.getItemByItemId()),
                reorder.getQuantity(),
                reorder.getReorderDate(),
                reorder.getDelivered()
        );
    }

    @Override
    public Reorder entityFromDTO(ReorderDTO reorderDTO) {
        final Reorder reorder = new Reorder();
        if (reorderDTO.getId() != -1) {
            reorder.setId(reorderDTO.getId());
        }
        reorder.setItemId(reorderDTO.getItem().getId());
        reorder.setDelivered(reorderDTO.getDelivered());
        reorder.setReorderDate(reorderDTO.getReorderDate());
        reorder.setQuantity(reorderDTO.getQuantity());
        return reorder;
    }
}
