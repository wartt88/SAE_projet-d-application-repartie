package JDBC.JDBC;

import java.rmi.Remote;
import java.sql.Connection;

public interface InterfaceModele extends Remote {

    /**
     * getter de la Connection
     * @return la connection à la base de données
     */
    public Connection getConnection();

}
