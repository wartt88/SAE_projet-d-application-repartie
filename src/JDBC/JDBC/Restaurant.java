package JDBC.JDBC;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Restaurant implements InterfaceRestaurant, Serializable
{
    //Modele qui permet d'accéder a la base de donnée
    private final transient RestaurantModele modele;

    public Restaurant(String usernam, String password)
    {
        this.modele = new RestaurantModele(usernam,password);
    }

    /**
     * Méthode qui permet d'accéder aux données des restaurants
     * @return retourne les données en JSON
     * @throws RemoteException, exception RMI
     */
    @Override
    public String getRestaurants() throws RemoteException
    {
        try {
            //Attribut qui représente les données des restaurants en JSON
            return this.modele.getListeRestaurant();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Méthode qui permet de reserver une table d'un restaurant
     * @param idRestaurant, id du restaurant
     * @param numTable, numéro de la table
     * @param date, date de la reservation,
     * @param nom, nom de la personnne,
     * @param prenom, prenom de la personne
     * @param nbPersonnes, nombre de personne
     * @param numeroTel, numéro de téléphone
     * @throws RemoteException, exception RMI
     */
    @Override
    public void reserver(int idRestaurant, int numTable, String date,String nom, String prenom, int nbPersonnes,String numeroTel) throws RemoteException, BDDException {
        this.modele.reservation(idRestaurant,numTable,date,nom,prenom,nbPersonnes,numeroTel);
    }
}
