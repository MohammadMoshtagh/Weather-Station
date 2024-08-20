package edu.sharif.webproject.weather.country.entity.dto;

import lombok.Getter;

@Getter
public class LocationDto {
    private float latitude;
    private float longitude;
    private String name;
    private String country;
}
