package serveur;

import java.rmi.RemoteException;

public class Serveur implements InterfaceServeur {
    @Override
    public void enregistrerServiceRestaurant() throws RemoteException {
        System.out.println("Enregistrement du service Restorant");
    }

    public String getRestaurant() throws RemoteException {
        System.out.println("Récupération du service Restorant");
        return "Restorant";
    }

    public String reserverRestaurant() throws RemoteException {
        System.out.println("Réservation du service Restorant");
        return "Reservation OK";
    }
}
