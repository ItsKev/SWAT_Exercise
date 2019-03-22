package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public final class ReorderDTO implements Serializable {
    private final int id;
    private final ItemDTO item;
    private final int quantity;
    private final Timestamp reorderDate;
    private final Timestamp delivered;

    public ReorderDTO(int id, ItemDTO item, int quantity, Timestamp reorderDate, Timestamp delivered) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.reorderDate = reorderDate;
        this.delivered = delivered;
    }

    public int getId() {
        return this.id;
    }

    public ItemDTO getItem() {
        return this.item;
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
        return Objects.equals(this.item, reorderDTO.item) &&
                Objects.equals(this.quantity, reorderDTO.quantity) &&
                Objects.equals(this.reorderDate, reorderDTO.reorderDate) &&
                Objects.equals(this.delivered, reorderDTO.delivered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.item, this.quantity, this.reorderDate, this.delivered);
    }
}
