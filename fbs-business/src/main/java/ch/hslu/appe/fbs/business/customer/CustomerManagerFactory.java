package ch.hslu.appe.fbs.business.customer;

import ch.hslu.appe.fbs.data.customer.CustomerPersistorFactory;

public class CustomerManagerFactory {

    private CustomerManagerFactory() {
    }

    public static CustomerManager getCustomerManager() {
        return new CustomerManagerImpl(CustomerPersistorFactory.createCustomerPersistor());
    }

}
