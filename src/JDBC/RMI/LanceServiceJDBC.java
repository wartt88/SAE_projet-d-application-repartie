package JDBC.RMI;

import JDBC.JDBC.InterfaceRestaurant;
import JDBC.JDBC.Restaurant;
import serveur.InterfaceServeur;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LanceServiceJDBC {
    public static void main(String[] args) {
        if (args.length != 4)
        {
            System.out.println("Usage: java LanceServiceJDBC <ip> <port> usernameDB passwordDB");
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
            Restaurant restaurant = new Restaurant(args[2],args[3]);

            // On crée un service Modele Data qui permet d'accéder au donnée de la BD
            InterfaceRestaurant serviceModeleDate =(InterfaceRestaurant) UnicastRemoteObject.exportObject(restaurant,0);

            serveur.enregistrerServiceRestaurant(serviceModeleDate);
            System.out.println("Le service restaurant est lancé et ajouté au serveur.");
        } catch (RemoteException | NotBoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
