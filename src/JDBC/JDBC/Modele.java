package JDBC.JDBC;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;


public class Modele{
    // Information de connexion à la base de données
    private String userName;
    private String password;
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
            // URL de connexion à la base de données
            String url = "jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb";
            this.connection = DriverManager.getConnection(url, userName, password);
            this.connection.setAutoCommit(false);
            System.out.println("connection successful !");
        } catch (Exception e) {
            System.out.println("connection failed");
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
     * @param idRestaurant int
     */
    public void reservation(int idRestaurant, int numTable, String date,String nom, String prenom, int nbPersonnes,String numeroTel) {
        try {
            // On crée la requete
            String requete = "INSERT INTO elbouro11u.reservation(idrestaurant, numtab, datres, nom, prenom, nbpers, numeroTel) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement prepare = this.connection.prepareStatement(requete);
            prepare.setInt(1,idRestaurant);
            prepare.setInt(2,numTable);
            prepare.setString(3,date);
            prepare.setString(4,nom);
            prepare.setString(5,prenom);
            prepare.setInt(6,nbPersonnes);
            prepare.setString(7,numeroTel);

            prepare.executeUpdate();
            this.connection.commit();
        } catch (SQLException e) {
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
}

