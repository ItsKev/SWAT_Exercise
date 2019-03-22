package ch.hslu.appe.fbs.client.Userinterface.MainController;

import ch.hslu.appe.fbs.client.Userinterface.CreateOrderController.OrderController;
import ch.hslu.appe.fbs.client.Userinterface.Shared.CurrentOrderSingleton;
import ch.hslu.appe.fbs.client.Userinterface.ViewOrderController.ViewAllOrdersController;
import ch.hslu.appe.fbs.client.Userinterface.ViewReordersController.ViewReordersController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainViewController {

    public AnchorPane anchorPane;
    public Button OrderButton;
    public Button WatchOrdersButton;
    public Button StorageOrdersButton;
    public Button ManageStorageButton;

    private Parent orderView;
    private OrderController orderController;

    private Parent viewOrdersView;
    private ViewAllOrdersController viewOrdersViewController;

    private Parent reordersView;
    private ViewReordersController viewReordersController;

    public MainViewController() {
        FXMLLoader orderViewLoader = new FXMLLoader();
        orderViewLoader.setLocation(getClass().getResource("/Views/CreateOrder/OrderView.fxml"));

        FXMLLoader viewOrderLoader = new FXMLLoader();
        viewOrderLoader.setLocation(getClass().getResource("/Views/ViewOrder/ViewAllOrders.fxml"));

        FXMLLoader viewReordersLoader = new FXMLLoader();
        viewReordersLoader.setLocation(getClass().getResource("/Views/ViewReorders/ReordersView.fxml"));

        CurrentOrderSingleton.createNewOrder();

        try {
            Parent orderContent = orderViewLoader.load();
            this.orderView = orderContent;
            this.orderController = orderViewLoader.getController();

            Parent viewOrderContent = viewOrderLoader.load();
            this.viewOrdersView = viewOrderContent;
            this.viewOrdersViewController = viewOrderLoader.getController();

            Parent reordersContent = viewReordersLoader.load();
            this.reordersView = reordersContent;
            this.viewReordersController = viewReordersLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void orderButtonPressed(ActionEvent actionEvent) {
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(orderView);
        this.orderController.initController();
        this.setSelectedButton(this.OrderButton);
    }

    public void watchOrdersButtonPressed(ActionEvent actionEvent) {
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(viewOrdersView);
        this.viewOrdersViewController.initController();
        this.setSelectedButton(this.WatchOrdersButton);
    }

    public void storageOrdersButtonPressed(ActionEvent actionEvent) {
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(reordersView);
        this.viewReordersController.initController();
        this.setSelectedButton(this.StorageOrdersButton);
    }

    public void manageStorageButtonPressed(ActionEvent actionEvent) {
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(new Label("manage storage"));
        this.setSelectedButton(this.ManageStorageButton);
    }

    public void setSelectedButton(Button selectedButton) {
        this.OrderButton.getStyleClass().remove("selected-button");
        this.WatchOrdersButton.getStyleClass().remove("selected-button");
        this.StorageOrdersButton.getStyleClass().remove("selected-button");
        this.ManageStorageButton.getStyleClass().remove("selected-button");
        selectedButton.getStyleClass().add("selected-button");
    }
}
