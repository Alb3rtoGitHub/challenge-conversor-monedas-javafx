package com.alura.challengeconversormonedasjavafx.servicio;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIServicio {
    private final String apiKey;

    public APIServicio(String apiKey) {
        this.apiKey = apiKey;
    }

    public double consultarTasaDeCambio(String base, String destino){
        String url = "https://v6.exchangerate-api.com/v6/"+ apiKey + "/pair/" + base + "/" + destino;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            return jsonResponse.get("conversion_rate").getAsDouble();

        } catch (Exception e) {
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
            return 0.0;
        }
    }
}
