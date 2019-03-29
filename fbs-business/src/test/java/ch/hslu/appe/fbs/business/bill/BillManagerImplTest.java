package ch.hslu.appe.fbs.business.bill;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.data.bill.BillPersistor;
import ch.hslu.appe.fbs.data.reminder.ReminderPersistor;
import ch.hslu.appe.fbs.model.db.Bill;
import ch.hslu.appe.fbs.model.db.Reminder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
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
        when(billPersistorMock.getById(1)).thenReturn(Optional.of(createBill(1, 10)));
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
        Bill expected = createBill(1, 10);

        BillDTO actual = billManager.getBillById(1);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
        Assert.assertEquals(expected.getOrderId(), actual.getOrderId());
    }

    @Test (expected = IllegalArgumentException.class)
    public void getBillById_When_InvalidBillId_Then_ThrowException() {
        billManager.getBillById(10);
    }

    @Test
    public void getBillsByOrderId() {
    }

    @Test
    public void getBillsByCustomerId() {
    }

    @Test
    public void getRemindedBillsByCustomerId() {
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
            bills.add(createBill(i, i * 10));
        }
        return bills;
    }

    private Bill createBill(int billId, int price) {
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setPrice(price * 10);
        bill.setRemindersById(createRemindersForBill(billId));
        return bill;
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