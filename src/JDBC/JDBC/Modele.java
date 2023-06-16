package JDBC.JDBC;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class Modele implements InterfaceModele {

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
            System.out.println("Connection avec succes !");
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    /**
     * méthode reservation permet d'enregistrer une reservation dans la base de données
     * @param date String
     * @param nbPersonnes int
     * @param numTable int
     * @param nom String
     * @param prenom String
     * @param idReservation int
     * @param idRestaurant int
     */
    public void reservation(String date, int nbPersonnes, int numTable, String nom, String prenom, int idReservation, int idRestaurant) {
        try {
            // On crée la requete
            String requete = "INSERT INTO Reservation VALUES (" + idReservation + ", '" + date + "', " + nbPersonnes + ", " + numTable + ", '" + nom + "', '" + prenom + "', " + idRestaurant + ")";
            // On execute la requete
            this.connection.createStatement().executeUpdate(requete);
            // On commit
            this.connection.commit();
        } catch (Exception e) {
            System.out.println("Erreur lors de la reservation");
            e.printStackTrace();
        }
    }


    /**
     * méthode getListeRestaurant permet de récupérer la liste des restaurants
     *
     * @return liste des restaurants
     */
    public String getListeRestaurant() throws SQLException {
        // On crée la requete
        String requete = "SELECT * FROM elbouro11u.restaurant";
        // On execute la requete
        Statement stmt = this.connection.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        JSONArray json = new JSONArray();
        while (res.next())
        {
            JSONObject objetJson  = new JSONObject();
            objetJson.put("latitude", res.getString("latitude"));
            objetJson.put("longitude", res.getString("longitude"));
            objetJson.put("nom", res.getString("nom"));
            json.put(objetJson);
        }
        return json.toString();
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

    public static void main(String[] args) throws SQLException {
        Modele modele = new Modele(args[0],args[1]);
        System.out.println(modele.getListeRestaurant());
    }
}

