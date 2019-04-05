package ch.hslu.appe.fbs.remote.rmi;

import ch.hslu.appe.fbs.common.rmi.FBSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public final class RmiConnector {

    private static final Logger LOGGER = LogManager.getLogger(RmiConnector.class);

    private RmiConnector() {
    }

    private static Registry registry = null;

    public static void tryBindServiceToRegistry(FBSService fbsService) {
        try {
            bindServiceToRegistry(fbsService);
        } catch (RemoteException | AlreadyBoundException e) {
            LOGGER.error(e);
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
