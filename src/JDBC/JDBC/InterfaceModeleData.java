package JDBC.JDBC;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceModeleData extends Remote
{
    public String getRestaurants() throws RemoteException;
}
