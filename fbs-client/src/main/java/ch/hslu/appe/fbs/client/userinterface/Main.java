package ch.hslu.appe.fbs.client.userinterface;

import ch.hslu.appe.fbs.client.userinterface.login.LoginController;
import ch.hslu.appe.fbs.client.userinterface.login.LoginObserver;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.common.rmi.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application implements LoginObserver {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> close());
        showLogin();
    }

    private void showLogin() throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Login.fxml"));
        Parent content = loader.load();
        LoginController loginController = loader.getController();
        loginController.addObserver(this);
        this.stage.setTitle("login");
        Scene scene = new Scene(content, 350, 400);
        scene.getStylesheets().add(getClass().getResource("/Styles/bootstrap3.css").toExternalForm());
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void showMain() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Main.fxml"));
        Parent content = null;

        try {
            content = loader.load();
            loader.getController();
        } catch (IOException e) {
            LOGGER.error(e);
            return;
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
            LOGGER.error(e);
        }
    }

    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    @Override
    public void loginSuccessful() {
        this.stage.close();
        this.showMain();
    }
}