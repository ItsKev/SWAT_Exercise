package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.model.db.Bill;

public final class BillWrapper implements Wrappable<Bill, BillDTO> {
    @Override
    public BillDTO dtoFromEntity(Bill bill) {
        return new BillDTO(
                bill.getId(),
                bill.getOrderId(),
                bill.getPrice()
        );
    }

    @Override
    public Bill entityFromDTO(BillDTO billDTO) {
        final Bill bill = new Bill();
        if (billDTO.getId() != -1) {
            bill.setId(billDTO.getId());
        }
        bill.setOrderId(billDTO.getOrderId());
        bill.setPrice(billDTO.getPrice());
        return bill;
    }
}
