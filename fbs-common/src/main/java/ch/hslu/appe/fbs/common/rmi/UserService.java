package ch.hslu.appe.fbs.common.rmi;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.permission.UserPermissions;

import java.rmi.RemoteException;
import java.util.Map;

public interface UserService extends FBSService {

    /***
     * Service to perform a user.
     *
     * @param name Name of the user which performs a user
     * @param password Password of the user which performs a user
     * @return The user-specific UserDTO
     * @throws IllegalArgumentException In case the specified name is null, empty or there's no user with the specified name
     * @throws RemoteException In case of an connection-specific issue
     */
    UserDTO performLogin(String name, String password) throws IllegalArgumentException, RemoteException;

    /***
     * Service to perform a logout.
     *
     * @throws RemoteException In case of an connection-specific issue
     */
    void performLogout() throws RemoteException;

    /***
     * Service to check if the current user's (the one who's invoking this method) got the specified permissions {@link UserPermissions}.
     *
     * @param userPermissions One specific or an array of {@link UserPermissions} which ought to be checked against the current user
     * @return A map with each of the specified permissions and an associated flag if the current user is privileged to execute these
     */
    Map<UserPermissions, Boolean> checkUserPermissions(UserPermissions... userPermissions) throws RemoteException;
}
