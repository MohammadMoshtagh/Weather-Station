package edu.sharif.webproject.country;

import com.google.gson.JsonParseException;
import edu.sharif.webproject.country.dto.CountryDto;
import edu.sharif.webproject.country.dto.CountryNamesDto;
import edu.sharif.webproject.country.dto.CountryWeatherDto;
import edu.sharif.webproject.external.api.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
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

    @Autowired
    public CountryService(ExternalApiService externalApiService, CountryParserService countryParserService) {
        this.externalApiService = externalApiService;
        this.countryParserService = countryParserService;
    }

    @Cacheable(value = "CountriesCache")
    public CountryNamesDto getAllCountriesNames() {
        String responseBody = externalApiService.sendRequest(countryUrl, HttpMethod.GET, null);
        return countryParserService.parseCountriesNames(responseBody);
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
