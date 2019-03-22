package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.model.db.Bill;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BillWrapperTest {

    private BillWrapper billWrapper;

    private final int billId = 1;
    private final int billPrice = 10;
    private final int orderId = 2;

    @Before
    public void setUp() {
        billWrapper = new BillWrapper();
    }

    @Test
    public void dtoFromEntity_When_BillEntity_Then_ReturnDTO() {
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setOrderId(orderId);
        bill.setPrice(billPrice);
        BillDTO expected = new BillDTO(billId, orderId, billPrice);

        BillDTO billDTO = billWrapper.dtoFromEntity(bill);

        Assert.assertEquals(expected.getId(), billDTO.getId());
        Assert.assertEquals(expected.getOrderId(), billDTO.getOrderId());
        Assert.assertEquals(expected.getPrice(), billDTO.getPrice());
    }

    @Test
    public void entityFromDTO_When_DTO_Then_ReturnBillEntity() {
        BillDTO billDTO = new BillDTO(billId, orderId, billPrice);
        Bill expected = new Bill();
        expected.setId(billId);
        expected.setOrderId(orderId);
        expected.setPrice(billPrice);

        Bill bill = billWrapper.entityFromDTO(billDTO);

        Assert.assertEquals(expected.getId(), bill.getId());
        Assert.assertEquals(expected.getOrderId(), bill.getOrderId());
        Assert.assertEquals(expected.getPrice(), bill.getPrice());
    }
}