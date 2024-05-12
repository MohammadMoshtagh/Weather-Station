package edu.sharif.webproject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Country {

    private String name;
    private String capital;
    private String iso2;
    private long population;
    private float pop_growth;
    private Currency currency;

    public Country(String name,
                   String capital,
                   String iso2,
                   long population,
                   float pop_growth,
                   Currency currency) {
        this.name = name;
        this.capital = capital;
        this.iso2 = iso2;
        this.population = population;
        this.pop_growth = pop_growth;
        this.currency = currency;
    }

}
