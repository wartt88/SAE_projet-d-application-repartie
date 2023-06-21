package JDBC.JDBC;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class RestaurantModele {
    // Objet de connexion à la base de données
    private Connection connection = null;

    /**
     * Constructeur de la classe Modele
     * le modele permet de faire les requetes à la base de données
     * dans le constructeur on se connecte à la base de données
     *
     * @param userName passé en premiere argument
     * @param password passé en deuxieme argument
     */
    public RestaurantModele(String userName, String password) {
        // On initialise les attributs
        // Information de connexion à la base de données

        // try catch pour se connecter à la base de données
        try {
            // URL de connexion à la base de données
            String url = "jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb";
            this.connection = DriverManager.getConnection(url, userName, password);
            this.connection.setAutoCommit(false);
            System.out.println("Connexion a la base de donnee reussis !");
        } catch (Exception e) {
            System.out.println("Erreur: connexion a la base de donne echoue.");
            e.printStackTrace();
        }
    }

    /**
     * méthode reservation permet d'enregistrer une reservation dans la base de données
     *
     * @param date         String
     * @param nbPersonnes  int
     * @param numTable     int
     * @param nom          String
     * @param prenom       String
     * @param idRestaurant int
     */
    public void reservation(int idRestaurant, int numTable, String date, String nom, String prenom, int nbPersonnes, String numeroTel) throws BDDException {
        try {
            // On crée la requete
            String requete = "INSERT INTO elbouro11u.reservation(idrestaurant, numtab, datres, nom, prenom, nbpers, numeroTel) VALUES(?,?,?,?,?,?,?)";

            // On transforme le string en date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(date);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            PreparedStatement prepare = this.connection.prepareStatement(requete);
            prepare.setInt(1, idRestaurant);
            prepare.setInt(2, numTable);
            prepare.setTimestamp(3, timestamp);
            prepare.setString(4, nom);
            prepare.setString(5, prenom);
            prepare.setInt(6, nbPersonnes);
            prepare.setString(7, numeroTel);

            String verification = "SELECT numtab, nbplace FROM elbouro11u.tabl where numtab = ?";
            PreparedStatement stmt = this.connection.prepareStatement(verification);
            stmt.setInt(1, numTable);
            try {
                ResultSet res = stmt.executeQuery();
                if (res.next()) {
                    int nbPlace = res.getInt("nbplace");
                    if (nbPlace <= nbPersonnes) {
                        throw new BDDException("Le nombre de place est insuffisant");
                    }
                }
                else {
                    throw new BDDException("Le numero de table n'existe pas");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la reservation (numero de table n'existe pas)");
                e.printStackTrace();
                throw new BDDException(e.getMessage());
            }


            prepare.executeUpdate();
            this.connection.commit();
            System.out.println("reservation réussis avec succes.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la reservation");
            e.printStackTrace();
            throw new BDDException("Erreur lors de la reservation" + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Erreur lors de la reservation (date)");
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
        while (res.next()) {
            JSONObject objetJson = new JSONObject();
            objetJson.put("latitude", res.getString("latitude"));
            objetJson.put("longitude", res.getString("longitude"));
            objetJson.put("nom", res.getString("nom"));
            objetJson.put("id", res.getString("idRestaurant"));

            json.put(objetJson);
        }
        System.out.println("la liste des restaurants a ete envoye avec succes.");
        return json.toString();
    }
}

