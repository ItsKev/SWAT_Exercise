package ch.hslu.appe.fbs.data.order;

public final class OrderPersistorFactory {

    private OrderPersistorFactory() {
    }

    public static OrderPersistor createOrderPersistor() {
        return new OrderPersistorJpa();
    }
}
