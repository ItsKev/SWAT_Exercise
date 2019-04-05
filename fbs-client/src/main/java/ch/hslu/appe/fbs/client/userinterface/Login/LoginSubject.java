package ch.hslu.appe.fbs.client.userinterface.Login;

public interface LoginSubject {
    void addObserver(LoginObserver observer);

    void update();
}
