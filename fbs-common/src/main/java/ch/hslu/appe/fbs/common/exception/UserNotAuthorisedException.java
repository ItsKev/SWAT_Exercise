package ch.hslu.appe.fbs.common.exception;

import ch.hslu.appe.fbs.common.permission.UserPermissions;

import java.util.Arrays;

public final class UserNotAuthorisedException extends Exception {

    public UserNotAuthorisedException(UserPermissions... userPermissions) {
        super("The current user is not authorised to execute the following actions: " + Arrays.toString(userPermissions));
    }
}
