package ch.hslu.appe.fbs.data.orderitem;

public final class OrderItemPersistorFactory {

    private OrderItemPersistorFactory() {
    }

    public static OrderItemPersistor createOrderItemPersistor() {
        return new OrderItemPersistorJpa();
    }
}
