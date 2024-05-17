package edu.sharif.webproject.service;

import edu.sharif.webproject.service.country.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    void testGetAllCountriesNames() {

    }
}
