package ch.hslu.appe.fbs.business.bill;

public final class BillManagerFactory {

    private BillManagerFactory() {
    }

    public static BillManager getBillManager() {
        return new BillManagerImpl();
    }
}
