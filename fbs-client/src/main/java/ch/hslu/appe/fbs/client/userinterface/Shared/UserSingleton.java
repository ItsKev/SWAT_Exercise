package ch.hslu.appe.fbs.client.userinterface.Shared;

import ch.hslu.appe.fbs.common.dto.UserDTO;

public class UserSingleton {
    private static UserDTO User;

    public static void setUser(UserDTO user) {
        User = user;
    }

    public static UserDTO getUser() {
        return User;
    }
}
