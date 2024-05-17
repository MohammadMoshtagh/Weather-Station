package edu.sharif.webproject.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.sharif.webproject.model.dto.*;
import edu.sharif.webproject.service.country.CountryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {
    private static CountryDto iranDto;

    private static CountryDto usDto;
    private static CountryWeatherDto usWeatherDto;
    private static CountryNamesDto countryNamesDto;

    @MockBean
    private CountryService countryService;


    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void createCountryNamesMock() {
        countryNamesDto = new CountryNamesDto();
        countryNamesDto.addCountryName(new CountryNameDto("Iran"));
        countryNamesDto.addCountryName(new CountryNameDto("US"));
        countryNamesDto.addCountryName(new CountryNameDto("Germany"));

        iranDto = new CountryDto(
                "Iran",
                "Tehran",
                "IR",
                83993,
                1.4f,
                new CurrencyDto("IRR", "Rial"));


        usDto = new CountryDto(
                "United States",
                "Washington, D.C.",
                "US",
                331003,
                0.6f,
                new CurrencyDto("USD", "Us Dollar"));

        usWeatherDto = new CountryWeatherDto(
                "United States",
                "Washington, D.C.",
                2.03f,
                291,
                7,
                45);
    }

    @BeforeEach
    void addCountryServiceMockFunctions() {
        when(countryService.getAllCountriesNames()).thenReturn(countryNamesDto);
        when(countryService.getCountryByName("Iran")).thenReturn(iranDto);
        when(countryService.getCountryByName("US")).thenReturn(usDto);
        when(countryService.getCountryWeatherByCountryName("US")).thenReturn(usWeatherDto);
    }

    @Test
    void testGetCountriesApi() throws Exception {
        this.mockMvc.perform(get("/countries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(countryNamesDto.getCountries().size()))
                .andExpect(jsonPath("$.countries[*].name").value(
                        containsInAnyOrder(countryNamesDto.getCountries()
                                .stream().map(CountryNameDto::getName).toArray())));

    }

    @Test
    void testGetCountryByNameApi() throws Exception {
        this.mockMvc.perform(get("/countries/Iran"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(iranDto.getName()))
                .andExpect(jsonPath("$.capital").value(iranDto.getCapital()))
                .andExpect(jsonPath("$.iso2").value(iranDto.getIso2()))
                .andExpect(jsonPath("$.population").value(iranDto.getPopulation()))
                .andExpect(jsonPath("$.pop_growth").value(iranDto.getPop_growth()));
    }

    @Test
    void testGetCountryWeatherApi() throws Exception {
        this.mockMvc.perform(get("/countries/US/weather"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country_name").value(usWeatherDto.getCountry_name()))
                .andExpect(jsonPath("$.capital").value(usWeatherDto.getCapital()))
                .andExpect(jsonPath("$.wind_speed").value(usWeatherDto.getWind_speed()))
                .andExpect(jsonPath("$.wind_degrees").value(usWeatherDto.getWind_degrees()))
                .andExpect(jsonPath("$.temp").value(usWeatherDto.getTemp()))
                .andExpect(jsonPath("$.humidity").value(usWeatherDto.getHumidity()));
    }
}
