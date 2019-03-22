package ch.hslu.appe.fbs.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FBSService extends Remote {

    String getServiceName() throws RemoteException;

}
