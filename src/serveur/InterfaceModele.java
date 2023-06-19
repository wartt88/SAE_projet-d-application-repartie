package serveur;

import java.io.Serializable;
import java.rmi.Remote;
import java.sql.SQLException;

public interface InterfaceModele extends Remote, Serializable {

    /**
     * méthode getListeRestaurant permet de récupérer la liste des restaurants
     * @return liste des restaurants
     */
    public String getListeRestaurant() throws SQLException;

    /**
     * Méthode qui permet d'insérer une reservation dans la base de donnée
     * @param idRestaurant, id du restaurant
     * @param numTable, numéro de la table
     * @param date, date de la reservation
     * @param nom, nom de la personne qui reserve
     * @param prenom, prenom de la personne qui reserve
     * @param nbPersonnes, nombre de personnes qui réserve
     * @param numeroTel, numéro de téléphone de la personne qui reserve
     */
    public void reservation(int idRestaurant, int numTable, String date,String nom, String prenom, int nbPersonnes,String numeroTel);

}
