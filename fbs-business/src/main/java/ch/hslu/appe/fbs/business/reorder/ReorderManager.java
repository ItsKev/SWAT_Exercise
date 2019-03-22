package ch.hslu.appe.fbs.business.reorder;

import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;

import java.util.List;

public interface ReorderManager {
    void markReorderAsDelivered(final int reorderId, final UserDTO userDTO) throws UserNotAuthorisedException;

    List<ReorderDTO> getAllReorders(final UserDTO userDTO) throws UserNotAuthorisedException;
}
