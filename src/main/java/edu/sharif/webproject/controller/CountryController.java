package edu.sharif.webproject.controller;

import edu.sharif.webproject.model.CountryNames;
import edu.sharif.webproject.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
