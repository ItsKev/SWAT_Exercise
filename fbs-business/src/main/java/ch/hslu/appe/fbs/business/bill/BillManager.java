package ch.hslu.appe.fbs.business.bill;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.model.db.Bill;

import java.util.List;

public interface BillManager {

    List<BillDTO> getAllBills();

    BillDTO getBillById(int billId);

    List<BillDTO> getBillsByOrderId(int orderId);

    List<BillDTO> getBillsByCustomerId(int customerId);

    List<BillDTO> getRemindedBillsByCustomerId(int customerId, UserDTO userDTO) throws UserNotAuthorisedException;

    List<BillDTO> getRemindedBillsByOrderId(int orderId);

    Bill generateBill(int orderId, int price);
}
