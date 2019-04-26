package ch.hslu.appe.fbs.business.item;

import ch.hslu.appe.fbs.business.stock.Stock;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.item.ItemPersistor;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistor;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.Item;
import ch.hslu.appe.fbs.model.db.Reorder;
import ch.hslu.appe.fbs.model.db.User;
import ch.hslu.appe.fbs.model.db.UserRole;
import ch.hslu.appe.fbs.wrapper.ItemWrapper;
import ch.hslu.appe.fbs.wrapper.UserWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ItemManagerImplTest {

    @Mock
    private ItemPersistor itemPersistorMock;

    @Mock
    private ReorderPersistor reorderPersistorMock;

    @Mock
    private Stock centralStockMock;

    private ItemManager itemManager;

    private Item testee;

    @Before
    public void setUp() {
        itemManager = new ItemManagerImpl(itemPersistorMock, reorderPersistorMock, centralStockMock);
        Mockito.when(itemPersistorMock.getAllItems()).thenReturn(createItems());
        testee = createItem(2);
        Mockito.when(itemPersistorMock.getItemById(2)).thenReturn(Optional.of(testee));
        Mockito.when(reorderPersistorMock.getByItemId(2)).thenReturn(createReorder());
    }

    @Test
    public void getAllItems_When_UserAuthorized_Then_GetAllItems() throws UserNotAuthorisedException {
        List<ItemDTO> items = itemManager.getAllItems(createUser());
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals("000" + i, items.get(i).getArtNr());
        }
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getAllItems_When_UserNotAuthorized_Then_ThrowException() throws UserNotAuthorisedException {
        itemManager.getAllItems(createUnauthorizedUser());
    }

    @Test
    public void updateItemStock_When_ItemDoesNotNeedReorder_Then_UpdateItemStockWithoutReorder() {
        Mockito.when(itemPersistorMock.getItemById(2)).thenReturn(Optional.of(createItemWithoutReorder(2)));
        itemManager.updateItemStock(new ItemWrapper().dtoFromEntity(createItemWithoutReorder(2)), 5);
        Mockito.verify(itemPersistorMock, Mockito.times(1)).updateStock(Mockito.any(Item.class));
        Mockito.verify(reorderPersistorMock, Mockito.times(0)).save(Mockito.any(Reorder.class));
    }

    @Test
    public void updateItemStock_When_ItemDoesNeedReorder_Then_UpdateItemStockReorder() {
        itemManager.updateItemStock(new ItemWrapper().dtoFromEntity(createItem(2)), 20);
        Mockito.verify(itemPersistorMock, Mockito.times(1)).updateStock(Mockito.any(Item.class));
        Mockito.verify(reorderPersistorMock, Mockito.times(1)).save(Mockito.any(Reorder.class));
    }

    @Test
    public void updateMinLocalStock_When_MethodIsInvoked_Then_StockGetsUpdated() {
        itemManager.updateMinLocalStock(new ItemWrapper().dtoFromEntity(createItem(2)), 20);
        Mockito.verify(itemPersistorMock, Mockito.times(1)).updateStock(Mockito.any(Item.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMinLocalStock_When_MethodIsInvokedWithAWrongMinLocalStock_Then_ThrowException() {
        itemManager.updateMinLocalStock(new ItemWrapper().dtoFromEntity(createItem(2)), -1);
        Mockito.verify(itemPersistorMock, Mockito.times(0)).updateStock(Mockito.any(Item.class));
    }

    @Test
    public void getAvailableItemQuantity_When_ItemExists_Then_ReturnQuantity() {
        int expected = createItem(2).getLocalStock();
        int actual = itemManager.getAvailableItemQuantity(2);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAvailableItemQuantity_When_ItemDoesNotExists_Then_ThrowException() {
        itemManager.getAvailableItemQuantity(-1);
    }

    @Test
    public void refillItemStock_When_ItemIsAvailable_Then_UpdateStock() {
        itemManager.refillItemStock(new ItemWrapper().dtoFromEntity(createItem(2)), 10);
        Mockito.verify(itemPersistorMock, Mockito.times(1)).updateStock(Mockito.any(Item.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void refillItemStock_When_ItemIsNotAvailable_Then_ThrowException() {
        itemManager.refillItemStock(new ItemWrapper().dtoFromEntity(createItem(-1)), 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refillItemStock_When_QuantityIsNotValid_Then_ThrowException() {
        itemManager.refillItemStock(new ItemWrapper().dtoFromEntity(createItem(2)), -1);
    }

    @Test
    public void getItem_When_ItemAvailable_Then_ReturnItem() {
        ItemDTO expected = new ItemWrapper().dtoFromEntity(createItem(2));
        ItemDTO actual = itemManager.getItem(2);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getArtNr(), actual.getArtNr());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getItem_When_ItemNotAvailable_Then_ThrowException() {
        itemManager.getItem(-1);
    }

    private List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add(createItem(i));
        }
        return items;
    }

    private Item createItem(int id) {
        Item item = new Item();
        item.setName("TestItem");
        item.setArtNr("000" + id);
        item.setId(id);
        item.setLocalStock(10);
        item.setVirtualLocalStock(5);
        item.setMinLocalStock(10);
        item.setPrice(5);
        return item;
    }

    private Item createItemWithoutReorder(int id) {
        Item item = new Item();
        item.setName("TestItem");
        item.setArtNr("000" + id);
        item.setId(id);
        item.setLocalStock(15);
        item.setVirtualLocalStock(15);
        item.setMinLocalStock(10);
        item.setPrice(5);
        return item;
    }


    private UserDTO createUser() {
        User user = new User();
        user.setId(1);
        user.setPassword("");
        user.setUserName("Test");
        user.setDeleted((byte) 0);
        UserRole userRole = new UserRole();
        userRole.setId(1);
        userRole.setRoleName(UserRoles.SYSADMIN.getRole());
        user.setUserRoleByUserRole(userRole);
        user.setUserRole(1);
        return new UserWrapper().dtoFromEntity(user);
    }

    private UserDTO createUnauthorizedUser() {
        User user = new User();
        user.setId(1);
        user.setPassword("");
        user.setUserName("Test");
        user.setDeleted((byte) 0);
        UserRole userRole = new UserRole();
        userRole.setId(10);
        userRole.setRoleName(UserRoles.BRANCHMANAGER.getRole());
        user.setUserRoleByUserRole(userRole);
        user.setUserRole(1);
        return new UserWrapper().dtoFromEntity(user);
    }

    private List<Reorder> createReorder() {
        Reorder reorder = new Reorder();
        reorder.setItemId(1);
        reorder.setQuantity(2);
        reorder.setReorderDate(Timestamp.from(Instant.now()));
        reorder.setItemByItemId(testee);
        List<Reorder> reorders = new ArrayList<>();
        reorders.add(reorder);
        return reorders;
    }
}