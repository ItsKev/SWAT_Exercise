package ch.hslu.appe.fbs.data.userrole;

public final class UserRolePersistorFactory {

    private UserRolePersistorFactory() {
    }

    public static UserRolePersistor createUserRolePersistor() {
        return new UserRolePersistorJpa();
    }
}
