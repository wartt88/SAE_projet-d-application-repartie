package serveur;

import JDBC.JDBC.InterfaceModeleData;
import JDBC.JDBC.Modele;
import JDBC.JDBC.ModeleData;
import donnees_bloquees.EtablissementSup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServeur extends Remote {
    void enregistrerServiceRestaurant(InterfaceModeleData modeleData) throws RemoteException;

    void enregistrerServiceEtablissementSup(EtablissementSup etablissementSup) throws RemoteException;
}