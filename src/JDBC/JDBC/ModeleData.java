package JDBC.JDBC;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ModeleData implements InterfaceModeleData, Serializable
{
    private String json;
    private transient Modele modele;

    public ModeleData(String usernam, String password)
    {
        this.modele = new Modele(usernam,password);
    }

    @Override
    public String getRestaurants() throws RemoteException
    {
        try {
            String res = this.modele.getListeRestaurant();
            this.json = res;
            return this.json;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
