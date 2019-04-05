package ch.hslu.appe.fbs.client.userinterface.createordercontroller;

import ch.hslu.appe.fbs.client.UiModels.UiOrder;
import ch.hslu.appe.fbs.client.userinterface.Shared.AlertMessages;
import ch.hslu.appe.fbs.client.userinterface.Shared.CurrentOrderSingleton;
import ch.hslu.appe.fbs.client.userinterface.Shared.SpecificOrderController;
import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class FinishOrderController {

    private static final Logger LOGGER = LogManager.getLogger(FinishOrderController.class);

    @FXML
    private AnchorPane contentAnchorPane;

    private Parent specificOrderView;
    private SpecificOrderController specificOrderController;
    private CustomerService customerService;
    private UiOrder order;

    public FinishOrderController() {
        FXMLLoader specificOrderViewLoader = new FXMLLoader();
        specificOrderViewLoader.setLocation(getClass().getResource("/Views/Shared/SpecificOrderView.fxml"));

        try {
            this.specificOrderView = specificOrderViewLoader.load();
            this.specificOrderController = specificOrderViewLoader.getController();

        } catch (IOException e) {
            LOGGER.error(e);
        }

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            this.customerService = (CustomerService) registry.lookup(RmiLookupTable.getCustomerServiceName());
        } catch (RemoteException | NotBoundException e) {
            LOGGER.error(e);
        }
    }

    public void initController() {
        this.contentAnchorPane.getChildren().clear();
        this.contentAnchorPane.getChildren().add(this.specificOrderView);


        this.order = CurrentOrderSingleton.getCurrentOrder();
        this.specificOrderController.initController(this.order);
    }

    public void finishPressed() {
        if (this.order.getCustomer() == null) {
            this.showNoCustomerError();
            return;
        }

        List<BillDTO> openPayments = getOpenPayments();
        boolean orderIsValid = openPayments.isEmpty() && !this.order.getOrderItems().isEmpty();

        if (orderIsValid) {
            finishOrder();
        } else if (this.order.getOrderItems().isEmpty()) {
            this.showNoItemsError();
        } else if (!openPayments.isEmpty()) {
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
        List<BillDTO> openPayments = new ArrayList<>();
        try {
            openPayments = this.customerService.getRemindedBills(this.order.getCustomer().getId());
        } catch (RemoteException e) {
            LOGGER.error(e);
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
        if (this.order.getId() == -1) {
            sendOrder();
        } else {
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
            LOGGER.error(e);
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }
    }

    private void sendOrder() {
        try {
            customerService.createOrder(this.order.getOrderDTO());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bestellung wurde abgeschickt!");
            alert.showAndWait();
            CurrentOrderSingleton.createNewOrder();
        } catch (RemoteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Senden der Bestellung ist fehlgeschlagen!");
            alert.showAndWait();
            LOGGER.error(e);
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }
    }
}
