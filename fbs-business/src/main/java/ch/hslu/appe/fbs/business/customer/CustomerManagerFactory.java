package ch.hslu.appe.fbs.business.customer;

public class CustomerManagerFactory {

    private CustomerManagerFactory() {
    }

    public static CustomerManager getCustomerManager() {
        return new CustomerManagerImpl();
    }

}
