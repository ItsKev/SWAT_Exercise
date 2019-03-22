package ch.hslu.appe.fbs.client.Userinterface.ViewOrderController;

import ch.hslu.appe.fbs.client.UiModels.UiOrder;
import ch.hslu.appe.fbs.client.Userinterface.Shared.AlertMessages;
import ch.hslu.appe.fbs.client.Userinterface.Shared.CurrentOrderSingleton;
import ch.hslu.appe.fbs.client.Userinterface.Shared.SpecificOrderController;
import ch.hslu.appe.fbs.common.dto.OrderDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ViewAllOrdersController {
    public AnchorPane SpecificOrderAnchorPane;

    private CustomerService customerService;

    public TableView<UiOrder> OverviewTable;
    public TableColumn<UiOrder, String> CustomerColumn;
    public TableColumn<UiOrder, String> UserColumn;
    public TableColumn<UiOrder, String> TimeStampColumn;
    public TableColumn<UiOrder, String> OrderStateColumn;

    private List<UiOrder> uiOrders = new ArrayList<>();
    private UiOrder selectedItem;

    private Parent specificOrderView;
    private SpecificOrderController specificOrderController;

    public ViewAllOrdersController() {
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
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void initController() {
        this.loadOrders();

        this.CustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        this.UserColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        this.TimeStampColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        this.OrderStateColumn.setCellValueFactory(new PropertyValueFactory<>("orderStateName"));

        this.SpecificOrderAnchorPane.getChildren().clear();
        this.SpecificOrderAnchorPane.getChildren().add(specificOrderView);
    }

    private void loadOrders() {
        this.uiOrders.clear();
        List<OrderDTO> orders;

        try {
            orders = this.customerService.getAllOrders();
            orders.forEach(orderDTO -> this.uiOrders.add(new UiOrder(orderDTO)));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }

        ObservableList<UiOrder> observableList = FXCollections.observableList(this.uiOrders);
        this.OverviewTable.setItems(observableList);
    }

    public void itemSelected(MouseEvent mouseEvent) {
        UiOrder item = OverviewTable.getSelectionModel().getSelectedItem();

        if (item != null && item != this.selectedItem) {
            this.selectedItem = item;
            this.showSelectedItem();
        }
    }

    private void showSelectedItem() {
        this.specificOrderController.initController(this.selectedItem);
    }

    public void cancelOrderButtonPressed(ActionEvent actionEvent) {
        if (this.selectedItem != null) {
            cancelOrder();
        } else {
            showErrorMessageWithText(AlertMessages.CancelOrderNoneSelected);
        }
    }

    public void editOrderButtonPressed(ActionEvent actionEvent) {
        if (this.selectedItem != null) {
            editOrder();
        } else {
            this.showErrorMessageWithText(AlertMessages.EditOrderNoneSelected);
        }
    }

    private void editOrder() {
        if (CurrentOrderSingleton.getCurrentOrder().isEmpty()) {
            CurrentOrderSingleton.setCurrentOrder(this.selectedItem);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, AlertMessages.EditOrderAndDiscardOtherOrder);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                CurrentOrderSingleton.setCurrentOrder(this.selectedItem);
            }
        }
    }

    private void showErrorMessageWithText(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    private void cancelOrder() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, AlertMessages.SureCancelOrder);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                this.customerService.cancelOrder(this.selectedItem.getOrderDTO());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }

        this.initController();
    }
}
