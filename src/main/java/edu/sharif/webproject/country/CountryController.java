package edu.sharif.webproject.country;

import edu.sharif.webproject.country.dto.CountryDto;
import edu.sharif.webproject.country.dto.CountryNamesDto;
import edu.sharif.webproject.country.dto.CountryWeatherDto;
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
    public CountryNamesDto getCountries() {
        return countryService.getAllCountriesNames();
    }

    @GetMapping("/{name}")
    public CountryDto getCountry(@PathVariable String name) {
        return countryService.getCountryByName(name);
    }

    @GetMapping("/{name}/weather")
    public CountryWeatherDto getCountryWeather(@PathVariable String name) {
        return countryService.getCountryWeatherByCountryName(name);
    }
}
