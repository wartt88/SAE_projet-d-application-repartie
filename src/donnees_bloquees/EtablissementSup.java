package donnees_bloquees;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class EtablissementSup implements Serializable, InterfaceEtablissementSup {
    private final boolean IUT;

    public EtablissementSup(boolean isIut) {
        IUT = isIut;
    }

    public String recupererListeDetablissementsSuperieurs() throws URISyntaxException, IOException, InterruptedException {

        HttpClient client;

        // si machine IUT
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (IUT) {
            String proxy = "www-cache";
            int port = 3128;

            client = HttpClient.newBuilder().proxy(ProxySelector.of(new InetSocketAddress(proxy, port)))
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

        }
        else {
            // sinon
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }

                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Installer le gestionnaire de confiance tout-trusting
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            }

            client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////


        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.data.gouv.fr/fr/datasets/r/5fb6d2e3-609c-481d-9104-350e9ca134fa")).build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        return response.body();
    }

}
