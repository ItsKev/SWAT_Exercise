package ch.hslu.appe.fbs.client.Userinterface.CreateOrderController;

import ch.hslu.appe.fbs.client.UiModels.UiItem;
import ch.hslu.appe.fbs.client.UiModels.UiOrder;
import ch.hslu.appe.fbs.client.Userinterface.Shared.AlertMessages;
import ch.hslu.appe.fbs.client.Userinterface.Shared.CurrentOrderSingleton;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsController {

    public TableView<UiItem> tableView;
    public TableColumn<UiItem, Integer> id;
    public TableColumn<UiItem, String> name;
    public TableColumn<UiItem, Integer> price;
    public TableColumn<UiItem, String> artNr;
    public TableColumn<UiItem, Integer> localStock;
    private ItemService itemService;
    private UiOrder order;

    public OrderItemsController() {
        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            this.itemService = (ItemService) registry.lookup(RmiLookupTable.getItemServiceName());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void initController() {
        this.order = CurrentOrderSingleton.getCurrentOrder();
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.price.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.artNr.setCellValueFactory(new PropertyValueFactory<>("artNr"));
        this.localStock.setCellValueFactory(new PropertyValueFactory<>("localStock"));
    }

    public void reloadItems() {
        List<ItemDTO> allItems = getItemDtos();
        ObservableList<UiItem> uiItems = getUiItems(allItems);

        this.tableView.getItems().clear();
        this.tableView.setItems(uiItems);
    }

    public void addItemPressed(ActionEvent actionEvent) {
        UiItem selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            this.order.addUiItem(selectedItem);
        }
    }

    private ObservableList<UiItem> getUiItems(List<ItemDTO> allItems) {
        List<UiItem> list = new ArrayList<>();
        ObservableList<UiItem> uiItems = FXCollections.observableList(list);

        for (ItemDTO item : allItems) {
            uiItems.add(new UiItem(item));
        }
        return uiItems;
    }

    private List<ItemDTO> getItemDtos() {
        List<ItemDTO> allItems = null;
        try {
            allItems = this.itemService.getAllItems();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }

        return allItems;
    }
}
