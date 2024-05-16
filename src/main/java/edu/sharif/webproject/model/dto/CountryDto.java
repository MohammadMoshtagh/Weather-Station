package edu.sharif.webproject.model.dto;

import lombok.Getter;

@Getter
public class CountryDto {

    private String name;
    private String capital;
    private String iso2;
    private long population;
    private float pop_growth;
    private CurrencyDto currency;

    public CountryDto(String name,
                      String capital,
                      String iso2,
                      long population,
                      float pop_growth,
                      CurrencyDto currency) {
        this.name = name;
        this.capital = capital;
        this.iso2 = iso2;
        this.population = population;
        this.pop_growth = pop_growth;
        this.currency = currency;
    }

}
