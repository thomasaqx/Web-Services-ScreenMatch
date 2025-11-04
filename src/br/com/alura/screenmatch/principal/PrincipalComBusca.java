package br.com.alura.screenmatch.principal;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import  java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {

        String apiKey = System.getenv("OMDB_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("Erro: A variável de ambiente OMDB_API_KEY não foi definida.");
            return;
        }

        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite uma série ou um filme: ");

        var titulo = leitura.nextLine();
        var endereco = "http://www.omdbapi.com/?t=" +
                URLEncoder.encode(titulo, StandardCharsets.UTF_8) +
                "&apikey=" + apiKey;


        //Cria o "mensageiro" (cliente) que fará as requisições
        HttpClient client = HttpClient.newHttpClient();
        ////Cria a "mensagem" (requisição) especificando o endereço (URL)
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();
        //envia a requisição e armazena a resposta como uma String
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        System.out.println(json);


        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
        System.out.println(meuTituloOmdb);
        try {
            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println("Título já convertido");
            System.out.println(meuTitulo);
        } catch (NumberFormatException e){
            System.out.println("Ocorreu um erro");
            System.out.println(e.getMessage());
        }

        System.out.println("O programa finalizou corretamente");
    }
}
