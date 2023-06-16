package donnees_bloquees.RMI;

import donnees_bloquees.EtablissementSup;
import serveur.InterfaceServeur;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LanceServiceEtablissementSuperieur {
    public static void main(String[] args) {
        // Verification des arguments
        if (args.length != 2) {
            System.out.println("Usage: java LanceServiceEtablissementSuperieur <ip> <port>");
            System.exit(1);
        }
        // Recuperation du registre du serveur
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            InterfaceServeur serveur = (InterfaceServeur) registry.lookup("serveur");
            //Creation de l'objet EtablissementSup pour l'enregistrer sur le serveur
            EtablissementSup etablissementSup = new EtablissementSup();
            //Enregistrement du service
            serveur.enregistrerServiceEtablissementSup(etablissementSup);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

    }
}
