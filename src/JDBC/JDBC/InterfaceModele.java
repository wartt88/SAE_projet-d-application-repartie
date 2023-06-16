package JDBC.JDBC;

import java.rmi.Remote;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InterfaceModele extends Remote {

    /**
     * getter de la Connection
     * @return la connection à la base de données
     */
    public Connection getConnection();

    /**
     * méthode getListeRestaurant permet de récupérer la liste des restaurants
     * @return liste des restaurants
     */
    public String getListeRestaurant() throws SQLException;


}
