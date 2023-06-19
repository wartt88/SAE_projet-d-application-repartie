package JDBC.RMI;

import JDBC.JDBC.InterfaceModeleData;
import JDBC.JDBC.ModeleData;
import serveur.InterfaceServeur;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LanceServiceJDBC {
    public static void main(String[] args) {

        if (args.length != 4) {
            System.out.println("Usage: java LanceServiceEtablissementSuperieur <ip> <port> usernameDB passwordDB");
            System.exit(1);
        }
        // Recuperation du registre du serveur
        Registry registry = null;
        try {
            // on récupère l'annuaire
            registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));

            //on récupere le serveur centrale, pour pouvoir enregistrer le modele Data
            InterfaceServeur serveur = (InterfaceServeur) registry.lookup("ServeurCentrale");

            // on crée un objet Modele Data avec les login , username et password
            ModeleData modeleData = new ModeleData(args[2],args[3]);

            // On crée un service Modele Data qui permet d'accéder au donnée de la BD
            InterfaceModeleData serviceModeleDate =(InterfaceModeleData) UnicastRemoteObject.exportObject(modeleData,0);

            // L'erreur est ici !!!!
            serveur.enregistrerServiceRestaurant(serviceModeleDate);
        } catch (RemoteException | NotBoundException e)
        {
            throw new RuntimeException(e);
        }


    }
}
