package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServeur extends Remote {
    void enregistrerServiceRestaurant() throws RemoteException;
}