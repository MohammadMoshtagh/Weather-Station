package edu.sharif.webproject.model.dto;

import lombok.Getter;

@Getter
public class CountryNameDto {
    private String name;

    public CountryNameDto(String name) {
        this.name = name;
    }
}
