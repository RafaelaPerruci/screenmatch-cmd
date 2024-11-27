package br.com.rafaelaperruci.screenmatch_cmd_line.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumerAPI {

    public String getData(String url){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException e){
            throw new RuntimeException("Erro na comunicação da APIA.");
        }
        catch (Exception e) {
            throw new RuntimeException("Erro: Endereço não encontrado.");
        }

        String json = response.body();

        return json;
    }




}
