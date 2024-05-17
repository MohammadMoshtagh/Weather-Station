package edu.sharif.webproject.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.sharif.webproject.model.dto.CountryNameDto;
import edu.sharif.webproject.model.dto.CountryNamesDto;
import edu.sharif.webproject.service.country.CountryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    private static CountryNamesDto countryNamesDto;

    @BeforeAll
    static void createCountryNamesMock() {
        countryNamesDto = new CountryNamesDto();
        countryNamesDto.addCountryName(new CountryNameDto("Iran"));
        countryNamesDto.addCountryName(new CountryNameDto("US"));
        countryNamesDto.addCountryName(new CountryNameDto("Germany"));
    }

    @Test
    void testGetCountriesApi() throws Exception {
        when(countryService.getAllCountriesNames()).thenReturn(countryNamesDto);
        this.mockMvc.perform(get("/countries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(countryNamesDto.getCountries().size()))
                .andExpect(jsonPath("$.countries[*].name").value(
                        containsInAnyOrder(countryNamesDto.getCountries()
                                .stream().map(CountryNameDto::getName).toArray())));

    }
}
