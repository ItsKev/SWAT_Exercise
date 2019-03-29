package ch.hslu.appe.fbs.business.bill;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.bill.BillPersistor;
import ch.hslu.appe.fbs.data.reminder.ReminderPersistor;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.*;
import ch.hslu.appe.fbs.wrapper.UserWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BillManagerImplTest {

    @Mock
    private BillPersistor billPersistorMock;

    @Mock
    private ReminderPersistor reminderPersistorMock;

    private BillManager billManager;

    @Before
    public void setUp() {
        billManager = new BillManagerImpl(billPersistorMock, reminderPersistorMock);
        when(billPersistorMock.getAll()).thenReturn(createTesteeBills());
        when(billPersistorMock.getById(1)).thenReturn(Optional.of(createBill(1, 5, 10, 1)));
    }

    @Test
    public void getAllBills_When_BillsRequested_Then_ReturnAllBills() {
        List<BillDTO> bills = billManager.getAllBills();
        List<Bill> expectedBills = createTesteeBills();
        for (int i = 0; i < expectedBills.size(); i++) {
            Bill expected = expectedBills.get(i);
            BillDTO actual = bills.get(i);
            Assert.assertEquals(expected.getId(), actual.getId());
            Assert.assertEquals(expected.getOrderId(), actual.getOrderId());
            Assert.assertEquals(expected.getPrice(), actual.getPrice());
        }
    }

    @Test
    public void getBillById_When_ValidBillId_Then_ReturnCorrectBill() {
        Bill expected = createBill(1, 5, 10, 1);

        BillDTO actual = billManager.getBillById(1);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
        Assert.assertEquals(expected.getOrderId(), actual.getOrderId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBillById_When_InvalidBillId_Then_ThrowException() {
        billManager.getBillById(10);
    }

    @Test
    public void getBillsByOrderId_When_RequestBillsWithOrderId_Then_ReturnCorrectBills() {
        List<Bill> expectedBills = createTesteeBillsWithOrderId(10);
        List<BillDTO> actualBills = billManager.getBillsByOrderId(10);
        for (int i = 0; i < actualBills.size(); i++) {
            Bill expected = expectedBills.get(i);
            BillDTO actual = actualBills.get(i);
            Assert.assertEquals(expected.getId(), actual.getId());
            Assert.assertEquals(expected.getOrderId(), actual.getOrderId());
            Assert.assertEquals(expected.getPrice(), actual.getPrice());
        }
    }

    @Test
    public void getBillsByOrderId_When_RequestBillsWithIncorrectOrderId_Then_ReturnEmptyList() {
        List<BillDTO> actualBills = billManager.getBillsByOrderId(13);
        Assert.assertTrue(actualBills.isEmpty());
    }

    @Test
    public void getBillsByCustomerId_When_CustomerIsAvailable_Then_ReturnBills() {
        List<Bill> expected = createTesteeBills().stream()
                .filter(bill -> bill.getOrderByOrderId().getCustomerId() == 1)
                .collect(Collectors.toList());
        List<BillDTO> billsByCustomerId = billManager.getBillsByCustomerId(1);
        billsByCustomerId.forEach(billDTO -> Assert.assertTrue(expected.stream()
                .anyMatch(bill -> bill.getId().equals(billDTO.getId()))));
    }

    @Test
    public void getBillsByCustomerId_When_CustomerIsNotAvailable_Then_ReturnEmptyList() {
        Assert.assertTrue(billManager.getBillsByCustomerId(11).isEmpty());
    }

    @Test
    public void getRemindedBillsByCustomerId() throws UserNotAuthorisedException {
        List<BillDTO> actual = billManager.getRemindedBillsByCustomerId(1,
                new UserWrapper().dtoFromEntity(createUser()));
    }

    @Test
    public void getRemindedBillsByOrderId() {
    }

    @Test
    public void generateBill() {
    }

    private List<Bill> createTesteeBills() {
        List<Bill> bills = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            bills.add(createBill(i, i * 5, i * 10, i % 2));
        }
        return bills;
    }

    private List<Bill> createTesteeBillsWithOrderId(int orderId) {
        List<Bill> bills = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            bills.add(createBill(i, orderId, i * 10, i));
        }
        return bills;
    }

    private Bill createBill(int billId, int orderId, int price, int customerId) {
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setOrderByOrderId(createOrder(orderId, customerId));
        bill.setPrice(price * 10);
        bill.setRemindersById(createRemindersForBill(billId));
        return bill;
    }

    private Order createOrder(int orderId, int customerId) {
        Order order = new Order();
        order.setId(orderId);
        order.setDateTime(Timestamp.from(Instant.now()));
        order.setOrderItemsById(createOrderItems(orderId));
        order.setCustomerByCustomerId(createCustomer(customerId));
        order.setCustomerId(customerId);
        order.setUserId(customerId);
        order.setUserByUserId(createUser());
        OrderState orderState = new OrderState();
        orderState.setId(1);
        orderState.setState("Open");
        order.setOrderStateByOrderState(orderState);
        order.setOrderState(1);
        return order;
    }

    private User createUser() {
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
        return user;
    }

    private Customer createCustomer(int customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setPrename("Test");
        customer.setSurname("Testee");
        customer.setPlz(6206);
        customer.setAdress("Testadress 12");
        customer.setCity("Testhausen");
        return customer;
    }

    private List<OrderItem> createOrderItems(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setPrice(10);
            orderItem.setQuantity(10);
            Item item = new Item();
            item.setId(i);
            item.setArtNr("1");
            item.setName("Test");
            orderItem.setItemByItemId(item);
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private List<Reminder> createRemindersForBill(int billId) {
        List<Reminder> reminders = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Reminder reminder = new Reminder();
            reminder.setId(i);
            reminder.setBillId(billId);
            reminders.add(reminder);
        }
        return reminders;
    }
}