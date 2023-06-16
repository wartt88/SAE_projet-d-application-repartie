package donnees_bloquees;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://www.data.gouv.fr/fr/datasets/r/5fb6d2e3-609c-481d-9104-350e9ca134fa")).GET().build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.newBuilder()
                .build().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        String redirectUrl = response.headers().firstValue("Location").orElse("");
        System.out.println("Redirection URL: " + redirectUrl);
        String data = "http://data.enseignementsup-recherche.gouv.fr//explore/dataset/fr-esr-principaux-etablissements-enseignement-superieur/download?format=json&timezone=Europe/Berlin&use_labels_for_header=false";
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI(data))
                .GET()
                .headers("Referer", "strict-origin-when-cross-origin")
                .build();
        HttpResponse<String> response2 = client.newBuilder()
                .build().send(request2, HttpResponse.BodyHandlers.ofString());
        System.out.println(response2.statusCode());
        System.out.println(response2.body());
        String redirectUrl2 = response.headers().firstValue("Location").orElse("");
        System.out.println("Redirection URL: " + redirectUrl2);
    }
}
