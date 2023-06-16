package serveur;

import java.rmi.RemoteException;

/**
 * Classe représentant le serveur qui offre des services de restaurant.
 */
public class Serveur implements InterfaceServeur {
    Object Restaurant;

    /**
     * Enregistre le service de restaurant sur le serveur.
     *
     * @throws RemoteException si une erreur liée à la communication RMI se produit.
     */
    @Override
    public void enregistrerServiceRestaurant() throws RemoteException {
        System.out.println("Enregistrement du service Restaurant");
        this.Restaurant = new Object();
    }

    /**
     * Récupère le service de restaurant enregistré sur le serveur.
     *
     * @return une chaîne de caractères représentant le service de restaurant.
     * @throws RemoteException       si une erreur liée à la communication RMI se produit.
     * @throws ServiceNotBindException si le service de restaurant n'est pas enregistré actuellement sur le serveur.
     */
    public String getRestaurant() throws RemoteException, ServiceNotBindException {
        if (this.Restaurant == null)
            throw new ServiceNotBindException("Le service Restaurant n'est pas enregistré actuellement sur le serveur");
        System.out.println("Récupération du service Restaurant");
        return "Restaurant";
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
}
