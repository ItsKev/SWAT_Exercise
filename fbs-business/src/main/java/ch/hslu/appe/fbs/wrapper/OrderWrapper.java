package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.OrderDTO;
import ch.hslu.appe.fbs.common.dto.OrderItemDTO;
import ch.hslu.appe.fbs.model.db.Order;

import java.util.ArrayList;
import java.util.Collection;

public final class OrderWrapper implements Wrappable<Order, OrderDTO> {

    private final CustomerWrapper customerWrapper;
    private final UserWrapper userWrapper;
    private final OrderStateWrapper orderStateWrapper;
    private final OrderItemWrapper orderItemWrapper;

    public OrderWrapper() {
        this.customerWrapper = new CustomerWrapper();
        this.userWrapper = new UserWrapper();
        this.orderStateWrapper = new OrderStateWrapper();
        this.orderItemWrapper = new OrderItemWrapper();
    }

    @Override
    public OrderDTO dtoFromEntity(Order order) {
        final Collection<OrderItemDTO> orderItemDTOCollection = new ArrayList<>();
        order.getOrderItemsById().forEach(orderItem -> orderItemDTOCollection.add(this.orderItemWrapper.dtoFromEntity(orderItem)));
        return new OrderDTO(
                order.getId(),
                this.customerWrapper.dtoFromEntity(order.getCustomerByCustomerId()),
                this.userWrapper.dtoFromEntity(order.getUserByUserId()),
                this.orderStateWrapper.dtoFromEntity(order.getOrderStateByOrderState()),
                order.getDateTime(),
                orderItemDTOCollection
        );
    }

    @Override
    public Order entityFromDTO(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        final Order order = new Order();
        if (orderDTO.getId() != -1) {
            order.setId(orderDTO.getId());
        }
        order.setUserId(orderDTO.getUser().getId());
        order.setCustomerId(orderDTO.getCustomer().getId());
        order.setOrderState(orderDTO.getOrderState().getId());
        order.setDateTime(orderDTO.getDateTime());
        return order;
    }
}
