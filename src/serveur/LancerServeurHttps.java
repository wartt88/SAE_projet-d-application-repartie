package serveur;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.*;
import java.security.cert.CertificateException;

public class LancerServeurHttps {
    // port sur lequel le serveur HTTPS va écouter
    private static final int HTTPS_PORT = 9000;

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
        HttpsServer httpsServer = HttpsServer.create(new InetSocketAddress(HTTPS_PORT), 0);

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // initialise the keystore
            char[] password = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("key.jks");
            ks.load(fis, password);

            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            // setup the HTTPS context and parameters

            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // initialise the SSL context
                        SSLContext context = getSSLContext();
                        SSLEngine engine = context.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        // Set the SSL parameters
                        SSLParameters sslParameters = context.getSupportedSSLParameters();
                        params.setSSLParameters(sslParameters);

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        // on crée le route handler
        httpsServer.createContext("/", new RouteHandler(serveur));
        // on lance le serveur HTTP
        httpsServer.setExecutor(null);
        // on lance le serveur
        httpsServer.start();
        // on affiche les informations du serveur
        System.out.println("Serveur lancé sur l'ip  : " + ip.getHostAddress() + " Service RMI sur le port : " + RMI_PORT + " Service HTTPS sur le port : " + HTTPS_PORT);
    }
}