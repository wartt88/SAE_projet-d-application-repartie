package serveur;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerServeur {

    // port sur lequel le serveur HTTP va écouter
    private static final int HTTP_PORT = 9000;

    // port sur lequel le serveur RMI va écouter
    private static final int RMI_PORT = 1099;

    /**
     * Méthode main du serveur
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // on crée un registre RMI sur le port 1099
        Registry reg = LocateRegistry.createRegistry(RMI_PORT);
        // on créer le serveur
        Serveur serveur = new Serveur();
        // on exporte le serveur
        InterfaceServeur rd = (InterfaceServeur) UnicastRemoteObject.exportObject(serveur, 0);
        // on bind le serveur
        reg.rebind("ServeurCentrale", rd);
        // on récupère l'ip du serveur
        InetAddress ip = InetAddress.getLocalHost();
        // on crée le serveur HTTP
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(HTTP_PORT), 0);
        // on crée le route handler
        httpServer.createContext("/", new RouteHandler(serveur));
        // on lance le serveur HTTP
        httpServer.setExecutor(null);
        // on lance le serveur
        httpServer.start();
        // on affiche les informations du serveur
        System.out.println("Serveur lancé sur l'ip  :" + ip.getHostAddress() + "Service RMI sur le port : " + RMI_PORT + " Service HTTP sur le port : " + HTTP_PORT);

    }


}