package ch.hslu.appe.fbs.business.order;

import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.OrderDTO;
import ch.hslu.appe.fbs.common.dto.OrderStateDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;

import java.util.List;

public interface OrderManager {

    /**
     * Get all orders of a specific customer
     *
     * @param customerId the id of the customer whose orders need to be returned
     * @param userDTO The user which initiated this action
     * @return all orders of the specified customer
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    List<OrderDTO> getOrders(int customerId, UserDTO userDTO) throws UserNotAuthorisedException;

    /**
     * Get all orders of a specific customer
     *
     * @param customer the customer whose orders need to be returned
     * @param userDTO The user which initiated this action
     * @return all orders of the specified customer
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    List<OrderDTO> getOrders(CustomerDTO customer, UserDTO userDTO) throws UserNotAuthorisedException;

    /**
     * Get all orders of a specific customer in a specific state
     *
     * @param customerId the id of the customer whose orders need to be returned
     * @param orderState the specified state in which an order has to be
     * @param userDTO The user which initiated this action
     * @return all orders of the specified customer in the specified state
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    List<OrderDTO> getOrdersByOrderState(int customerId, OrderStateDTO orderState, UserDTO userDTO) throws UserNotAuthorisedException;

    /**
     * Create a new order
     *
     * @param order the order to create
     * @param userDTO The user which initiated this action
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    void createOrder(OrderDTO order, UserDTO userDTO) throws UserNotAuthorisedException;

    /**
     * Cancel an order
     *
     * @param order the order to cancel
     * @param userDTO The user which initiated this action
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    void cancelOrder(OrderDTO order, UserDTO userDTO) throws UserNotAuthorisedException;

    /**
     * Updates an Order
     *
     * @param order the Updated Order
     * @param userDTO The user which initiated this action
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    void updateOrder(OrderDTO order, UserDTO userDTO) throws UserNotAuthorisedException;

    /***
     * Get all orders
     * @param userDTO The user which initiated this action
     * @return all orders from every customer
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    List<OrderDTO> getAllOrders(UserDTO userDTO) throws UserNotAuthorisedException;
}
