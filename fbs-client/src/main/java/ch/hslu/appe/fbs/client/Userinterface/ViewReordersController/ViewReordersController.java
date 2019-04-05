package ch.hslu.appe.fbs.client.Userinterface.ViewReordersController;

import ch.hslu.appe.fbs.client.UiModels.UiItem;
import ch.hslu.appe.fbs.client.UiModels.UiReorder;
import ch.hslu.appe.fbs.client.Userinterface.Shared.AlertMessages;
import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ReorderService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ViewReordersController {

    public TableView<UiReorder> tableView;
    public TableColumn<UiItem, Integer> orderId;
    public TableColumn<UiItem, String> itemName;
    public TableColumn<UiItem, Integer> quantity;
    public TableColumn<UiItem, String> orderedTimeStamp;
    public TableColumn<UiItem, String> receivedTimeStamp;
    private ReorderService reorderService;
    private List<UiReorder> reorders;
    private UiReorder selectedItem;

    public ViewReordersController() {
        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            this.reorderService = (ReorderService) registry.lookup(RmiLookupTable.getReorderServiceName());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void initController() {
        this.reloadItems();
        this.orderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.itemName.setCellValueFactory(new PropertyValueFactory<>("item"));
        this.quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.orderedTimeStamp.setCellValueFactory(new PropertyValueFactory<>("reorderDate"));
        this.receivedTimeStamp.setCellValueFactory(new PropertyValueFactory<>("delivered"));
    }

    private List<UiReorder> getReorders() {
        List<ReorderDTO> allReorderDTOs = null;
        ArrayList<UiReorder> allUiReorders = new ArrayList<>();
        try {
            allReorderDTOs = this.reorderService.getAllReorders();
            allReorderDTOs.forEach(reorderDTO -> allUiReorders.add(new UiReorder(reorderDTO)));

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            // TODO specify response
        }

        return allUiReorders;
    }

    public void reloadItems() {
        this.reorders = getReorders();
        ObservableList<UiReorder> uiReorders = FXCollections.observableList(this.reorders);

        this.tableView.getItems().clear();
        this.tableView.setItems(uiReorders);
    }

    public void markAsDelivered(ActionEvent actionEvent) {
        if (this.selectedItem.getDelivered() != null){
            this.showAlreadyDeliveredError();
            return;
        }

        try {
            this.reorderService.markReorderAsDelivered(this.selectedItem.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }

        this.reloadItems();
    }

    private void showAlreadyDeliveredError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, AlertMessages.AlreadyDelivered);
        alert.showAndWait();
    }

    public void itemSelected(MouseEvent mouseEvent) {
        UiReorder item = tableView.getSelectionModel().getSelectedItem();

        if (item != null && item != this.selectedItem) {
            this.selectedItem = item;
        }
    }
}
