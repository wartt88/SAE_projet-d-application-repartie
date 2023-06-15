package serveur;

import java.rmi.RemoteException;

public class Serveur implements InterfaceServeur {
    Object Restaurant;

    @Override
    public void enregistrerServiceRestaurant() throws RemoteException {
        System.out.println("Enregistrement du service Restorant");
        this.Restaurant = new Object();
    }

    public String getRestaurant() throws RemoteException, ServiceNotBindException {
        if (this.Restaurant == null)
            throw new ServiceNotBindException("Le service Restorant n'est pas enregistré actuellement sur le serveur");
        System.out.println("Récupération du service Restorant");
        return "Restorant";
    }

    public String reserverRestaurant() throws RemoteException {
        System.out.println("Réservation du service Restorant");
        return "Reservation OK";
    }
}
