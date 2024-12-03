package com.demo.xiying.service.Impl;

import com.demo.xiying.service.WebclientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class WebclientServiceImpl implements WebclientService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public double getSimilarity(String text1, String text2) {
        WebClient webClient = webClientBuilder.build();

        Mono<JsonNode> response = webClient.get()
            .uri("/similarity?text1=" + text1 + "&text2=" + text2)
            .retrieve()
            .bodyToMono(JsonNode.class);

        JsonNode jsonNode = response.block();
        double similarity = jsonNode.get("similarity").asDouble();
        return similarity;
    }
}
