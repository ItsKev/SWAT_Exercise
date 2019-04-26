package ch.hslu.appe.fbs.business.customer;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationManager;
import ch.hslu.appe.fbs.business.logger.Logger;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.customer.CustomerPersistor;
import ch.hslu.appe.fbs.model.db.Customer;
import ch.hslu.appe.fbs.wrapper.CustomerWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class CustomerManagerImpl implements CustomerManager {

    private static final ReentrantLock lock = new ReentrantLock();

    private final CustomerPersistor customerPersistor;
    private final CustomerWrapper customerWrapper;

    public CustomerManagerImpl(CustomerPersistor customerPersistor) {
        this.customerPersistor = customerPersistor;
        this.customerWrapper = new CustomerWrapper();
    }

    @Override
    public List<CustomerDTO> getAllCustomers(UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_ALL_CUSTOMERS);
        List<CustomerDTO> customers = new ArrayList<>();
        lock.lock();
        try {
            this.customerPersistor.getAll().forEach(customer -> customers.add(customerWrapper.dtoFromEntity(customer)));
        } finally {
            lock.unlock();
        }
        return customers;
    }

    @Override
    public CustomerDTO getCustomer(int customerId, UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.GET_CUSTOMER);
        Optional<Customer> customer;
        lock.lock();
        try {
            customer = this.customerPersistor.getById(customerId);
        } finally {
            lock.unlock();
        }
        if (customer.isPresent()) {
            return customerWrapper.dtoFromEntity(customer.get());
        }
        throw new IllegalArgumentException("Customer with id " + customerId + " not found!");
    }

    @Override
    public void createCustomer(CustomerDTO customerDTO, UserDTO userDTO) throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.CREATE_CUSTOMER);
        final Customer customer = this.customerWrapper.entityFromDTO(customerDTO);
        lock.lock();
        try {
            this.customerPersistor.save(customer);
        } finally {
            lock.unlock();
        }
        Logger.logInfo(userDTO.getUserName(), "Created new customer: " + customer.getSurname() + " " + customer.getPrename());
    }
}
