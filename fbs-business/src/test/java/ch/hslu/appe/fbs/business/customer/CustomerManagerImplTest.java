package ch.hslu.appe.fbs.business.customer;

import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.customer.CustomerPersistor;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.Customer;
import ch.hslu.appe.fbs.model.db.User;
import ch.hslu.appe.fbs.model.db.UserRole;
import ch.hslu.appe.fbs.wrapper.CustomerWrapper;
import ch.hslu.appe.fbs.wrapper.UserWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CustomerManagerImplTest {

    @Mock
    private CustomerPersistor customerPersistorMock;

    private CustomerManager customerManager;

    @Before
    public void setUp() {
        customerManager = new CustomerManagerImpl(customerPersistorMock);
        Mockito.when(customerPersistorMock.getAll()).thenReturn(createCustomers());
        Mockito.when(customerPersistorMock.getById(2))
                .thenReturn(Optional.of(createCustomer(2, "Test")));
    }

    @Test
    public void getAllCustomers_When_UserAuthorized_Then_GetAllCustomers() throws UserNotAuthorisedException {
        List<CustomerDTO> customers = customerManager.getAllCustomers(createUser());
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals("Test" + i, customers.get(i).getPrename());
        }
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getAllCustomers_When_UserNotAuthorized_Then_ThrowException() throws UserNotAuthorisedException {
        customerManager.getAllCustomers(createUnauthorizedUser());
    }

    @Test
    public void getCustomer_When_UserAuthorized_Then_GetCustomerWithId() throws UserNotAuthorisedException {
        CustomerDTO customer = customerManager.getCustomer(2, createUser());
        Assert.assertEquals("Test2", customer.getPrename());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getCustomer_When_UserNotAuthorized_Then_ThrowException() throws UserNotAuthorisedException {
        customerManager.getCustomer(2, createUnauthorizedUser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomer_When_CustomerWithIdNotAvailable_Then_ThrowException() throws UserNotAuthorisedException {
        customerManager.getCustomer(99, createUser());
    }

    @Test
    public void createCustomer_When_UserAuthorized_Then_PersistNewCustomer() throws UserNotAuthorisedException {
        Customer testee = createCustomer(1, "Test");
        customerManager.createCustomer(new CustomerWrapper().dtoFromEntity(testee),
                createUser());
        Mockito.verify(customerPersistorMock, Mockito.times(1)).save(testee);
    }

    @Test (expected = UserNotAuthorisedException.class)
    public void createCustomer_When_UserNotAuthorized_Then_ThrowException() throws UserNotAuthorisedException {
        Customer testee = createCustomer(1, "Test");
        customerManager.createCustomer(new CustomerWrapper().dtoFromEntity(testee),
                createUnauthorizedUser());
        Mockito.verify(customerPersistorMock, Mockito.times(0)).save(testee);
    }

    private List<Customer> createCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customers.add(createCustomer(i, "Test"));
        }
        return customers;
    }

    private Customer createCustomer(int customerId, String name) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setPrename(name + customerId);
        customer.setSurname("Testee");
        customer.setPlz(6206);
        customer.setAdress("Testadress 12");
        customer.setCity("Testhausen");
        return customer;
    }

    private UserDTO createUser() {
        User user = new User();
        user.setId(1);
        user.setPassword("");
        user.setUserName("Test");
        user.setDeleted((byte) 0);
        UserRole userRole = new UserRole();
        userRole.setId(1);
        userRole.setRoleName(UserRoles.SYSADMIN.getRole());
        user.setUserRoleByUserRole(userRole);
        user.setUserRole(1);
        return new UserWrapper().dtoFromEntity(user);
    }

    private UserDTO createUnauthorizedUser() {
        User user = new User();
        user.setId(1);
        user.setPassword("");
        user.setUserName("Test");
        user.setDeleted((byte) 0);
        UserRole userRole = new UserRole();
        userRole.setId(10);
        userRole.setRoleName(UserRoles.BRANCHMANAGER.getRole());
        user.setUserRoleByUserRole(userRole);
        user.setUserRole(1);
        return new UserWrapper().dtoFromEntity(user);
    }
}