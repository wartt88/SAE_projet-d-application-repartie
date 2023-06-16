package JDBC.RMI;

import JDBC.JDBC.Modele;
import serveur.InterfaceServeur;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LanceServiceJDBC {
    public static void main(String[] args) {

        if (args.length != 4) {
            System.out.println("Usage: java LanceServiceEtablissementSuperieur <ip> <port> usernameDB passwordDB");
            System.exit(1);
        }
        // Recuperation du registre du serveur
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            InterfaceServeur serveur = (InterfaceServeur) registry.lookup("serveur");
            //Creation de l'objet EtablissementSup pour l'enregistrer sur le serveur
            Modele modele = new Modele(args[2], args[3]);
            //Enregistrement du service
            serveur.enregistrerServiceRestaurant(modele);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }


    }
}
