package ch.hslu.appe.fbs.data.order;

import ch.hslu.appe.fbs.model.db.Order;

import java.util.List;
import java.util.Optional;

public interface OrderPersistor {

    /***
     * Gets an order by the specified id (if present).
     *
     * @param orderId The specified id of the order which ought to be returned
     * @return An optional of type {@link Order} which contains the specified order if present
     */
    Optional<Order> getById(final int orderId);

    /***
     * Gets a list of all orders in the database.
     *
     * @return A list of type {@link Order} which contains all the orders contained in the database
     */
    List<Order> getAll();

    /***
     * Gets all orders associated with the specified customer.
     *
     * @param customerId The id of the specified customer
     * @return A list of type {@link Order} which contains all orders associated with the specified customer
     */
    List<Order> getByCustomer(final int customerId);

    /***
     * Gets all orders associated with the specified customer and explicit state.
     *
     * @param customerId The id of the specified customer
     * @param stateId The id of the explicit state in which the orders have to be
     * @return A list of type {@link Order} which contains all orders associated with the specified customer and the explicit state
     */
    List<Order> getByCustomerAndOrderState(final int customerId, final int stateId);

    /***
     * Saves a new order in the database.
     *
     * @param order The order to be saved
     */
    void save(final Order order);

    /***
     * Updates an existing order in the database (if present).
     *
     * @param order The order to be updated (if present)
     */
    void update(final Order order);
}
