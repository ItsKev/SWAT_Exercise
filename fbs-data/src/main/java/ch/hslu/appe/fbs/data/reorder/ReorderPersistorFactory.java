package ch.hslu.appe.fbs.data.reorder;

public final class ReorderPersistorFactory {

    private ReorderPersistorFactory() {
    }

    public static ReorderPersistor createReorderPersistor() {
        return new ReorderPersistorJpa();
    }
}
