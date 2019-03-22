package ch.hslu.appe.fbs.common.rmi;

public class RmiLookupTable {
    private static final String USER_SERVICE_NAME = "UserService";
    private static final String CUSTOMER_SERVICE_NAME = "CustomerService";
    private static final String ITEM_SERVICE_NAME = "ItemService";
    private static final String REORDER_SERVICE_NAME = "ReorderService";

    public static String getUserServiceName() {
        return USER_SERVICE_NAME;
    }

    public static String getCustomerServiceName() {
        return CUSTOMER_SERVICE_NAME;
    }

    public static String getItemServiceName() {
        return ITEM_SERVICE_NAME;
    }

    public static String getReorderServiceName() {
        return REORDER_SERVICE_NAME;
    }
}
