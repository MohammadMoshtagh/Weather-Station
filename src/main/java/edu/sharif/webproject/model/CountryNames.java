package edu.sharif.webproject.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CountryNames {

    private final List<CountryName> countries;
    private int count;

    public CountryNames() {
        this.countries = new ArrayList<>();
    }

    public void addCountryName(CountryName countryName) {
        countries.add(countryName);
        count++;
    }
}
