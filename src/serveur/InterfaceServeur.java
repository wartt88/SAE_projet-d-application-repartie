package serveur;

import JDBC.JDBC.Modele;
import donnees_bloquees.EtablissementSup;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface InterfaceServeur extends Remote {
    void enregistrerServiceRestaurant(Modele modele) throws RemoteException, SQLException;

    void enregistrerServiceEtablissementSup(EtablissementSup etablissementSup) throws RemoteException;
}