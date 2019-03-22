package ch.hslu.appe.fbs.business.customer;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationManager;
import ch.hslu.appe.fbs.business.logger.Logger;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.customer.CustomerPersistor;
import ch.hslu.appe.fbs.data.customer.CustomerPersistorFactory;
import ch.hslu.appe.fbs.model.db.Customer;
import ch.hslu.appe.fbs.wrapper.CustomerWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerManagerImpl implements CustomerManager {

    private static final Object LOCK = new Object();

    private final CustomerPersistor customerPersistor;
    private final CustomerWrapper customerWrapper;

    public CustomerManagerImpl() {
        this.customerPersistor = CustomerPersistorFactory.createCustomerPersistor();
        this.customerWrapper = new CustomerWrapper();
    }

    @Override
    public List<CustomerDTO> getAllCustomers(UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ALL_CUSTOMERS);
        synchronized (LOCK) {
            List<CustomerDTO> customers = new ArrayList<>();
            this.customerPersistor.getAll().forEach(customer -> customers.add(customerWrapper.dtoFromEntity(customer)));
            return customers;
        }
    }

    @Override
    public CustomerDTO getCustomer(int customerId, UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_CUSTOMER);
        synchronized (LOCK) {
            Optional<Customer> customer = this.customerPersistor.getById(customerId);
            if (customer.isPresent()) {
                return customerWrapper.dtoFromEntity(customer.get());
            }
            throw new IllegalArgumentException("Customer with id " + customerId + " not found!");
        }
    }

    @Override
    public void createCustomer(CustomerDTO customerDTO, UserDTO userDTO) throws UserNotAuthorisedException {
        if (customerDTO == null) {
            throw new IllegalArgumentException("customer reference can't be null");
        }
        if (userDTO == null) {
            throw new IllegalArgumentException("user reference can't be null");
        }
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.CREATE_CUSTOMER);
        synchronized (LOCK) {
            final Customer customer = this.customerWrapper.entityFromDTO(customerDTO);
            this.customerPersistor.save(customer);
            Logger.logInfo(userDTO.getUserName(), "Created new customer: " + customer.getSurname() + " " + customer.getPrename());
        }
    }
}
