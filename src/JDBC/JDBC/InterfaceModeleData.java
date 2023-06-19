package JDBC.JDBC;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceModeleData extends Remote
{
    public String getRestaurants() throws RemoteException;

    public void reserver(int idRestaurant, int numTable, String date,String nom, String prenom, int nbPersonnes,String numeroTel) throws  RemoteException;

}
