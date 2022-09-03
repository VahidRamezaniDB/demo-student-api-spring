package com.example.student.service;

import com.example.student.configuration.SelfCallConfiguration;
import com.example.student.controller.SelfCallController;
import com.example.student.exception.NoContentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SelfService {

    private SelfCallConfiguration configuration;

    public SelfService(SelfCallConfiguration configuration) {
        this.configuration = configuration;
    }

    public void callSelfApi(long id){
//        WebClient webClient = WebClient.builder().baseUrl(configuration.getStudentUrl()).build();
        WebClient webClient = WebClient.builder().baseUrl("http://127.0.0.1:8080/student").build();
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(HttpMethod.GET);

        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/{id}", id);

        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("");

        Mono<String> response = headersSpec.exchangeToMono(clientResponse -> {
            if(clientResponse.statusCode().equals(HttpStatus.OK)){
                return clientResponse.bodyToMono(String.class);
            }else{
                System.out.println(clientResponse.statusCode());
                throw new NoContentException();
            }
        });
        System.out.println(response.block());
    }
}
