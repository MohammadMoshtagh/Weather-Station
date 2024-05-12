package edu.sharif.webproject.controller;

import edu.sharif.webproject.model.Country;
import edu.sharif.webproject.model.CountryNames;
import edu.sharif.webproject.model.CountryWeather;
import edu.sharif.webproject.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    public CountryService countryService;

    @GetMapping("")
    public CountryNames getCountries() throws Exception {
        return countryService.getCountries();
    }

    @GetMapping("/{name}")
    public Country getCountry(@PathVariable String name) throws Exception {
        return countryService.getCountryByName(name);
    }

    @GetMapping("/{name}/weather")
    public CountryWeather getCountryWeather(@PathVariable String name) throws Exception {
        return countryService.getCountryWeatherByCountryName(name);
    }
}
