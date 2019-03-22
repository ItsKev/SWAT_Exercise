package ch.hslu.appe.fbs.remote.rmi;

import ch.hslu.appe.fbs.common.rmi.FBSService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public final class RmiConnector {
    private static Registry registry = null;

    public static void tryBindServiceToRegistry(FBSService fbsService) {
        try {
            bindServiceToRegistry(fbsService);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    private static void bindServiceToRegistry(FBSService fbsService) throws RemoteException, AlreadyBoundException {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        final FBSService serviceStub = (FBSService) UnicastRemoteObject.exportObject(fbsService, 0);
        if (registry == null) {
            registry = LocateRegistry.createRegistry(1099);
        }
        registry.bind(serviceStub.getServiceName(), serviceStub);
    }
}
