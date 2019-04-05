package ch.hslu.appe.fbs.client;

import ch.hslu.appe.fbs.client.Userinterface.Shared.AlertMessages;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.common.rmi.UserService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {

    public static void main(String[] args) throws MalformedURLException {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 2077);
            UserService userService = (UserService) Naming.lookup(RmiLookupTable.getUserServiceName());
            try {
                UserDTO user = userService.performLogin("zhdat", "1234");
                System.out.println("Successfully logged in " + user.getUserName());
            } catch (IllegalArgumentException e) {
                System.out.println("Failed");
            }

            try {
                UserDTO user = userService.performLogin("zhdat", "datatypist");
                System.out.println("Successfully logged in " + user.getUserName());
            } catch (IllegalArgumentException e) {
                System.out.println("Failed");
            }

            CustomerService customerService = (CustomerService) registry.lookup(RmiLookupTable.getCustomerServiceName());
            customerService.getOrders(18).forEach(orderDTO -> System.out.println(orderDTO.toString()));

            ItemService itemService = (ItemService) registry.lookup(RmiLookupTable.getItemServiceName());
            itemService.getAllItems().forEach(itemDTO -> System.out.println(itemDTO.getName()));

        } catch (RemoteException |
                NotBoundException e)

        {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }
    }
}
