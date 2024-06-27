package edu.sharif.webproject.weather.external_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate;

    @Retryable(retryFor = Exception.class,
            backoff = @Backoff(delayExpression = "${rest-template-retry.max-delay}"),
            maxAttemptsExpression = "${rest-template-retry.max-attempts}")
    public <E> ResponseEntity<E> sendRequest(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Class<E> eClass) {
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<E> response;
        try {
            response = restTemplate.exchange(url, httpMethod, entity, eClass);
            return response;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "External API doesn't answer!");
        }
    }
}
