package ch.hslu.appe.fbs.data.reminder;

import ch.hslu.appe.fbs.model.db.Reminder;

import java.util.List;
import java.util.Optional;

public interface ReminderPersistor {

    /***
     * Gets a reminder by the specified id (if present).
     *
     * @param reminderId The specified id of the reminder which ought to be returned
     * @return An optional of type {@link Reminder} which contains the specified reminder if present
     */
    Optional<Reminder> getById(final int reminderId);

    /***
     * Gets a list of reminders associated with a specific bill.
     *
     * @param billId The id of the specified bill
     * @return A list of type {@link Reminder} which contains the reminders associated with the specified bill
     */
    List<Reminder> getByBillId(final int billId);

    /***
     * Gets a list of all reminders in the database.
     *
     * @return A list of type {@link Reminder} which contains all the reminders contained in the database
     */
    List<Reminder> getAll();

    /***
     * Saves a new reminder in the database.
     *
     * @param reminder The reminder to be saved
     */
    void save(final Reminder reminder);

    /***
     * Updates an existing reminder in the database (if present).
     *
     * @param reminder The reminder to be updated (if present)
     */
    void update(final Reminder reminder);
}
