package ch.hslu.appe.fbs.remote.user;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationManager;
import ch.hslu.appe.fbs.business.user.UserManager;
import ch.hslu.appe.fbs.business.user.UserManagerFactory;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.common.rmi.UserService;
import ch.hslu.appe.fbs.remote.session.UserSessionDictionary;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private final UserManager userManager;

    public UserServiceImpl() throws RemoteException {
        super();
        this.userManager = UserManagerFactory.getUserManager();
    }

    @Override
    public UserDTO performLogin(String name, String password) throws IllegalArgumentException, RemoteException {
        final UserDTO userDTO = this.userManager.loginUser(name, password);
        UserSessionDictionary.addUserSession(userDTO);
        return userDTO;
    }

    @Override
    public void performLogout() throws RemoteException {
        UserSessionDictionary.removeUserSession();
    }

    @Override
    public Map<UserPermissions, Boolean> checkUserPermissions(UserPermissions... userPermissions) {
        if (userPermissions == null) {
            throw new IllegalArgumentException("The specified permissions can't be a null reference");
        }
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        final Map<UserPermissions, Boolean> permissionMap = new HashMap<>();
        for (UserPermissions userPermission : userPermissions) {
            final boolean permissionGranted = AuthorisationManager.checkUserPermission(userDTO, userPermission);
            permissionMap.put(userPermission, permissionGranted);
        }
        return permissionMap;
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getUserServiceName();
    }
}