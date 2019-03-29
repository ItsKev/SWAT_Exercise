package ch.hslu.appe.fbs.business.user;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.data.user.UserPersistor;
import ch.hslu.appe.fbs.model.db.User;
import ch.hslu.appe.fbs.model.db.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerImplTest {

    @Mock
    private UserPersistor userPersistorMock;

    private UserManager userManager;

    @Before
    public void setUp() {
        userManager = new UserManagerImpl(userPersistorMock);
        User user = new User();
        user.setUserName("Test");
        user.setPassword("1234");
        user.setId(1);
        UserRole userRole = new UserRole();
        userRole.setId(1);
        userRole.setRoleName("TestRole");
        user.setUserRoleByUserRole(userRole);
        when(userPersistorMock.getByName("Test")).thenReturn(Optional.of(user));
    }

    @Test
    public void loginUser_When_LoginSuccessful_Then_GetUserDTO() {
        UserDTO loginUser = userManager.loginUser("Test", "1234");
        Assert.assertEquals("Test", loginUser.getUserName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_When_WrongPassword_Then_ThrowException() {
        userManager.loginUser("Test", "1111");
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_When_UserNotPresent_Then_ThrowException() {
        userManager.loginUser("Tom", "1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_When_UserNameIsNull_Then_ThrowException() {
        userManager.loginUser(null, "1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_When_UserNameIsEmpty_Then_ThrowException() {
        userManager.loginUser(null, "1234");
    }
}