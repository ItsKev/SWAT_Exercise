package ch.hslu.appe.fbs.client.Userinterface.CreateOrderController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrderController {


    public AnchorPane anchorPane;
    public Button ViewItemsButton;
    public Button ViewOrderButton;
    public Button ViewCustomerButton;
    public Button ViewFinnishOrderButton;

    private Parent orderItemsView;
    private OrderItemsController orderItemsController;

    private Parent choseCustomerView;
    private ChoseCustomerController choseCustomerController;

    private Parent finnishOrderView;
    private FinnishOrderController finnishOrderController;

    public OrderController() {
        FXMLLoader orderItemsViewLoader = new FXMLLoader();
        orderItemsViewLoader.setLocation(getClass().getResource("/Views/CreateOrder/OrderItemsView.fxml"));

        FXMLLoader choseCustomerLoader = new FXMLLoader();
        choseCustomerLoader.setLocation(getClass().getResource("/Views/CreateOrder/ChoseCustomer.fxml"));

        FXMLLoader finnishOrderLoader = new FXMLLoader();
        finnishOrderLoader.setLocation(getClass().getResource("/Views/CreateOrder/FinnishOrderView.fxml"));

        try {
            this.orderItemsView = orderItemsViewLoader.load();
            this.orderItemsController = orderItemsViewLoader.getController();

            this.choseCustomerView = choseCustomerLoader.load();
            this.choseCustomerController = choseCustomerLoader.getController();

            this.finnishOrderView = finnishOrderLoader.load();
            this.finnishOrderController = finnishOrderLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initController() {
        this.orderItemsController.initController();
        this.choseCustomerController.initController();
        this.finnishOrderController.initController();
    }

    public void viewItemsPressed(ActionEvent actionEvent) {
        this.setSelectedButton(this.ViewItemsButton);
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(orderItemsView);
        this.orderItemsController.initController();
        this.orderItemsController.reloadItems();
    }

    public void viewCustomerPressed(ActionEvent actionEvent) {
        this.setSelectedButton(this.ViewCustomerButton);
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(this.choseCustomerView);
        this.choseCustomerController.initController();
    }

    public void viewFinnishOrderPressed(ActionEvent actionEvent) {
        this.setSelectedButton(this.ViewFinnishOrderButton);
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(this.finnishOrderView);
        this.finnishOrderController.initController();
    }

    public void setSelectedButton(Button selectedButton) {
        this.ViewItemsButton.getStyleClass().remove("selected-button");
        this.ViewCustomerButton.getStyleClass().remove("selected-button");
        this.ViewFinnishOrderButton.getStyleClass().remove("selected-button");

        selectedButton.getStyleClass().add("selected-button");
    }
}
