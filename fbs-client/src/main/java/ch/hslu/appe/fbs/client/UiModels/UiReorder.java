package ch.hslu.appe.fbs.client.UiModels;

import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.ReorderDTO;

import java.sql.Timestamp;
import java.util.Objects;

public class UiReorder {private final int id;
    private final ItemDTO item;
    private final int quantity;
    private final Timestamp reorderDate;
    private Timestamp delivered;

    public UiReorder(ReorderDTO reorderDTO) {
        this.id = reorderDTO.getId();
        this.item = reorderDTO.getItem();
        this.quantity = reorderDTO.getQuantity();
        this.reorderDate = reorderDTO.getReorderDate();
        this.delivered = reorderDTO.getDelivered();
    }

    public int getId() {
        return this.id;
    }

    public String getItem() {
        return this.item.getName();
    }

    public int getQuantity() {
        return this.quantity;
    }

    public Timestamp getDelivered() {
        return this.delivered;
    }

    public Timestamp getReorderDate() {
        return reorderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReorderDTO)) return false;
        ReorderDTO reorderDTO = (ReorderDTO) o;
        return Objects.equals(this.item, reorderDTO.getItem()) &&
                Objects.equals(this.quantity, reorderDTO.getQuantity()) &&
                Objects.equals(this.reorderDate, reorderDTO.getReorderDate()) &&
                Objects.equals(this.delivered, reorderDTO.getDelivered());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.item, this.quantity, this.reorderDate, this.delivered);
    }
}
