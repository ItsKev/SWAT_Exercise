package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.userrole.UserRoles;

import java.util.*;

public final class AuthorisationManager {

    private Permissions permissions;

    public AuthorisationManager() {
        permissions = new Permissions();
    }

    public void checkUserAuthorisation(final UserDTO userDTO, final UserPermissions userPermission) throws UserNotAuthorisedException {
        final boolean permissionGranted = checkUserPermission(userDTO, userPermission);
        if (!permissionGranted) {
            throw new UserNotAuthorisedException(userPermission);
        }
    }

    public boolean checkUserPermission(final UserDTO userDTO, final UserPermissions userPermission) {
        final UserRoles userRole = UserRoles.fromString(userDTO.getUserRole().getUserRole());
        Optional<Permission> permission = permissions.getPermission(userRole);
        if (permission.isPresent()) {
            final List<UserPermissions> userPermissions = permission.get().getUserPermissions();
            return userPermissions.contains(userPermission) || userPermissions.contains(UserPermissions.ADMIN);
        }
        throw new IllegalArgumentException("UserRole not present");
    }
}
