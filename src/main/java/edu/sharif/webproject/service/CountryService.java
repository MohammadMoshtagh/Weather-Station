package edu.sharif.webproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.sharif.webproject.model.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryService {

    public CountryNames getCountries() throws Exception {
        String fooResourceUrl = "https://countriesnow.space/api/v0.1/countries";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("HTTP Response is not OK");
        }
        return parseCountries(response);
    }

    public Country getCountryByName(String name) throws Exception {
        return callCountryApi(name);
    }

    private Country callCountryApi(String name) throws Exception {
        String fooResourceUrl = "https://api.api-ninjas.com/v1/country?name=" + name;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", "cY7m114tedLWNPoZc82Rng==II8DlcnDOJCePp9X");

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("HTTP Response is not OK");
        }
        var jsonArray = parseToJsonArray(response);
        var countryData = jsonArray.get(0).getAsJsonObject();

        var capitalName = countryData.get("capital").getAsString();
        var iso2 = countryData.get("iso2").getAsString();
        var population = countryData.get("population").getAsLong();
        var pop_growth = countryData.get("pop_growth").getAsFloat();

        var currencyJson = countryData.get("currency").getAsJsonObject();
        var currency = new Currency(currencyJson.get("code").getAsString(),
                currencyJson.get("name").getAsString());

        return new Country(
                name,
                capitalName,
                iso2,
                population,
                pop_growth,
                currency);
    }

    public CountryWeather getCountryWeatherByCountryName(String countryName) throws Exception {
        var country = callCountryApi(countryName);
        String fooResourceUrl = "https://api.api-ninjas.com/v1/weather?city=" + country.getCapital();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", "cY7m114tedLWNPoZc82Rng==II8DlcnDOJCePp9X");

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("HTTP Response is not OK");
        }
        var jsonObject = parseToJsonObject(response);

        var windSpeed = jsonObject.get("wind_speed").getAsFloat();
        var windDegrees = jsonObject.get("wind_degrees").getAsFloat();
        var temp = jsonObject.get("temp").getAsFloat();
        var humidity = jsonObject.get("humidity").getAsFloat();

        return new CountryWeather(
                country.getName(),
                country.getCapital(),
                windSpeed,
                windDegrees,
                temp,
                humidity);
    }

    private CountryNames parseCountries(ResponseEntity<String> response) throws JsonProcessingException {
        var jsonObject = parseToJsonObject(response);
        var countryNames = new CountryNames();

        for (var element : jsonObject.get("data").getAsJsonArray()) {
            var countryName = new CountryName(element.getAsJsonObject().get("country").getAsString());
            countryNames.addCountryName(countryName);
        }

        return countryNames;
    }

    private JsonObject parseToJsonObject(ResponseEntity<String> response) {
        Gson gson = new Gson();
        return gson.fromJson(response.getBody(), JsonObject.class);
    }

    private JsonArray parseToJsonArray(ResponseEntity<String> response) {
        Gson gson = new Gson();
        return gson.fromJson(response.getBody(), JsonArray.class);
    }
}
