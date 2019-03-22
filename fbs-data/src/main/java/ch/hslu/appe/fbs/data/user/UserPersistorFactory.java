package ch.hslu.appe.fbs.data.user;

public final class UserPersistorFactory {

    private UserPersistorFactory() {
    }

    public static UserPersistor createUserPersistor() {
        return new UserPersistorJpa();
    }

}
