package ch.hslu.appe.fbs.data.reorder;

import ch.hslu.appe.fbs.model.db.Reorder;

import java.util.List;
import java.util.Optional;

public interface ReorderPersistor {

    /***
     * Gets a reorder by the specified id (if present).
     *
     * @param reorderId The specified id of the reorder which ought to be returned
     * @return An optional of type {@link Reorder} which contains the specified reorder if present
     */
    Optional<Reorder> getById(final int reorderId);

    /***
     * Gets a list of reminders associated with a specific item.
     *
     * @param itemId The id of the specified item
     * @return A list of type {@link Reorder} which contains the reminders associated with the specified item
     */
    List<Reorder> getByItemId(final int itemId);

    /***
     * Gets a list of reminders associated with a specific order.
     *
     * @param orderId The id of the specified order
     * @return A list of type {@link Reorder} which contains the reminders associated with the specified order
     */
    List<Reorder> getByOrderId(final int orderId);

    /***
     * Gets a list of all reorders in the database.
     *
     * @return A list of type {@link Reorder} which contains all the reorders contained in the database
     */
    List<Reorder> getAll();

    /***
     * Gets a list of all currently active reorders associated with a specific item and order.
     *
     * @param itemId The id of the specified item
     * @param orderId The id of the specified order
     * @return A list of type {@link Reorder} which contains all currently active reorders associated with the specified item and order
     */
    List<Reorder> getActiveReorders(final int itemId, final int orderId);

    /***
     * Saves a new reorder in the database.
     *
     * @param reorder The reorder to be saved
     */
    void save(final Reorder reorder);

    /***
     * Updates an existing reorder in the database (if present).
     *
     * @param reorder The reorder to be updated (if present)
     */
    void update(final Reorder reorder);
}
