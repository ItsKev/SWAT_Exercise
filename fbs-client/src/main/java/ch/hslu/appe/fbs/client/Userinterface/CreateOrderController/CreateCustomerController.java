package ch.hslu.appe.fbs.client.Userinterface.CreateOrderController;

import ch.hslu.appe.fbs.client.Userinterface.Shared.AlertMessages;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CreateCustomerController {

    public TextField prename;
    public TextField surname;
    public TextField plz;
    public TextField city;
    public TextField adress;

    private CustomerService customerService;

    public CreateCustomerController() {
        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            this.customerService = (CustomerService) registry.lookup(RmiLookupTable.getCustomerServiceName());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void createCustomer(ActionEvent actionEvent) {
        if (prename.getText().isEmpty() || this.surname.getText().isEmpty() ||
                this.plz.getText().isEmpty() || this.city.getText().isEmpty() ||
                this.adress.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informationen unvollständig");
            alert.setContentText("Bitte füllen Sie alle Felder aus");
            alert.showAndWait();
            return;
        }

        CustomerDTO customer = new CustomerDTO(-1, this.prename.getText(), this.surname.getText(),
                Integer.parseInt(this.plz.getText()), this.city.getText(), this.adress.getText());

        try {
            this.customerService.createCustomer(customer);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UserNotAuthorisedException e) {
            AlertMessages.ShowNoPermissionMessage();
        }

        Stage stage = (Stage) this.plz.getScene().getWindow();
        stage.close();
    }
}
