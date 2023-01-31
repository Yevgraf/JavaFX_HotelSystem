package BLL;

import Model.EstacionamentoAPI.Estacionamento;
import Model.EstacionamentoAPI.ResponseTicket;
import Model.EstacionamentoAPI.Ticket;
import Model.EstacionamentoAPI.TicketInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
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

    public Estacionamento GetLugares() {
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

    public Ticket GetTicketsCriados() {
        try {

            String result = GETRequestParkingAPI("ticket");

            if (result.isEmpty())
                return null;

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(result, Ticket.class);
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

    public ResponseTicket POSTCreateParkingReservation(TicketInfo body) {
        try {
            var objectMapper = new ObjectMapper();

            String requestBody = objectMapper
                    .writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .setHeader("Content-Type", "application/json")
                    .setHeader("Authorization", "Basic " + encondeAPICredentials(apiID, apiSecret))
                    .uri(URI.create(parkAPIURL + "ticket"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            CompletableFuture<HttpResponse<String>> response =
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            String parsedResponse = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

            return objectMapper.readValue(parsedResponse, ResponseTicket.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean DeleteTicket(String idTicket) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(parkAPIURL + "Ticket/" + idTicket))
                    .setHeader("Content-Type", "application/json")
                    .setHeader("Authorization", "Basic " + encondeAPICredentials(apiID, apiSecret))
                    .DELETE()
                    .build();

            var client = HttpClient.newHttpClient();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}