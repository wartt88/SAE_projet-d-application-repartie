package JDBC.Modele;

import java.sql.Connection;
import java.sql.DriverManager;

public class Modele {

    private String userName;
    private String password;
    private String url = "jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb";
    private Connection connection = null;

    public Modele(String userName, String password) {
        this.userName = userName;
        this.password = password;
        try {
            this.connection = DriverManager.getConnection(this.url, userName, password);
            this.connection.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

}

