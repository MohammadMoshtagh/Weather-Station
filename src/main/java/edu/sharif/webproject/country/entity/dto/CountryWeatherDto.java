package edu.sharif.webproject.country.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CountryWeatherDto {

    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty
    private String capital;
    @JsonProperty("wind_speed")
    private float windSpeed;
    @JsonProperty("wind_degrees")
    private float windDegrees;
    @JsonProperty
    private float temp;
    @JsonProperty
    private float humidity;

    public CountryWeatherDto(
            String CountryName,
            String capital,
            float wind_speed,
            float wind_degrees,
            float temp,
            float humidity) {
        this.countryName = CountryName;
        this.capital = capital;
        this.windSpeed = wind_speed;
        this.windDegrees = wind_degrees;
        this.temp = temp;
        this.humidity = humidity;
    }
}
