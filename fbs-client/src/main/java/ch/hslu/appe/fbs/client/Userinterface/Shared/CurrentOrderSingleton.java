package ch.hslu.appe.fbs.client.Userinterface.Shared;

import ch.hslu.appe.fbs.client.UiModels.UiOrder;

public class CurrentOrderSingleton {
    private static UiOrder currentOrder;

    public static void setCurrentOrder(UiOrder orderToSet) {
        currentOrder = orderToSet;
    }

    public static UiOrder getCurrentOrder() {
        return currentOrder;
    }

    public static void createNewOrder() {
        currentOrder = new UiOrder();
    }
}
