package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.OrderStateDTO;
import ch.hslu.appe.fbs.model.db.OrderState;

public final class OrderStateWrapper implements Wrappable<OrderState, OrderStateDTO> {
    @Override
    public OrderStateDTO dtoFromEntity(OrderState orderState) {
        return new OrderStateDTO(
                orderState.getId(),
                orderState.getState()
        );
    }

    @Override
    public OrderState entityFromDTO(OrderStateDTO orderStateDTO) {
        final OrderState orderState = new OrderState();
        if (orderStateDTO.getId() != -1) {
            orderState.setId(orderState.getId());
        }
        orderState.setState(orderStateDTO.getOrderState());
        return orderState;
    }
}
