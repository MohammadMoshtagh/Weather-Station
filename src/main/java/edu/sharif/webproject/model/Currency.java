package edu.sharif.webproject.model;

import lombok.Getter;

@Getter
public class Currency {

    private String code;
    private String name;

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
