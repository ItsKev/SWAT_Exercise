package ch.hslu.appe.fbs.client.userinterface.login;

public interface LoginSubject {
    void addObserver(LoginObserver observer);

    void update();
}
