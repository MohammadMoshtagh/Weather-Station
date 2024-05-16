package edu.sharif.webproject.service.country;

import edu.sharif.webproject.model.dto.*;
import edu.sharif.webproject.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application-dev.properties")
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

    public CountryNamesDto getAllCountriesNames() {
        String responseBody = externalApiService.sendRequest(countryUrl, HttpMethod.GET, null);
        return countryParserService.parseCountriesNames(responseBody);
    }

    public CountryDto getCountryByName(String countryName) {
        String resourceUrl = countryDetailsUrl + countryName;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", ninjasApiKey);

        String responseBody = externalApiService.sendRequest(resourceUrl, HttpMethod.GET, headers);
        return countryParserService.parseCountry(responseBody);
    }

    public CountryWeatherDto getCountryWeatherByCountryName(String countryName) {
        var country = getCountryByName(countryName);
        String resourceUrl = countryWeatherUrl + country.getCapital();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", ninjasApiKey);

        String responseBody = externalApiService.sendRequest(resourceUrl, HttpMethod.GET, headers);
        return countryParserService.parseCityWeather(responseBody, country.getName(), country.getCapital());
    }
}
