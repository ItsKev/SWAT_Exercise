package ch.hslu.appe.fbs.data.item;

public final class ItemPersistorFactory {

    private ItemPersistorFactory() {
    }

    public static ItemPersistor createItemPersistor() {
        return new ItemPersistorJpa();
    }

}
