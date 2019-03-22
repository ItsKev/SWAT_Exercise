package ch.hslu.appe.fbs.data.orderstate;

public final class OrderStatePersistorFactory {

    private OrderStatePersistorFactory() {
    }

    public static OrderStatePersistor createOrderStatePersistor() {
        return new OrderStatePersistorJpa();
    }
}
