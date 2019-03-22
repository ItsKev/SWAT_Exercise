package ch.hslu.appe.fbs.data.reminder;

public final class ReminderPersistorFactory {

    private ReminderPersistorFactory() {
    }

    public static ReminderPersistor createReminderPersistor() {
        return new ReminderPersistorJpa();
    }
}
