package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.userrole.UserRoles;

import java.util.*;

public final class AuthorisationManager {

    private static final Map<String, List<UserPermissions>> PERMISSIONS = new HashMap<>();

    static {
        PERMISSIONS.put(UserRoles.SYSADMIN.getRole(), Collections.singletonList(UserPermissions.ADMIN));
        PERMISSIONS.put(UserRoles.DATATYPIST.getRole(),
                Arrays.asList(UserPermissions.ADD_ITEM, UserPermissions.EDIT_ITEM, UserPermissions.DELETE_ITEM, UserPermissions.MARK_REORDER_DELIVERED,
                        UserPermissions.GET_CUSTOMER, UserPermissions.GET_ALL_CUSTOMERS, UserPermissions.GET_ORDER, UserPermissions.GET_ALL_ORDERS,
                        UserPermissions.GET_REORDER, UserPermissions.GET_ALL_REORDERS, UserPermissions.GET_REMINDER, UserPermissions.GET_ALL_REMINDERS,
                        UserPermissions.GET_ITEM, UserPermissions.GET_ALL_ITEMS));
        PERMISSIONS.put(UserRoles.BRANCHMANAGER.getRole(),
                Arrays.asList(UserPermissions.GET_ORDER, UserPermissions.GET_ALL_ORDERS, UserPermissions.GET_REORDER, UserPermissions.GET_ALL_REORDERS,
                        UserPermissions.GET_REMINDER, UserPermissions.GET_ALL_REMINDERS, UserPermissions.VIEW_LOGS));
        PERMISSIONS.put(UserRoles.SALESPERSON.getRole(),
                Arrays.asList(UserPermissions.CREATE_CUSTOMER, UserPermissions.EDIT_CUSTOMER, UserPermissions.DELETE_CUSTOMER,
                        UserPermissions.CREATE_ORDER, UserPermissions.EDIT_ORDER, UserPermissions.CANCEL_ORDER,
                        UserPermissions.GET_CUSTOMER, UserPermissions.GET_ALL_CUSTOMERS, UserPermissions.GET_ORDER, UserPermissions.GET_ALL_ORDERS,
                        UserPermissions.GET_REORDER, UserPermissions.GET_ALL_REORDERS, UserPermissions.GET_REMINDER, UserPermissions.GET_ALL_REMINDERS,
                        UserPermissions.GET_ITEM, UserPermissions.GET_ALL_ITEMS));
    }

    public static void checkUserAuthorisation(final UserDTO userDTO, final UserPermissions userPermission) throws UserNotAuthorisedException {
        if (userDTO == null) {
            throw new IllegalArgumentException("user object can't be a null reference");
        }
        if (userPermission == null) {
            throw new IllegalArgumentException("permission object can't be a null reference");
        }
        final boolean permissionGranted = checkUserPermission(userDTO, userPermission);
        if (!permissionGranted) {
            throw new UserNotAuthorisedException(userPermission);
        }
    }

    public static boolean checkUserPermission(final UserDTO userDTO, final UserPermissions userPermission) {
        if (userDTO == null) {
            throw new IllegalArgumentException("user object can't be a null reference");
        }
        if (userPermission == null) {
            throw new IllegalArgumentException("permission object can't be a null reference");
        }
        final String userRole = userDTO.getUserRole().getUserRole();
        final List<UserPermissions> userPermissions = PERMISSIONS.get(userRole);
        return userPermissions.contains(userPermission) || userPermissions.contains(UserPermissions.ADMIN);
    }
}
