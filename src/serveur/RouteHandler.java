package serveur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class RouteHandler implements HttpHandler {
    Serveur serveur;

    /**
     * Constructeur de la classe RouteHandler
     *
     * @param s
     */
    public RouteHandler(Serveur s) {
        serveur = s;
    }

    /**
     * Méthode principale pour gérer les requêtes HTTP reçues par le serveur.
     *
     * @param httpExchange L'échange HTTP contenant la requête et la réponse.
     * @throws IOException En cas d'erreur lors de la manipulation des flux de données.
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // on récupère l'url et la méthode de la requête
        String requestedURL = httpExchange.getRequestURI().toString();
        // on récupère la méthode de la requête
        String requestMethod = httpExchange.getRequestMethod();
        // on récupère le flux de sortie
        OutputStream outputStream = httpExchange.getResponseBody();
        // Récupérez la valeur de l'en-tête 'Origin' de la requête

        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        System.out.println("URL : " + requestedURL);
        System.out.println("Method : " + requestMethod);

        // on try catch pour gérer les exceptions
        try {
            // on vérifie si l'url est /restaurant/getTables
            if (requestedURL.startsWith("/restaurant/getTables")) {
                // si la méthode n'est pas GET
                if (!requestMethod.equals("GET")) {
                    System.out.println("pas GET");
                    httpExchange.sendResponseHeaders(405, 0);
                    outputStream.close();
                    return;
                } else {
                    // si la méthode est GET
                    System.out.println("GET RESTAURANTS");
                    // on récupère le service
                    String response = serveur.getRestaurant();
                    System.out.println(response);
                    // on écrit la réponse
                    httpExchange.sendResponseHeaders(200, response.getBytes().length);

                    outputStream.write(response.getBytes());
                }
            }

            // on vérifie si l'url est /restaurant/reserverTable
            if (requestedURL.startsWith("/restaurant/reserverTable")) {
                // si la méthode n'est pas POST
                if (!requestMethod.equals("POST")) {
                    // on envoie une erreur 405
                    httpExchange.sendResponseHeaders(405, 0);
                    // on ferme le flux de sortie
                    outputStream.close();
                    return;
                } else {
                    // si la méthode est POST
                    String response = serveur.reserverRestaurant();
                    // on écrit la réponse
                    outputStream.write(response.getBytes());
                }
            }
            if (requestedURL.startsWith("/recupererListEtablissement")) {
                if (!requestMethod.equals("GET")) {
                    // on envoie une erreur 405
                    httpExchange.sendResponseHeaders(405, 0);
                    // on ferme le flux de sortie
                    outputStream.close();
                    return;
                } else {
                    // si la méthode est POST
                    String response = serveur.getEtablissementSup().recupererListeDetablissementsSuperieurs();
                    // on écrit la réponse
                    httpExchange.sendResponseHeaders(200, response.getBytes().length);
                    outputStream.write(response.getBytes());
                }
            }
        } catch (ServiceNotBindException e) { // on catch l'exception si le service n'est pas bind
            // Si le service ne s'est pas déclaré sur le serveur
            httpExchange.sendResponseHeaders(503, 0);
            String error = e.getMessage();
            outputStream.write(error.getBytes());
            outputStream.close();
            return;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // on envoie une réponse 200
        // on ferme le flux de sortie
        outputStream.close();
    }
}
