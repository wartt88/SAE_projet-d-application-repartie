package serveur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RouteHandlerHttps implements HttpHandler {
    Serveur serveur;

    /**
     * Constructeur de la classe RouteHandler
     *
     * @param s Le serveur
     */
    public RouteHandlerHttps(Serveur s) {
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
                System.out.println("GET TABLES");
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
                    String response = serveur.getRestaurant().getRestaurants();
                    System.out.println(response);
                    // on écrit la réponse
                    httpExchange.sendResponseHeaders(200, response.getBytes().length);
                    outputStream.write(response.getBytes());
                }
            }

            // on vérifie si l'url est /restaurant/reserverTable
            if (requestedURL.startsWith("/restaurant/reserverTable")) {
                System.out.println("POST RESERVER TABLE");
                // si la méthode n'est pas POST
                if (!requestMethod.equals("POST")) {
                    // on envoie une erreur 405
                    httpExchange.sendResponseHeaders(405, 0);
                    // on ferme le flux de sortie
                    outputStream.close();
                    return;
                } else {
                    System.out.println("On cherche le body");
                    // si la méthode est POST
                    // Recuperation des données
                    //JSONObject data = new JSONObject(httpExchange.getRequestBody().toString());
                    System.out.println("Data reserver table");
                    //System.out.println(data);
                    System.out.println(httpExchange.getRequestBody().toString());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8));
                    StringBuilder requestBody = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        requestBody.append(line);
                    }
                    bufferedReader.close();
                    String requestBodyString = requestBody.toString();
                    System.out.println(requestBodyString);
                    // Conversion de la chaîne en objet JSONObject
                    JSONObject jsonObject = new JSONObject(requestBodyString);
                    try {
                        int id = jsonObject.getInt("id");
                        int numTable = jsonObject.getInt("numTable");
                        String date = jsonObject.getString("date");
                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        int nbPersonnes = jsonObject.getInt("nbPersonnes");
                        String telephone = jsonObject.getString("telephone");
                        serveur.getRestaurant().reserver(id, numTable, date, nom, prenom, nbPersonnes, telephone);
                        httpExchange.sendResponseHeaders(200, 0);
                    } catch (Exception e) {
                        httpExchange.sendResponseHeaders(400, e.getMessage().getBytes().length);
                        outputStream.write(e.getMessage().getBytes());
                        e.printStackTrace();
                    }

                }
            }
            if (requestedURL.startsWith("/recupererListEtablissement")) {
                System.out.println("GET LISTE ETABLISSEMENT");
                if (!requestMethod.equals("GET")) {
                    // on envoie une erreur 405
                    httpExchange.sendResponseHeaders(405, 0);
                    // on ferme le flux de sortie
                    outputStream.close();
                    return;
                } else {
                    // si la méthode est POST
                    try {
                        String response = serveur.getEtablissementSup().recupererListeDetablissementsSuperieurs();
                        // on écrit la réponse
                        System.out.println("Reponse");
                        httpExchange.sendResponseHeaders(200, response.getBytes().length);
                        outputStream.write(response.getBytes());
                    } catch (ServiceNotBindException e) {
                        e.printStackTrace();
                        httpExchange.sendResponseHeaders(503, 0);
                        String error = "Service non bind";
                        outputStream.write(error.getBytes());
                        outputStream.close();
                        return;
                    }

                }
            }
        } catch (ServiceNotBindException e) { // on catch l'exception si le service n'est pas bind
            // Si le service ne s'est pas déclaré sur le serveur
            e.printStackTrace();
            httpExchange.sendResponseHeaders(503, 0);
            String error = e.getMessage();
            outputStream.write(error.getBytes());
            outputStream.close();
            return;
        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // on envoie une réponse 200
        // on ferme le flux de sortie
        outputStream.close();
    }
}
