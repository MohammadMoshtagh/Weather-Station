package edu.sharif.webproject.controller;

import edu.sharif.webproject.model.dto.CountryDto;
import edu.sharif.webproject.model.dto.CountryNamesDto;
import edu.sharif.webproject.model.dto.CountryWeatherDto;
import edu.sharif.webproject.service.country.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/countries")
public class CountryController {

    public CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("")
    public CountryNamesDto getCountries() throws Exception {
        return countryService.getAllCountriesNames();
    }

    @GetMapping("/{name}")
    public CountryDto getCountry(@PathVariable String name) throws Exception {
        return countryService.getCountryByName(name);
    }

    @GetMapping("/{name}/weather")
    public CountryWeatherDto getCountryWeather(@PathVariable String name) throws Exception {
        return countryService.getCountryWeatherByCountryName(name);
    }
}
