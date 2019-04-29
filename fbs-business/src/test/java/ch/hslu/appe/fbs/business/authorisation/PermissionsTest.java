package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class PermissionsTest {


    private Permissions permissions;

    @Before
    public void setUp() {
        permissions = new Permissions();
    }

    @Test
    public void getPermissions_When_MethodInvoked_Then_PermissionsAreReturned() {
        List<Permission> permissions = this.permissions.getPermissions();
        Assert.assertTrue(!permissions.isEmpty());
    }

    @Test
    public void getPermission_When_MethodInvoked_Then_UserPermissionsAreMappedAndReturned() {
        Optional<Permission> permission = this.permissions.getPermission(UserRoles.SYSADMIN);
        if (permission.isPresent()) {
            Assert.assertTrue(permission.get().getUserPermissions().contains(UserPermissions.ADMIN));
        } else {
            Assert.fail();
        }
    }
}
