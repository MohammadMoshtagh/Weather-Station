package edu.sharif.webproject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CountryName {
    private String name;

    public CountryName(String name) {
        this.name = name;
    }
}
