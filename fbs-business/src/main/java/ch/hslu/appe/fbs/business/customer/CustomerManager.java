package ch.hslu.appe.fbs.business.customer;

import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;

import java.util.List;

public interface CustomerManager {

    /**
     * Get all customers
     *
     * @param userDTO The user which initiated this action
     * @return all customers in the database
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    List<CustomerDTO> getAllCustomers(UserDTO userDTO) throws UserNotAuthorisedException;

    /**
     * Gets a specific customer by the specified id.
     *
     * @param customerId The customer's unique id
     * @param userDTO The user which initiated this action
     * @return the customer (if present)
     * @throws IllegalArgumentException In case the customer with the given id is not found
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    CustomerDTO getCustomer(int customerId, UserDTO userDTO) throws UserNotAuthorisedException;

    /**
     * Creates a new customer.
     *
     * @param customerDTO The customer to create
     * @param userDTO The user which initiated this action
     * @throws UserNotAuthorisedException In case the specified user isn't authorised to execute this action
     */
    void createCustomer(CustomerDTO customerDTO, UserDTO userDTO) throws UserNotAuthorisedException;
}
