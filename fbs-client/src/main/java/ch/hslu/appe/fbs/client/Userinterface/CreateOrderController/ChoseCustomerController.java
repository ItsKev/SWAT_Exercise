package ch.hslu.appe.fbs.client.Userinterface.CreateOrderController;

import ch.hslu.appe.fbs.client.UiModels.UiOrder;
import ch.hslu.appe.fbs.client.Userinterface.Shared.AlertMessages;
import ch.hslu.appe.fbs.client.Userinterface.Shared.CurrentOrderSingleton;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ChoseCustomerController {
    public TableView<CustomerDTO> tableView;
    public TableColumn<CustomerDTO, String> prename;
    public TableColumn<CustomerDTO, String> surname;
    public TableColumn<CustomerDTO, Integer> plz;
    public TableColumn<CustomerDTO, String> city;
    public TableColumn<CustomerDTO, Integer> adress;
    private UiOrder order;
    private CustomerService customerService;

    public ChoseCustomerController() {
        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            // TODO: remove dependency
            this.customerService = (CustomerService) registry.lookup(RmiLookupTable.getCustomerServiceName());
            // this.customerService = new CustomerServiceImpl();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void choseCustomerPressed(ActionEvent actionEvent) {
        CustomerDTO selectedCustomer = this.tableView.getSelectionModel().getSelectedItem();
        this.order.setCustomer(selectedCustomer);
    }

    public void initController() {
        this.order = CurrentOrderSingleton.getCurrentOrder();

        this.prename.setCellValueFactory(new PropertyValueFactory<>("prename"));
        this.surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.plz.setCellValueFactory(new PropertyValueFactory<>("plz"));
        this.city.setCellValueFactory(new PropertyValueFactory<>("city"));
        this.adress.setCellValueFactory(new PropertyValueFactory<>("adress"));

        ObservableList<CustomerDTO> uiItems = null;

        try {
            List<CustomerDTO> allCustomers = this.customerService.getAllCustomers();
            uiItems = FXCollections.observableList(allCustomers);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }

        this.tableView.setItems(uiItems);
    }

    public void createCustomerPressed(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/CreateOrder/CreateCustomerView.fxml"));
        Parent createCustomerView = null;

        try {
            createCustomerView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Kunde erstellen");
        stage.setScene(new Scene(createCustomerView, 450, 450));
        stage.showAndWait();
        this.initController();
    }
}
