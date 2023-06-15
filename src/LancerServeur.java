import com.sun.net.httpserver.HttpServer;
import serveur.InterfaceServeur;
import serveur.RouteHandler;
import serveur.Serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerServeur {

    private static final int HTTP_PORT = 9000;
    private static final int RMI_PORT = 1099;

    public static void main(String[] args) throws IOException {


        Registry reg = LocateRegistry.createRegistry(RMI_PORT);
        Serveur serveur = new Serveur();
        InterfaceServeur rd = (InterfaceServeur) UnicastRemoteObject.exportObject(serveur, 0);
        reg.rebind("ServeurCentrale", rd);
        InetAddress ip = InetAddress.getLocalHost();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(HTTP_PORT), 0);
        httpServer.createContext("/", new RouteHandler(serveur));
        httpServer.setExecutor(null);
        httpServer.start();
        System.out.println("Serveur lancé sur l'ip  :" + ip.getHostAddress() + "Service RMI sur le port : " + RMI_PORT + " Service HTTP sur le port : " + HTTP_PORT);

    }


}