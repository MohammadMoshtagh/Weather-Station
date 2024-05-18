package edu.sharif.webproject.external.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(retryFor = Exception.class,
            backoff = @Backoff(delayExpression = "${rest-template-retry.max-delay}"),
            maxAttemptsExpression = "${rest-template-retry.max-attempts}")
    public String sendRequest(String url, HttpMethod httpMethod, HttpHeaders httpHeaders) {
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, httpMethod, entity, String.class);
            return response.getBody();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "External API doesn't answer!");
        }
    }
}
