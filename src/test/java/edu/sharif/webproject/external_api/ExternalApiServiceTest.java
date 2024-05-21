package edu.sharif.webproject.external_api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class ExternalApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExternalApiService externalApiService;

    @Test
    void testSendRequest() {
        when(restTemplate.exchange("https://testapi.com", HttpMethod.GET, new HttpEntity<>("", null), String.class))
                .thenReturn(new ResponseEntity<>("result", HttpStatus.OK));
        Assertions.assertEquals(externalApiService.sendRequest("https://testapi.com", HttpMethod.GET, null), "result");
    }
}
