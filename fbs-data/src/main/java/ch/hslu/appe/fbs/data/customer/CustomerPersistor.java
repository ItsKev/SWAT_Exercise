package ch.hslu.appe.fbs.data.customer;

import ch.hslu.appe.fbs.model.db.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerPersistor {

    /***
     * Gets a customer by the specified id (if present).
     *
     * @param customerId The specified id of the customer which ought to be returned
     * @return An optional of type {@link Customer} which contains the specified customer if present
     */
    Optional<Customer> getById(final int customerId);

    /***
     * Gets a list of all customers in the database.
     *
     * @return A list of type {@link Customer} which contains all the customers contained in the database
     */
    List<Customer> getAll();

    /***
     * Saves a new customer in the database.
     *
     * @param customer The customer to be saved
     */
    void save(final Customer customer);

    /***
     * Updates an existing customer in the database (if present).
     *
     * @param customer The customer to be updated (if present)
     */
    void update(final Customer customer);

    /***
     * Deletes the customer with all his orders
     * @param customer The customer to be deleted.
     */
    void delete(final Customer customer);
}
