package serveur;

import JDBC.JDBC.Modele;
import donnees_bloquees.EtablissementSup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServeur extends Remote {
    void enregistrerServiceRestaurant(Modele modele) throws RemoteException;

    void enregistrerServiceEtablissementSup(EtablissementSup etablissementSup) throws RemoteException;
}