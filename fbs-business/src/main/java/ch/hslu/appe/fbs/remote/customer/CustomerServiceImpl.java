package ch.hslu.appe.fbs.remote.customer;

import ch.hslu.appe.fbs.business.bill.BillManager;
import ch.hslu.appe.fbs.business.bill.BillManagerFactory;
import ch.hslu.appe.fbs.business.customer.CustomerManager;
import ch.hslu.appe.fbs.business.customer.CustomerManagerFactory;
import ch.hslu.appe.fbs.business.order.OrderManager;
import ch.hslu.appe.fbs.business.order.OrderManagerFactory;
import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.OrderDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.remote.session.UserSessionDictionary;

import java.rmi.RemoteException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerManager customerManager;
    private final OrderManager orderManager;
    private final BillManager billManager;

    public CustomerServiceImpl() {
        this.customerManager = CustomerManagerFactory.getCustomerManager();
        this.orderManager = OrderManagerFactory.getOrderManager();
        this.billManager = BillManagerFactory.getBillManager();
    }

    @Override
    public CustomerDTO getCustomer(int customerId) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException {
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        return this.customerManager.getCustomer(customerId, userDTO);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        return this.customerManager.getAllCustomers(userDTO);
    }

    @Override
    public List<OrderDTO> getOrders(int customerId) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException {
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        return this.orderManager.getOrders(customerId, userDTO);
    }

    @Override
    public List<BillDTO> getRemindedBills(int customerId) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException {
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        return this.billManager.getRemindedBillsByCustomerId(customerId, userDTO);
    }

    @Override
    public void createOrder(OrderDTO orderDTO) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException {
        if (orderDTO == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        this.orderManager.createOrder(orderDTO, userDTO);
    }

    @Override
    public void createCustomer(CustomerDTO customerDTO) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException {
        if (customerDTO == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        this.customerManager.createCustomer(customerDTO, userDTO);
    }

    @Override
    public void cancelOrder(OrderDTO orderDTO) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException {
        if (orderDTO == null) {
            throw new IllegalArgumentException("object reference can't be null");
        }
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        this.orderManager.cancelOrder(orderDTO, userDTO);
    }

    @Override
    public List<OrderDTO> getAllOrders() throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        return this.orderManager.getAllOrders(userDTO);
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getCustomerServiceName();
    }
}
