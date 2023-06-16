package serveur;

import JDBC.JDBC.Modele;
import JDBC.JDBC.Restaurant;
import donnees_bloquees.EtablissementSup;
import org.json.JSONArray;
import org.json.JSONObject;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe représentant le serveur qui offre des services de restaurant.
 */
public class Serveur implements InterfaceServeur {
    Modele restaurant;
    EtablissementSup etablissementSup;

    /**
     * Enregistre le service de restaurant sur le serveur.
     *
     * @throws RemoteException si une erreur liée à la communication RMI se produit.
     */
    @Override
    public void enregistrerServiceRestaurant(Modele restaurant) throws RemoteException {
        System.out.println("Enregistrement du service Restaurant");
        this.restaurant = restaurant;
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
     * @throws RemoteException       si une erreur liée à la communication RMI se produit.
     * @throws ServiceNotBindException si le service de restaurant n'est pas enregistré actuellement sur le serveur.
     */
    public String getRestaurant() throws RemoteException, ServiceNotBindException {
        if (this.restaurant == null)
            throw new ServiceNotBindException("Le service Restorant n'est pas enregistré actuellement sur le serveur");
        System.out.println("Récupération du service Restaurant");
        try {
            return restaurant.getListeRestaurant();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
