package JDBC.JDBC;

public class Restaurant {

    private String nom;
    private String longitude;
    private String latitude;

    /**
     * Constructeur de la classe Restaurant
     * @param nom
     * @param longitude
     * @param latitude
     */
    public Restaurant(String nom, String longitude, String latitude) {
        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //
    // GETTER
    //

    /**
     * getter du nom du restaurant
     * @return le nom du restaurant
     */
    public String getNom() {
        return nom;
    }

    /**
     * getter de la longitude du restaurant
     * @return
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * getter de la latitude du restaurant
     * @return
     */
    public String getLatitude() {
        return latitude;
    }
}
