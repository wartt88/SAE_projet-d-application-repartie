package serveur;

import JDBC.JDBC.InterfaceRestaurant;
import donnees_bloquees.InterfaceEtablissementSup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServeur extends Remote {
    void enregistrerServiceRestaurant(InterfaceRestaurant modeleData) throws RemoteException;

    void enregistrerServiceEtablissementSup(InterfaceEtablissementSup etablissementSup) throws RemoteException;
}