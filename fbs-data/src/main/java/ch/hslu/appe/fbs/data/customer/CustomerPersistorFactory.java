package ch.hslu.appe.fbs.data.customer;

public final class CustomerPersistorFactory {

    private CustomerPersistorFactory() {
    }

    public static CustomerPersistor createCustomerPersistor() {
        return new CustomerPersistorJpa();
    }
}
