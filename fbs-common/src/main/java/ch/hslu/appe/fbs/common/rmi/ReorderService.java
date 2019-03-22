package ch.hslu.appe.fbs.common.rmi;

import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;

import java.rmi.RemoteException;
import java.util.List;

public interface ReorderService extends FBSService {

    void markReorderAsDelivered(final int reorderId) throws RemoteException, UserNotAuthorisedException;

    List<ReorderDTO> getAllReorders() throws RemoteException, UserNotAuthorisedException;
}
