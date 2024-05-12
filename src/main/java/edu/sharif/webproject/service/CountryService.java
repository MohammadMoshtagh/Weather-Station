package edu.sharif.webproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.sharif.webproject.model.CountryName;
import edu.sharif.webproject.model.CountryNames;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    public CountryNames getCountries() throws Exception {
        String fooResourceUrl = "https://countriesnow.space/api/v0.1/countries";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("HTTP Response is not OK");
        }
        var countriesNames = parseCountries(response);
        return countriesNames;
    }

    private CountryNames parseCountries(ResponseEntity<String> response) throws JsonProcessingException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.getBody(), JsonObject.class);
        var countryNames = new CountryNames();

        for (var element : jsonObject.get("data").getAsJsonArray()) {
            var countryName = new CountryName(element.getAsJsonObject().get("country").getAsString());
            countryNames.addCountryName(countryName);
        }

        return countryNames;
    }
}
