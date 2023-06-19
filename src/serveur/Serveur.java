package serveur;

import JDBC.JDBC.InterfaceModeleData;
import donnees_bloquees.EtablissementSup;

import java.rmi.RemoteException;


/**
 * Classe représentant le serveur qui offre des services de restaurant.
 */
public class Serveur implements InterfaceServeur {
    InterfaceModeleData restaurant;
    EtablissementSup etablissementSup;

    /**
     * Enregistre le service de restaurant sur le serveur.
     *
     * @throws RemoteException si une erreur liée à la communication RMI se produit.
     */
    @Override
    public void enregistrerServiceRestaurant(InterfaceModeleData modeleData) throws RemoteException {
        System.out.println("Enregistrement du service Restaurant");
        this.restaurant = modeleData;
    }

    @Override
    public void enregistrerServiceEtablissementSup(EtablissementSup etablissementSup) throws RemoteException {
        System.out.println("Enregistrement du service EtablissementSup");
        this.etablissementSup = etablissementSup;
    }

    /**
     * Récupère le service de restaurant enregistré sur le serveur.
     *
     * @return une chaîne de caractères représentant le service de restaurant.
     * @throws RemoteException         si une erreur liée à la communication RMI se produit.
     * @throws ServiceNotBindException si le service de restaurant n'est pas enregistré actuellement sur le serveur.
     */
    public String getRestaurant() throws RemoteException, ServiceNotBindException {
        if (this.restaurant == null)
            throw new ServiceNotBindException("Le service Restorant n'est pas enregistré actuellement sur le serveur");
        System.out.println("Récupération du service Restaurant");
        System.out.println(this.restaurant.getRestaurants());
        return restaurant.getRestaurants();
    }

    /**
     * Réserve le service de restaurant.
     *
     * @return une chaîne de caractères indiquant que la réservation a été effectuée avec succès.
     * @throws RemoteException si une erreur liée à la communication RMI se produit.
     */
    public String reserverRestaurant() throws RemoteException {
        System.out.println("Réservation du service Restaurant");
        return "Reservation OK";
    }

    public EtablissementSup getEtablissementSup() {
        return this.etablissementSup;
    }
}
