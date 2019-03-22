package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

public final class OrderDTO implements Serializable {
    private final int id;
    private final CustomerDTO customer;
    private final UserDTO user;
    private final OrderStateDTO orderState;
    private final Timestamp dateTime;
    private final Collection<OrderItemDTO> orderItems;

    public OrderDTO(int id, CustomerDTO customer, UserDTO user, OrderStateDTO orderState, Timestamp dateTime, Collection<OrderItemDTO> orderItems) {
        this.id = id;
        this.customer = customer;
        this.user = user;
        this.orderState = orderState;
        this.dateTime = dateTime;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public CustomerDTO getCustomer() {
        return this.customer;
    }

    public UserDTO getUser() {
        return this.user;
    }

    public OrderStateDTO getOrderState() {
        return this.orderState;
    }

    public Timestamp getDateTime() {
        return this.dateTime;
    }

    public Collection<OrderItemDTO> getOrderItems() {
        return this.orderItems;
    }

    @Override
    public String toString() {
        return "Customer: " + this.customer.toString() + ", OrderState: " + this.orderState.getOrderState() + ", Timestamp: " + this.dateTime.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(this.customer, orderDTO.customer) &&
                Objects.equals(this.user, orderDTO.user) &&
                Objects.equals(this.orderState, orderDTO.orderState) &&
                Objects.equals(this.dateTime, orderDTO.dateTime) &&
                Objects.equals(this.orderItems, orderDTO.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.customer, this.user, this.orderState, this.dateTime, this.orderItems);
    }
}
