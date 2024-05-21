package edu.sharif.webproject.country;

import edu.sharif.webproject.country.entity.dto.CountryDto;
import edu.sharif.webproject.country.entity.dto.CountryNameDto;
import edu.sharif.webproject.country.entity.dto.CountryNamesResponse;
import edu.sharif.webproject.country.entity.dto.CountryWeatherDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    void testGetAllCountriesNames() {
        CountryNamesResponse countryNamesResponse = countryService.getAllCountriesNames(0, 100);

        Assertions.assertNotNull(countryNamesResponse);
        Assertions.assertTrue(countryNamesResponse.getCount() > 0);
        Assertions.assertEquals(
                countryNamesResponse.getCount(),
                countryNamesResponse.getCountries().stream().map(CountryNameDto::getName).collect(Collectors.toSet()).size());
    }

    @Test
    void testGetCountryByName() {
        String iran = "Iran";
        CountryDto iranDto = countryService.getCountryByName(iran);

        Assertions.assertNotNull(iranDto);
        Assertions.assertEquals("Iran, Islamic Republic Of", iranDto.getName());
        Assertions.assertEquals("Tehran", iranDto.getCapital());
        Assertions.assertEquals("IR", iranDto.getIso2());
        Assertions.assertEquals("IRR", iranDto.getCurrency().getCode());
        Assertions.assertEquals("Iranian Rial", iranDto.getCurrency().getName());

        Exception exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> countryService.getCountryByName("asdf"));
        String expectedMessage = "Country not found!";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void tesGetCountryWeather() {
        String iran = "iran";
        CountryWeatherDto tehranWeatherDto = countryService.getCountryWeatherByCountryName(iran);

        Assertions.assertNotNull(tehranWeatherDto);
        Assertions.assertEquals("Iran, Islamic Republic Of", tehranWeatherDto.getCountryName());
        Assertions.assertEquals("Tehran", tehranWeatherDto.getCapital());
    }
}
