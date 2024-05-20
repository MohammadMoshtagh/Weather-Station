package edu.sharif.webproject.country;

import edu.sharif.webproject.country.dto.CountryDto;
import edu.sharif.webproject.country.dto.CountryNamesDto;
import edu.sharif.webproject.country.dto.CountryWeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

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
