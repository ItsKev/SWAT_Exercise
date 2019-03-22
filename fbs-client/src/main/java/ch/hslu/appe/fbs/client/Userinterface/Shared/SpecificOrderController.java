package ch.hslu.appe.fbs.client.Userinterface.Shared;

import ch.hslu.appe.fbs.client.UiModels.UiOrder;
import ch.hslu.appe.fbs.client.UiModels.UiOrderItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.util.List;

public class SpecificOrderController {
    public Label SpecificOrderUser;
    public Label SpecificOrderCustomer;
    public Label SpecificOrderTimestamp;
    public Label SpecificOrderState;

    public TableView<UiOrderItem> SpecificItemTable;
    public TableColumn<UiOrderItem, Integer> SpecificIdColumn;
    public TableColumn<UiOrderItem, String> SpecificNameColumn;
    public TableColumn<UiOrderItem, Integer> SpecificPriceColumn;
    public TableColumn<UiOrderItem, Timestamp> SpecificArtnrColumn;
    public TableColumn<UiOrderItem, Integer> SpecificQuantityColumn;
    private UiOrder selectedItem;

    public void initController(UiOrder selectedItem) {
        this.selectedItem = selectedItem;
        this.SpecificIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.SpecificNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.SpecificPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.SpecificArtnrColumn.setCellValueFactory(new PropertyValueFactory<>("artNr"));
        this.SpecificQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        setLabels();

        List<UiOrderItem> orderItems = this.selectedItem.getOrderItems();
        ObservableList<UiOrderItem> uiItems = FXCollections.observableList(orderItems);
        this.SpecificItemTable.setItems(uiItems);
    }

    private void setLabels() {
        setCustomerLabel();
        setOrderStateLabel();
        setTimeStampLabel();

        this.SpecificOrderUser.setText(this.selectedItem.getUserName());
    }

    private void setTimeStampLabel() {
        if (this.selectedItem.getDateTime() != null) {
            this.SpecificOrderTimestamp.setText(this.selectedItem.getDateTime().toString());
        }
        else {
            this.SpecificOrderTimestamp.setText("Kein Zeitstempel vorhanden");
        }
    }

    private void setOrderStateLabel() {
        if (this.selectedItem.getOrderStateName() != null) {
            this.SpecificOrderState.setText(this.selectedItem.getOrderStateName());
        }
        else {
            this.SpecificOrderState.setText("Die Bestellung hat noch keinen Status");
        }

    }

    private void setCustomerLabel() {
        if (this.selectedItem.getCustomer() != null) {
            this.SpecificOrderCustomer.setText(this.selectedItem.getCustomer().toString());
        }
        else {
            this.SpecificOrderCustomer.setText("Es wurde noch kein Kunde ausgewählt");
        }

    }
}
