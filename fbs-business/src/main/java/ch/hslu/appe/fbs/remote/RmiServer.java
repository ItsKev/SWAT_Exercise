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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;

public class RmiServer {

    private static final Logger LOGGER = LogManager.getLogger(RmiServer.class);

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
            LOGGER.error(e);
        }
    }

    public static void main(String[] args) {
        RmiConnector.tryBindServiceToRegistry(userService);
        RmiConnector.tryBindServiceToRegistry(customerService);
        RmiConnector.tryBindServiceToRegistry(itemService);
        RmiConnector.tryBindServiceToRegistry(reorderService);
    }
}
