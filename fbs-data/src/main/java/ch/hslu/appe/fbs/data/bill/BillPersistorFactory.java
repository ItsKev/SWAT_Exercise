package ch.hslu.appe.fbs.data.bill;

public final class BillPersistorFactory {

    private BillPersistorFactory() {
    }

    public static BillPersistor createBillPersistor() {
        return new BillPersistorJpa();
    }
}
