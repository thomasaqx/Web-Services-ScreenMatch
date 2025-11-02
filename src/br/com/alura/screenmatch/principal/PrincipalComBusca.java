package br.com.alura.screenmatch.principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {

        String apiKey = System.getenv("OMDB_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("Erro: A variável de ambiente OMDB_API_KEY não foi definida.");
            return;
        }

        String titulo = "Dexter";
        String url = "http://www.omdbapi.com/?t=" + titulo + "&apikey=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

    }
}
