package ch.hslu.appe.fbs.business.user;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.data.user.UserPersistor;
import ch.hslu.appe.fbs.model.db.User;
import ch.hslu.appe.fbs.model.db.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerImplTest {

    @Mock
    private UserPersistor userPersistorMock;

    private UserManagerImpl userManager;

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
    public void loginUser() {
        UserDTO loginUser = userManager.loginUser("Test", "1234");
        Assert.assertEquals("Test", loginUser.getUserName());
    }
}