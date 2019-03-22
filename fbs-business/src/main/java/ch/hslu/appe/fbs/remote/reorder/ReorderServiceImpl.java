package ch.hslu.appe.fbs.remote.reorder;

import ch.hslu.appe.fbs.business.reorder.ReorderManager;
import ch.hslu.appe.fbs.business.reorder.ReorderManagerFactory;
import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ReorderService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.remote.session.UserSessionDictionary;

import java.rmi.RemoteException;
import java.util.List;

public class ReorderServiceImpl implements ReorderService {

    private final ReorderManager reorderManager;

    public ReorderServiceImpl() {
        this.reorderManager = ReorderManagerFactory.getReorderManager();
    }

    @Override
    public void markReorderAsDelivered(int reorderId) throws RemoteException, UserNotAuthorisedException {
        UserDTO userSession = UserSessionDictionary.getUserSession();
        this.reorderManager.markReorderAsDelivered(reorderId, userSession);
    }

    @Override
    public List<ReorderDTO> getAllReorders() throws RemoteException, UserNotAuthorisedException {
        UserDTO userSession = UserSessionDictionary.getUserSession();
        return this.reorderManager.getAllReorders(userSession);
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getReorderServiceName();
    }
}
