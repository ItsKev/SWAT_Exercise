package ch.hslu.appe.fbs.data.bill;

import ch.hslu.appe.fbs.model.db.Bill;

import java.util.List;
import java.util.Optional;

public interface BillPersistor {

    /***
     * Gets a bill by the specified id (if present).
     *
     * @param billId The specified id of the bill which ought to be returned
     * @return An optional of type {@link Bill} which contains the specified bill if present
     */
    Optional<Bill> getById(final int billId);

    /***
     * Gets a list of bills which are assigned to a specific order.
     *
     * @param orderId The id of the specified order
     * @return A list of type {@link Bill} which contains the bills assigned to the specified order
     */
    List<Bill> getByOrderId(final int orderId);

    /***
     * Gets a list of all bills in the database.
     *
     * @return A list of type {@link Bill} which contains all the bills contained in the database
     */
    List<Bill> getAll();

    /***
     * Saves a new bill in the database.
     *
     * @param bill The bill to be saved
     */
    void save(final Bill bill);

    /***
     * Updates an existing bill in the database (if present).
     *
     * @param bill The bill to be updated (if present)
     */
    void update(final Bill bill);
}
