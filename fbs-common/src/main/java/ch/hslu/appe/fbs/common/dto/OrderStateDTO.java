package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.util.Objects;

public final class OrderStateDTO implements Serializable {
    private final int id;
    private final String orderState;

    public OrderStateDTO(int id, String orderState) {
        this.id = id;
        this.orderState = orderState;
    }

    public int getId() {
        return this.id;
    }

    public String getOrderState() {
        return this.orderState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStateDTO)) return false;
        OrderStateDTO orderStateDTO = (OrderStateDTO) o;
        return Objects.equals(this.orderState, orderStateDTO.orderState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.orderState);
    }
}
