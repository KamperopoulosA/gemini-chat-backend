package com.ai.gemini_chat.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class QnAService {

    @Value("${gemini.api.url}")
    String apiUrl;

    @Value("${gemini.api.key}")
    String apiKey;

    private final WebClient webClient;

    public QnAService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getAnswer(String question){
        Map<String , Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text",question)
                        }   )
        }   );
       String response = webClient.post()
                .uri(apiUrl + apiKey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
