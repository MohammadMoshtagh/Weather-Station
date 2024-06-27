package edu.sharif.webproject.weather.country.entity.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CountryNamesResponse {

    private final List<CountryNameDto> countries;
    private int count;

    public CountryNamesResponse() {
        this.countries = new ArrayList<>();
    }

    public void addCountryName(CountryNameDto countryName) {
        countries.add(countryName);
        count++;
    }
}
