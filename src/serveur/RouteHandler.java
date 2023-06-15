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
        String requestedURL = httpExchange.getRequestURI().toString();
        String requestMethod = httpExchange.getRequestMethod();
        OutputStream outputStream = httpExchange.getResponseBody();

        if (requestedURL.equals("/")) {
            System.out.println("/ root");
        }
        if (requestedURL.startsWith("/addresto")) {
            if (!requestMethod.equals("GET")) {
                httpExchange.sendResponseHeaders(405, 0);
                outputStream.close();
                return;
            }
            String response = serveur.getRestaurant();
            outputStream.write(response.getBytes());
        }
        if (requestedURL.startsWith("/addresto")) {
            if (!requestMethod.equals("POST")) {
                httpExchange.sendResponseHeaders(405, 0);
                outputStream.close();
                return;
            }
            String response = serveur.reserverRestaurant();
            outputStream.write(response.getBytes());
        }
        httpExchange.sendResponseHeaders(200, 0);
        outputStream.close();

    }
}
