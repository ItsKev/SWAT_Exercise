package ch.hslu.appe.fbs.data.orderitem;

import ch.hslu.appe.fbs.model.db.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemPersistor {

    /***
     * Gets a specific orderitem associated with an order and an item.
     *
     * @param orderId The id of the specified order
     * @param itemId The id of the specified item
     * @return An optional of type {@link OrderItem} which contains the associated orderitem
     */
    Optional<OrderItem> getByOrderIdItemId(final int orderId, final int itemId);

    /***
     * Gets a list of orderitems associated with an specific order.
     *
     * @param orderId The id of the specified order
     * @return A list of type {@link OrderItem} which contains the orderitems associated with the specified order
     */
    List<OrderItem> getByOrderId(final int orderId);

    /***
     * Gets a list of orderitems associated with an specific item.
     *
     * @param itemId The id of the specified item
     * @return A list of type {@link OrderItem} which contains the orderitems associated with the specified item
     */
    List<OrderItem> getByItemId(final int itemId);

    /***
     * Gets a list of all orderitems in the database.
     *
     * @return A list of type {@link OrderItem} which contains all the orderitems contained in the database
     */
    List<OrderItem> getAll();

    /***
     * Saves a new orderitem in the database.
     *
     * @param orderItem The orderitem to be saved
     */
    void save(final OrderItem orderItem);

    /***
     * Saves a list of orderitems in the database.
     *
     * @param orderItems The orderitems to be stored
     */
    void saveAll(final List<OrderItem> orderItems);
}
