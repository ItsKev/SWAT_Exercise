package ch.hslu.appe.fbs.data.user;

import ch.hslu.appe.fbs.model.db.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistor {

    /***
     * Gets an user by the specified id (if present).
     *
     * @param userId The specified id of the user which ought to be returned
     * @return An optional of type {@link User} which contains the specified user if present
     */
    Optional<User> getById(final int userId);

    /***
     * Gets an user by the specified username (if present).
     *
     * @param name The specified username of the user which ought to be returned
     * @return An optional of type {@link User} which contains the specified user if present
     */
    Optional<User> getByName(final String name);

    /***
     * Gets a list of all users in the database.
     *
     * @return A list of type {@link User} which contains all the users contained in the database
     */
    List<User> getAll();

    /***
     * Saves a new user in the database.
     *
     * @param user The user to be saved
     */
    void save(final User user);
}
