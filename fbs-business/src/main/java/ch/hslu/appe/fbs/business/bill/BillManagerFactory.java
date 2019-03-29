package ch.hslu.appe.fbs.business.bill;

import ch.hslu.appe.fbs.data.bill.BillPersistorFactory;
import ch.hslu.appe.fbs.data.reminder.ReminderPersistorFactory;

public final class BillManagerFactory {

    private BillManagerFactory() {
    }

    public static BillManager getBillManager() {
        return new BillManagerImpl(BillPersistorFactory.createBillPersistor(),
                ReminderPersistorFactory.createReminderPersistor());
    }
}
