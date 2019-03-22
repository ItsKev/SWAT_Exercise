package ch.hslu.appe.fbs.data.orderstate;

import ch.hslu.appe.fbs.model.db.OrderState;

import java.util.List;
import java.util.Optional;

public interface OrderStatePersistor {

    /***
     * Gets an specific orderstate by the specified state.
     *
     * @param state The specified state
     * @return An optional of type {@link OrderState} which contains
     */
    Optional<OrderState> getByState(final String state);

    /***
     * Gets an orderstate by the specified id (if present).
     *
     * @param orderStateId The specified id of the orderstate which ought to be returned
     * @return An optional of type {@link OrderState} which contains the specified orderstate if present
     */
    Optional<OrderState> getById(final int orderStateId);

    /***
     * Gets a list of all orderstates in the database.
     *
     * @return A list of type {@link OrderState} which contains all the orderstates contained in the database
     */
    List<OrderState> getAll();
}
