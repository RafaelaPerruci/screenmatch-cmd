package br.com.rafaelaperruci.screenmatch_cmd_line.services;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import java.io.*;
import java.util.Scanner;


public class ChatGPTSearch {


    private String apiKey = "";

    public String translation(String texto) {


        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\api-chatgpt-key"))) {
            apiKey = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OpenAiService service = new OpenAiService(apiKey);


        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var response = service.createCompletion(requisicao);
        return response.getChoices().get(0).getText();
    }
}
