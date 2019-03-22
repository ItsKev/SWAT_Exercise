package ch.hslu.appe.fbs.client.Userinterface.CreateOrderController;

import ch.hslu.appe.fbs.client.UiModels.UiOrder;
import ch.hslu.appe.fbs.client.Userinterface.Shared.AlertMessages;
import ch.hslu.appe.fbs.client.Userinterface.Shared.CurrentOrderSingleton;
import ch.hslu.appe.fbs.client.Userinterface.Shared.SpecificOrderController;
import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.OrderDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class FinnishOrderController {
    public AnchorPane ContentAnchorPane;
    private Parent specificOrderView;
    private SpecificOrderController specificOrderController;
    private CustomerService customerService;
    private UiOrder order;

    public FinnishOrderController() {
        Registry registry = null;

        FXMLLoader specificOrderViewLoader = new FXMLLoader();
        specificOrderViewLoader.setLocation(getClass().getResource("/Views/Shared/SpecificOrderView.fxml"));

        try {
            Parent specificOrderView = specificOrderViewLoader.load();
            this.specificOrderView = specificOrderView;
            this.specificOrderController = specificOrderViewLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            this.customerService = (CustomerService) registry.lookup(RmiLookupTable.getCustomerServiceName());
            // this.customerService = new CustomerServiceImpl();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void initController() {
        this.ContentAnchorPane.getChildren().clear();
        this.ContentAnchorPane.getChildren().add(this.specificOrderView);


        this.order = CurrentOrderSingleton.getCurrentOrder();
        this.specificOrderController.initController(this.order);
    }

    public void finishPressed(ActionEvent actionEvent) {
        if(this.order.getCustomer() == null){
            this.showNoCustomerError();
            return;
        }

        List<BillDTO> openPayments = getOpenPayments();
        boolean orderIsValid = openPayments.isEmpty() && this.order.getOrderItems().isEmpty() == false;

         if (orderIsValid){
             finishOrder();
         }
         else if(this.order.getOrderItems().isEmpty()){
             this.showNoItemsError();
         }
         else if (openPayments.isEmpty() == false){
             this.askToFinishOrder();
         }
    }

    private void showNoItemsError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, AlertMessages.NoItems);
        alert.showAndWait();
    }

    private void showNoCustomerError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, AlertMessages.NoCustomer);
        alert.showAndWait();
    }

    private List<BillDTO> getOpenPayments() {
        List<BillDTO> openPayments = null;
        try {
            openPayments = this.customerService.getRemindedBills(this.order.getCustomer().getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }
        return openPayments;
    }

    private void askToFinishOrder() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, AlertMessages.OpenPayments);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            finishOrder();
        }
    }

    private void finishOrder() {
        if (this.order.getId() == -1){
            sendOrder();
        }
        else {
            editOrder();
        }
    }

    private void editOrder() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, AlertMessages.SureEditOrder);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            cancelOrder();
            this.order.setId(-1);
            sendOrder();
        }
    }

    private void cancelOrder() {
        try {
            this.customerService.cancelOrder(this.order.getOrderDTO());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }
    }

    private void sendOrder() {
        OrderDTO order = this.order.getOrderDTO();
        try {
            customerService.createOrder(order);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bestellung wurde abgeschickt!");
            alert.showAndWait();
            CurrentOrderSingleton.createNewOrder();
        } catch (RemoteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Senden der Bestellung ist fehlgeschlagen!");
            alert.showAndWait();
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }
    }
}
