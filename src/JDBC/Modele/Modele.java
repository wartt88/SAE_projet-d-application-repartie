package JDBC.Modele;

import java.sql.Connection;
import java.sql.DriverManager;

public class Modele {

    // Information de connexion à la base de données
    private String userName;
    private String password;

    // URL de connexion à la base de données
    private String url = "jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb";

    // Objet de connexion à la base de données
    private Connection connection = null;

    /**
     * Constructeur de la classe Modele
     * le modele permet de faire les requetes à la base de données
     * dans le constructeur on se connecte à la base de données
     * @param userName passé en premiere argument
     * @param password passé en deuxieme argument
     */
    public Modele(String userName, String password) {
        // On initialise les attributs
        this.userName = userName;
        this.password = password;

        // try catch pour se connecter à la base de données
        try {
            this.connection = DriverManager.getConnection(this.url, userName, password);
            this.connection.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    //
    // GETTER
    //

    /**
     * getter de la Connection
     * @return la connection à la base de données
     */
    public Connection getConnection() {
        return connection;
    }



}

