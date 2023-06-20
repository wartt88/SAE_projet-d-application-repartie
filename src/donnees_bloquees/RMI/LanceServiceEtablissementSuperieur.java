package donnees_bloquees.RMI;

import donnees_bloquees.EtablissementSup;
import serveur.InterfaceServeur;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            InterfaceServeur serveur = (InterfaceServeur) registry.lookup("ServeurCentrale");
            //Creation de l'objet EtablissementSup pour l'enregistrer sur le serveur

            Scanner sc = new Scanner(System.in);
            ArrayList<String> res = new ArrayList<String>(List.of(new String[]{"Oui", "OUI", "O", "o", "oui"}));
            // on pose la question
            System.out.println("Est-ce une utilisation sur un poste de l'IUT ? (oui/NON)");
            String retour = sc.nextLine();
            boolean iut = res.contains(retour);

            sc.close();

            EtablissementSup etablissementSup = new EtablissementSup(iut);
            //Enregistrement du service
            serveur.enregistrerServiceEtablissementSup(etablissementSup);
            System.out.println("On lance le service etablissement superieur et on l'enregistre au serveur.");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

    }
}
