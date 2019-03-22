package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import org.junit.Test;

public class AuthorisationManagerTest {

    @Test
    public void checkUserAuthorisation_When_UserHasAuthorisation_Then_NoExceptionIsThrown() throws UserNotAuthorisedException {
        UserDTO userDTO = getUserDTOWithRole(UserRoles.SYSADMIN);
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.EDIT_ITEM);
    }

    @Test (expected = UserNotAuthorisedException.class)
    public void checkUserAuthorisation_When_UserHasNoAuthorisation_Then_ThrowException() throws UserNotAuthorisedException {
        UserDTO userDTO = getUserDTOWithRole(UserRoles.SALESPERSON);
        AuthorisationManager.checkUserAuthorisation(userDTO, UserPermissions.EDIT_ITEM);
    }

    private UserDTO getUserDTOWithRole(UserRoles userRole) {
        UserRoleDTO userRoleDTO = new UserRoleDTO(2, userRole.getRole());
        return new UserDTO(1, userRoleDTO, "Test");
    }
}