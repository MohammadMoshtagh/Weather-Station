package edu.sharif.webproject.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.io.EOFException;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;

    public ExternalApiService() {
        this.restTemplate = new RestTemplate();
    }

    public String sendRequest(String url, HttpMethod httpMethod, HttpHeaders httpHeaders)
            throws UnknownHttpStatusCodeException {
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, httpMethod, entity, String.class);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "External API doesn't answer!");
        }

        return response.getBody();
    }
}
