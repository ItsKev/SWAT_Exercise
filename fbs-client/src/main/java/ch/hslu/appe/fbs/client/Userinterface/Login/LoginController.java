package ch.hslu.appe.fbs.client.Userinterface.Login;

import ch.hslu.appe.fbs.client.Userinterface.Shared.UserSingleton;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.common.rmi.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class LoginController implements LoginSubject {
    public PasswordField PasswordInput;
    public TextField UsernameInput;
    public Label LoginResponseLabel;
    private UserService userService;
    private boolean loginSuccesfull = false;
    private List<LoginObserver> observers = new ArrayList<>();
    private UserDTO user;

    public LoginController() {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            this.userService = (UserService) registry.lookup(RmiLookupTable.getUserServiceName());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        String username = UsernameInput.getText();
        String password = PasswordInput.getText();

        try {
            this.user = userService.performLogin(username, password);
            this.loginSuccesfull = true;
            UserSingleton.setUser(this.user);
            this.update();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            LoginResponseLabel.setText("Wrong username or password");
            LoginResponseLabel.setTextFill(Color.RED);
            LoginResponseLabel.setVisible(true);
        }
    }

    @Override
    public void addObserver(LoginObserver observer) {
        observers.add(observer);
    }

    @Override
    public void update() {
        for (LoginObserver observer : observers) {
            observer.loginSuccesfull();
        }
    }

    public void onEnter(ActionEvent actionEvent) {
        this.handleSubmitButtonAction(actionEvent);
    }
}
