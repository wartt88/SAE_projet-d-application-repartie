package JDBC.JDBC;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class Modele implements InterfaceModele, Serializable {
    // Information de connexion à la base de données
    private String userName;
    private String password;

    // URL de connexion à la base de données
    private String url = "jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb";

    // Objet de connexion à la base de données
    private transient Connection connection = null;

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
    @Override
    public void reservation(int idRestaurant, int numTable, String date,String nom, String prenom, int nbPersonnes,String numeroTel) {
        try {
            // On crée la requete
            String requete = "INSERT INTO elbouro11u.reservation(idrestaurant, numtab, datres, nom, prenom, nbpers, numeroTel) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement prepare = this.getConnection().prepareStatement(requete);
            prepare.setInt(1,idRestaurant);
            prepare.setInt(2,numTable);
            prepare.setString(3,date);
            prepare.setString(4,nom);
            prepare.setString(5,prenom);
            prepare.setInt(6,nbPersonnes);
            prepare.setString(7,numeroTel);

            prepare.executeUpdate();
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
    @Override
    public String getListeRestaurant() throws SQLException {
        // On crée la requete
        String requete = "SELECT * FROM elbouro11u.restaurant";
        // On execute la requete
        Statement stmt = this.getConnection().createStatement();
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
        if (connection == null) {
            try {
                this.connection = DriverManager.getConnection(this.url, userName, password);
                this.connection.setAutoCommit(false);
                System.out.println("Connection avec succes !");
            } catch (Exception e) {
                System.out.println("Connection failed");
                e.printStackTrace();
                System.exit(1);
            }
        }
        return connection;
    }


}

