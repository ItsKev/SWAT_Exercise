package ch.hslu.appe.fbs.client.Userinterface.CreateOrderController;

import ch.hslu.appe.fbs.client.UiModels.UiItem;
import ch.hslu.appe.fbs.client.UiModels.UiOrderItem;
import ch.hslu.appe.fbs.client.Userinterface.Shared.CurrentOrderSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class OrderOverviewController {
    public TableView<UiOrderItem> tableView;
    public TableColumn<UiItem, Integer> id;
    public TableColumn<UiItem, String> name;
    public TableColumn<UiItem, Integer> price;
    public TableColumn<UiItem, String> artNr;
    public TableColumn<UiItem, Integer> quantity;
    private List<UiOrderItem> uiItems;

    public void initController() {
        this.tableView.getItems().clear();
        List<UiOrderItem> uiItems = CurrentOrderSingleton.getCurrentOrder().getOrderItems();

        this.uiItems = (List) ((ArrayList) uiItems).clone();
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.price.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.artNr.setCellValueFactory(new PropertyValueFactory<>("artNr"));
        this.quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        ObservableList<UiOrderItem> observableList = FXCollections.observableList(this.uiItems);

        this.tableView.setItems(observableList);
    }
}
