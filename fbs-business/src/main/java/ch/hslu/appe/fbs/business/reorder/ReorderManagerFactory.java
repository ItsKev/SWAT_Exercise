package ch.hslu.appe.fbs.business.reorder;

public final class ReorderManagerFactory {

    private ReorderManagerFactory() {
    }

    public static ReorderManager getReorderManager() {
        return new ReorderManagerImpl();
    }

}
