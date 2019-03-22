package ch.hslu.appe.fbs.remote;

import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.ReorderService;
import ch.hslu.appe.fbs.common.rmi.UserService;
import ch.hslu.appe.fbs.remote.customer.CustomerServiceImpl;
import ch.hslu.appe.fbs.remote.item.ItemServiceImpl;
import ch.hslu.appe.fbs.remote.reorder.ReorderServiceImpl;
import ch.hslu.appe.fbs.remote.rmi.RmiConnector;
import ch.hslu.appe.fbs.remote.user.UserServiceImpl;

import java.rmi.RemoteException;

public class RmiServer {

    private static UserService userService;
    private static CustomerService customerService;
    private static ItemService itemService;
    private static ReorderService reorderService;

    static {
        try {
            userService = new UserServiceImpl();
            customerService = new CustomerServiceImpl();
            itemService = new ItemServiceImpl();
            reorderService = new ReorderServiceImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RmiConnector.tryBindServiceToRegistry(userService);
        RmiConnector.tryBindServiceToRegistry(customerService);
        RmiConnector.tryBindServiceToRegistry(itemService);
        RmiConnector.tryBindServiceToRegistry(reorderService);
    }
}
