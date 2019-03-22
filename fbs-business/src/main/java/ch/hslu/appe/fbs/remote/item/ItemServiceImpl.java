package ch.hslu.appe.fbs.remote.item;

import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.business.item.ItemManagerFactory;
import ch.hslu.appe.fbs.common.dto.ItemDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.remote.session.UserSessionDictionary;

import java.rmi.RemoteException;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private ItemManager itemManager;

    public ItemServiceImpl() {
        this.itemManager = ItemManagerFactory.getItemManager();
    }

    @Override
    public List<ItemDTO> getAllItems() throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = UserSessionDictionary.getUserSession();
        return this.itemManager.getAllItems(userDTO);
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getItemServiceName();
    }

}
