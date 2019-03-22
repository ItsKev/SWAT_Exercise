package ch.hslu.appe.fbs.business.user;

import ch.hslu.appe.fbs.data.user.UserPersistorFactory;

public class UserManagerFactory {
    private UserManagerFactory() {
    }

    public static UserManager getUserManager() {
        return new UserManagerImpl(UserPersistorFactory.createUserPersistor());
    }
}
