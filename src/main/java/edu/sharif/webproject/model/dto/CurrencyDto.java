package edu.sharif.webproject.model.dto;

import lombok.Getter;

@Getter
public class CurrencyDto {

    private String code;
    private String name;

    public CurrencyDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
