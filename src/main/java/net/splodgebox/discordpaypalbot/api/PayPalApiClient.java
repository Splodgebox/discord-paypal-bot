package net.splodgebox.discordpaypalbot.api;

import net.splodgebox.discordpaypalbot.config.PayPalConfig;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PayPalApiClient {

    private static final String PAYPAL_API_BASE_URL = "https://api-m.paypal.com/";
    private static final String CAPTURE_ENDPOINT = "v2/payments/captures/";
    private final HttpClient httpClient;

    public PayPalApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public boolean isValidPurchase(String transactionId) {
        try {
            JsonObject jsonObject = getValidPurchase(transactionId);
            return "COMPLETED".equalsIgnoreCase(jsonObject.getString("status"));
        } catch (Exception exception) {
            return false;
        }
    }

    public JsonObject getValidPurchase(String transactionId) {
        JsonReader jsonReader = Json.createReader(new StringReader(makeGetRequest(CAPTURE_ENDPOINT + transactionId)));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        return object;
    }

    public String makeGetRequest(String endpoint) {
        URI uri = URI.create(PAYPAL_API_BASE_URL + endpoint);

        HttpRequest request = createBaseRequestBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpRequest.Builder createBaseRequestBuilder() {
        return HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API_BASE_URL))
                .header("Authorization", String.format("Bearer %s", PayPalConfig.getAccessToken()))
                .header("Content-Type", "application/json");
    }

}
