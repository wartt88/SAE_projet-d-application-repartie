package serveur;

import JDBC.JDBC.InterfaceRestaurant;
import donnees_bloquees.InterfaceEtablissementSup;
import java.rmi.RemoteException;


/**
 * Classe représentant le serveur qui offre des services de restaurant.
 */
public class Serveur implements InterfaceServeur {
    InterfaceRestaurant restaurant;
    InterfaceEtablissementSup etablissementSup;


    /**
     * Enregistre le service de restaurant sur le serveur.
     *
     * @throws RemoteException si une erreur liée à la communication RMI se produit.
     */
    @Override
    public void enregistrerServiceRestaurant(InterfaceRestaurant modeleData) throws RemoteException {
        System.out.println("Enregistrement du service Restaurant");
        this.restaurant = modeleData;
    }

    /**
     * Enregistre le service d'établissement sur le serveur
     * @throws RemoteException, si une erreur RMI
     */
    @Override
    public void enregistrerServiceEtablissementSup(InterfaceEtablissementSup etablissementSup) throws RemoteException {
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
    public InterfaceRestaurant getRestaurant() throws RemoteException, ServiceNotBindException {
        if (this.restaurant == null)
            throw new ServiceNotBindException("Le service restaurant n'est pas enregistré actuellement sur le serveur");
        System.out.println("Récupération du service restaurant");
        return this.restaurant;
    }

    /**
     * Récupère le service d'établissement
     * @return retourne le service
     * @throws ServiceNotBindException, si le service de restaurant n'est pas enregistré actuellement sur le serveur.
     */
    public InterfaceEtablissementSup getEtablissementSup() throws ServiceNotBindException {
        if (this.etablissementSup == null)
            throw new ServiceNotBindException("Le service restaurant n'est pas enregistre actuellement sur le serveur");
        System.out.println("Récupération du service Etablissement superieur");
        return this.etablissementSup;
    }

    /**
     * Méthode que nous n'allons pas utiliser
     */
    public String reserverRestaurant() {
        //TODO
        return null;
    }
}
