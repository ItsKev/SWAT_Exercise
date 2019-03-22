package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.data.order.OrderPersistor;
import ch.hslu.appe.fbs.data.order.OrderPersistorFactory;
import ch.hslu.appe.fbs.model.db.Order;

import java.util.List;

public final class SampleQuery {

    public static void main(String[] args) {
        final OrderPersistor orderPersistor = OrderPersistorFactory.createOrderPersistor();
        List<Order> orders = orderPersistor.getByCustomer(13);
        orders.forEach(order -> {
            System.out.println(order.getId());
            System.out.println(order.getCustomerByCustomerId().getPrename());
            System.out.println(order.getOrderStateByOrderState().getState());
            System.out.println(order.getUserByUserId().getUserName());
        });
    }
}
