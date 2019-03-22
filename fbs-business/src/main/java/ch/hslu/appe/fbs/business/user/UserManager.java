package ch.hslu.appe.fbs.business.user;

import ch.hslu.appe.fbs.common.dto.UserDTO;

public interface UserManager {

    /***
     * Performs a user.
     *
     * @param name  name of the user
     * @param password  password (plain for now)
     * @return The {@link UserDTO} if the user was successful
     * @throws IllegalArgumentException If the user fails (incorrect user or password)
     */
    UserDTO loginUser(String name, String password) throws IllegalArgumentException;
}
