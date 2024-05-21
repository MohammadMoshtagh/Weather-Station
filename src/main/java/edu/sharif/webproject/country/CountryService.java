package edu.sharif.webproject.country;

import com.google.gson.JsonParseException;
import edu.sharif.webproject.country.entity.dto.CountryDto;
import edu.sharif.webproject.country.entity.dto.CountryNamesResponse;
import edu.sharif.webproject.country.entity.dto.CountryWeatherDto;
import edu.sharif.webproject.external_api.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final ExternalApiService externalApiService;
    private final CountryParserService countryParserService;

    @Value("${countries.names.url}")
    private String countryUrl;

    @Value("${ninjas.api-key}")
    private String ninjasApiKey;

    @Value("${country.details.url}")
    private String countryDetailsUrl;

    @Value("${country.weather.url}")
    private String countryWeatherUrl;


    @Cacheable(value = "CountriesCache")
    public CountryNamesResponse getAllCountriesNames(int pageNum, int pageSize) {
        String responseBody = externalApiService.sendRequest(countryUrl, HttpMethod.GET, null);
        return countryParserService.parseCountriesNames(responseBody, pageNum, pageSize);
    }

    @Cacheable(value = "CountryNameCache")
    public CountryDto getCountryByName(String countryName) {
        String resourceUrl = countryDetailsUrl + countryName;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", ninjasApiKey);

        String responseBody = externalApiService.sendRequest(resourceUrl, HttpMethod.GET, headers);
        try {
            return countryParserService.parseCountry(responseBody);
        } catch (JsonParseException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!");
        }
    }

    @Cacheable(value = "WeatherCache")
    public CountryWeatherDto getCountryWeatherByCountryName(String countryName) {
        var country = getCountryByName(countryName);
        String resourceUrl = countryWeatherUrl + country.getCapital();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", ninjasApiKey);

        String responseBody = externalApiService.sendRequest(resourceUrl, HttpMethod.GET, headers);
        return countryParserService.parseCityWeather(responseBody, country.getName(), country.getCapital());
    }
}
