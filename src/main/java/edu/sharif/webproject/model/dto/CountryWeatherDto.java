package edu.sharif.webproject.model.dto;

import lombok.Getter;

@Getter
public class CountryWeatherDto {

    private String country_name;
    private String capital;
    private float wind_speed;
    private float wind_degrees;
    private float temp;
    private float humidity;

    public CountryWeatherDto(
            String country_name,
            String capital,
            float wind_speed,
            float wind_degrees,
            float temp,
            float humidity) {
        this.country_name = country_name;
        this.capital = capital;
        this.wind_speed = wind_speed;
        this.wind_degrees = wind_degrees;
        this.temp = temp;
        this.humidity = humidity;
    }
}
