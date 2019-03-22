package ch.hslu.appe.fbs.business.order;

public class OrderManagerFactory {

    private OrderManagerFactory() {
    }

    public static OrderManager getOrderManager() {
        return new OrderManagerImpl();
    }

}
