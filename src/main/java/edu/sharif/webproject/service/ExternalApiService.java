package edu.sharif.webproject.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@Service
public class ExternalApiService {

    private RestTemplate restTemplate;

    public ExternalApiService() {
        this.restTemplate = new RestTemplate();
    }

    public String sendRequest(String url, HttpMethod httpMethod, HttpHeaders httpHeaders)
            throws UnknownHttpStatusCodeException {
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new UnknownHttpStatusCodeException("HTTP Response Code is not 200",
                    response.getStatusCode().value(),
                    response.getStatusCode().toString(), null, null, null);
        }

        return response.getBody();
    }
}
