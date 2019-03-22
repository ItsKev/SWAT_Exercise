package ch.hslu.appe.fbs.business.user;

public class UserManagerFactory {
    private UserManagerFactory() {
    }

    public static UserManager getUserManager() {
        return new UserManagerImpl();
    }
}
