package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.util.Objects;

public final class ReminderDTO implements Serializable {
    private final Integer id;
    private final Integer billId;
    private final BillDTO billByBillId;

    public ReminderDTO(Integer id, Integer billId, BillDTO billByBillId) {
        this.id = id;
        this.billId = billId;
        this.billByBillId = billByBillId;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getBillId() {
        return this.billId;
    }

    public BillDTO getBillByBillId() {
        return this.billByBillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReminderDTO)) return false;
        ReminderDTO reminderDTO = (ReminderDTO) o;
        return Objects.equals(this.billId, reminderDTO.billId) &&
                Objects.equals(this.billByBillId, reminderDTO.billByBillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.billId, this.billByBillId);
    }
}
