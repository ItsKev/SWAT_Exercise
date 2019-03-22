package ch.hslu.appe.fbs.business.item;

public class ItemManagerFactory {

    private ItemManagerFactory() {
    }

    public static ItemManager getItemManager() {
        return new ItemManagerImpl();
    }

}
