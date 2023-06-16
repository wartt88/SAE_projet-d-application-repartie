package JDBC.JDBC;

import java.rmi.Remote;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InterfaceModele extends Remote {

    /**
     * méthode getListeRestaurant permet de récupérer la liste des restaurants
     * @return liste des restaurants
     */
    public String getListeRestaurant() throws SQLException;


}
