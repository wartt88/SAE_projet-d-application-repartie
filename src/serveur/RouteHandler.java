package serveur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class RouteHandler implements HttpHandler {
    Serveur serveur;

    public RouteHandler(Serveur s) {
        serveur = s;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // on récupère l'url et la méthode de la requête
        String requestedURL = httpExchange.getRequestURI().toString();
        // on récupère la méthode de la requête
        String requestMethod = httpExchange.getRequestMethod();
        // on récupère le flux de sortie
        OutputStream outputStream = httpExchange.getResponseBody();


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
                    System.out.println("GET");
                    // on récupère le service
                    String response = serveur.getRestaurant();
                    // on écrit la réponse
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
        } catch (ServiceNotBindException e) { // on catch l'exception si le service n'est pas bind
            // Si le service ne s'est pas déclaré sur le serveur
            httpExchange.sendResponseHeaders(503, 0);
            String error = e.getMessage();
            outputStream.write(error.getBytes());
            outputStream.close();
            return;
        }
        // on envoie une réponse 200
        httpExchange.sendResponseHeaders(200, 0);
        // on ferme le flux de sortie
        outputStream.close();
    }
}
