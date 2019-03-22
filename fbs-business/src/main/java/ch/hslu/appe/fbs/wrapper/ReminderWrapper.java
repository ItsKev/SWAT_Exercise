package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.ReminderDTO;
import ch.hslu.appe.fbs.model.db.Reminder;

public final class ReminderWrapper implements Wrappable<Reminder, ReminderDTO> {

    private final BillWrapper billWrapper;

    public ReminderWrapper() {
        this.billWrapper = new BillWrapper();
    }

    @Override
    public ReminderDTO dtoFromEntity(Reminder reminder) {
        return new ReminderDTO(
                reminder.getId(),
                reminder.getBillId(),
                billWrapper.dtoFromEntity(reminder.getBillByBillId())
        );
    }

    @Override
    public Reminder entityFromDTO(ReminderDTO reminderDTO) {
        final Reminder rmd = new Reminder();
        if (reminderDTO.getId() != -1) {
            rmd.setId(reminderDTO.getId());
        }
        rmd.setBillId(reminderDTO.getBillId());
        return rmd;
    }
}
