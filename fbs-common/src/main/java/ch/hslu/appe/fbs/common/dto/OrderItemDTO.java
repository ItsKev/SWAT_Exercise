package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.util.Objects;

public final class OrderItemDTO implements Serializable {
    private final Integer price;
    private final Integer quantity;
    private final ItemDTO itemByItemId;

    public OrderItemDTO(Integer price, Integer quantity, ItemDTO itemByItemId) {
        this.price = price;
        this.quantity = quantity;
        this.itemByItemId = itemByItemId;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public ItemDTO getItemByItemId() {
        return this.itemByItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemDTO)) return false;
        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        return Objects.equals(this.price, orderItemDTO.price) &&
                Objects.equals(this.quantity, orderItemDTO.quantity) &&
                Objects.equals(this.itemByItemId, orderItemDTO.itemByItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.price, this.quantity, this.itemByItemId);
    }
}
