package edu.sharif.webproject.model.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CountryNamesDto {

    private final List<CountryNameDto> countries;
    private int count;

    public CountryNamesDto() {
        this.countries = new ArrayList<>();
    }

    public void addCountryName(CountryNameDto countryName) {
        countries.add(countryName);
        count++;
    }
}
