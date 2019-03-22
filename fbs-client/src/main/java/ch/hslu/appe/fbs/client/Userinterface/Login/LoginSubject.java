package ch.hslu.appe.fbs.client.Userinterface.Login;

public interface LoginSubject {
    void addObserver(LoginObserver observer);

    void update();
}
