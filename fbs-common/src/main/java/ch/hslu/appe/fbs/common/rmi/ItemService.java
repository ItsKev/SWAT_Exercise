package ch.hslu.appe.fbs.common.rmi;

import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;

import java.rmi.RemoteException;
import java.util.List;

public interface ItemService extends FBSService {

    List<ItemDTO> getAllItems() throws RemoteException, UserNotAuthorisedException;

}
