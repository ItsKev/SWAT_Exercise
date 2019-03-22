package ch.hslu.appe.fbs.data.userrole;

import ch.hslu.appe.fbs.model.db.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRolePersistor {

    /***
     * Gets an userrole by the specified id (if present).
     *
     * @param roleId The specified id of the userrole which ought to be returned
     * @return An optional of type {@link UserRole} which contains the specified userrole if present
     */
    Optional<UserRole> getById(final int roleId);

    /***
     * Gets an userrole by the specified name (if present).
     *
     * @param name The specified name of the userrole which ought to be returned
     * @return An optional of type {@link UserRole} which contains the specified userrole if present
     */
    Optional<UserRole> getByName(final String name);

    /***
     * Gets a list of all userroles in the database.
     *
     * @return A list of type {@link UserRole} which contains all the userroles contained in the database
     */
    List<UserRole> getAll();
}
