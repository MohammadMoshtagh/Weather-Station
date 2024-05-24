package edu.sharif.webproject.country;

import edu.sharif.webproject.config.RabbitMQConfig;
import edu.sharif.webproject.country.entity.dto.CountryDto;
import edu.sharif.webproject.country.entity.dto.CountryNamesResponse;
import edu.sharif.webproject.country.entity.dto.CountryWeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("")
    public CountryNamesResponse getCountries(@RequestParam int pageNum, @RequestParam int pageSize) {
        return countryService.getAllCountriesNames(pageNum, pageSize);
    }

    @GetMapping("/{name}")
    public CountryDto getCountry(@PathVariable String name) {
        return (CountryDto) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.QUEUE_NAME, name);
    }

    @GetMapping("/{name}/weather")
    public CountryWeatherDto getCountryWeather(@PathVariable String name) {
        return countryService.getCountryWeatherByCountryName(name);
    }
}
