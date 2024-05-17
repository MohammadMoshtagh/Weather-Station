package edu.sharif.webproject.service;

import edu.sharif.webproject.model.dto.*;
import edu.sharif.webproject.service.country.CountryParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

@SpringBootTest
public class CountryParserServiceTest {

    @Autowired
    private CountryParserService countryParserService;

    @Test
    void testParseCountriesNames() throws IOException {
        File file = ResourceUtils.getFile("classpath:countries_names_response_body.json");
        String responseBody = new String(Files.readAllBytes(file.toPath()));
        CountryNamesDto countryNamesDto = countryParserService.parseCountriesNames(responseBody);
        Assertions.assertEquals(227, countryNamesDto.getCount());
        Assertions.assertEquals(
                countryNamesDto.getCount(),
                countryNamesDto.getCountries().stream().map(CountryNameDto::getName).collect(Collectors.toSet()).size());
    }

    @Test
    void testParseCountry() throws IOException {
        File file = ResourceUtils.getFile("classpath:iran_response_body.json");
        String responseBody = new String(Files.readAllBytes(file.toPath()));
        CountryDto iranDto = countryParserService.parseCountry(responseBody);

        Assertions.assertEquals("Iran, Islamic Republic Of", iranDto.getName());
        Assertions.assertEquals("Tehran", iranDto.getCapital());
        Assertions.assertEquals("IR", iranDto.getIso2());
        Assertions.assertEquals(83993, iranDto.getPopulation());
        Assertions.assertEquals(1.4f, iranDto.getPopGrowth());
        Assertions.assertEquals("IRR", iranDto.getCurrency().getCode());
        Assertions.assertEquals("Iranian Rial", iranDto.getCurrency().getName());
    }

    @Test
    void testParseCityWeather() throws IOException {
        File file = ResourceUtils.getFile("classpath:tehran_weather_response_body.json");
        String responseBody = new String(Files.readAllBytes(file.toPath()));
        CountryWeatherDto tehranWeatherDto = countryParserService.parseCityWeather(responseBody, "Iran", "Tehran");

        Assertions.assertEquals("Tehran", tehranWeatherDto.getCapital());
        Assertions.assertEquals("Iran", tehranWeatherDto.getCountryName());
        Assertions.assertEquals(25, tehranWeatherDto.getTemp());
        Assertions.assertEquals(19, tehranWeatherDto.getHumidity());
        Assertions.assertEquals(260, tehranWeatherDto.getWindDegrees());
        Assertions.assertEquals(2.06f, tehranWeatherDto.getWindSpeed());
    }
}
