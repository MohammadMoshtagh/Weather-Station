package edu.sharif.webproject.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CountryDto {

    @JsonProperty
    private String name;
    @JsonProperty
    private String capital;
    @JsonProperty
    private String iso2;
    @JsonProperty
    private long population;
    @JsonProperty("pop_growth")
    private float popGrowth;
    @JsonProperty
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
        this.popGrowth = pop_growth;
        this.currency = currency;
    }

}
