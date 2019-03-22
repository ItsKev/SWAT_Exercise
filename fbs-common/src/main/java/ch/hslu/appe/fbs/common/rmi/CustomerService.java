package ch.hslu.appe.fbs.common.rmi;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.OrderDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;

import java.rmi.RemoteException;
import java.util.List;

public interface CustomerService extends FBSService {

    CustomerDTO getCustomer(int customerId) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException;

    List<CustomerDTO> getAllCustomers() throws RemoteException, UserNotAuthorisedException;

    List<OrderDTO> getOrders(int customerId) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException;

    List<BillDTO> getRemindedBills(int customerId) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException;

    void createOrder(OrderDTO orderDTO) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException;

    void createCustomer(CustomerDTO customerDTO) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException;

    void cancelOrder(OrderDTO orderDTO) throws RemoteException, IllegalArgumentException, UserNotAuthorisedException;

    List<OrderDTO> getAllOrders() throws RemoteException, UserNotAuthorisedException;
}
