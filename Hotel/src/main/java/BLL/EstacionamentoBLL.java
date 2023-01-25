package BLL;

import Model.EstacionamentoAPI.Estacionamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EstacionamentoBLL {

    private static final String parkAPIURL = "https://services.inapa.com/parking4hotel/api/";
    private static final String apiID = "FG4";
    private static final String apiSecret = "4#hpGg)A6(";
    private final HttpClient httpClient;

    public EstacionamentoBLL() {
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

    public Estacionamento GetLugaresDisponiveis() {
        try {

            String result = GETRequestParkingAPI("park");

            if (result.isEmpty())
                return null;

            ObjectMapper mapper = new ObjectMapper();
            Estacionamento teste = null;
            teste = mapper.readValue(result, Estacionamento.class);

            return teste;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String encondeAPICredentials(String id, String secret) {
        return Base64.getEncoder().encodeToString((id + ":" + secret).getBytes());
    }

    private String GETRequestParkingAPI(String endpoint) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .setHeader("Authorization", "Basic " + encondeAPICredentials(apiID, apiSecret))
                    .uri(URI.create(parkAPIURL + endpoint))
                    .build();

            CompletableFuture<HttpResponse<String>> response =
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            return response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
