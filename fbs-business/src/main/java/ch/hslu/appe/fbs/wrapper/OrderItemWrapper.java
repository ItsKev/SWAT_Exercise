package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.OrderItemDTO;
import ch.hslu.appe.fbs.model.db.OrderItem;

public final class OrderItemWrapper implements Wrappable<OrderItem, OrderItemDTO> {

    private final ItemWrapper itemWrapper;

    public OrderItemWrapper() {
        this.itemWrapper = new ItemWrapper();
    }

    @Override
    public OrderItemDTO dtoFromEntity(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getPrice(),
                orderItem.getQuantity(),
                this.itemWrapper.dtoFromEntity(orderItem.getItemByItemId())
        );
    }

    @Override
    public OrderItem entityFromDTO(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        final OrderItem orderItem = new OrderItem();
        orderItem.setItemId(orderItemDTO.getItemByItemId().getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        return orderItem;
    }
}
