package ch.hslu.appe.fbs.client.Userinterface;

import ch.hslu.appe.fbs.client.Userinterface.Login.LoginController;
import ch.hslu.appe.fbs.client.Userinterface.Login.LoginObserver;
import ch.hslu.appe.fbs.client.Userinterface.MainController.MainViewController;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.common.rmi.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application implements LoginObserver {

    private LoginController loginController;
    private Stage stage;
    private UserDTO user;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> close());
        showLogin();
        // this.showMain();
    }

    private void showLogin() throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Login.fxml"));
        Parent content = loader.load();
        this.loginController = loader.getController();
        this.loginController.addObserver(this);
        this.stage.setTitle("Login");
        Scene scene = new Scene(content, 350, 400);
        scene.getStylesheets().add(getClass().getResource("/Styles/bootstrap3.css").toExternalForm());
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void showMain() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Main.fxml"));
        Parent content = null;
        MainViewController controller = null;

        try {
            content = loader.load();
            controller = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.stage.setTitle("Application");
        this.stage.setMaximized(true);
        Scene scene = new Scene(content, 1200, 600);
        scene.getStylesheets().add(getClass().getResource("/Styles/bootstrap3.css").toExternalForm());
        this.stage.setScene(scene);

        this.stage.show();
    }

    private void close() {
        try {
            final Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            final UserService userService = (UserService) registry.lookup(RmiLookupTable.getUserServiceName());
            userService.performLogout();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void loginSuccesfull() {
        this.stage.close();
        this.showMain();
    }
}