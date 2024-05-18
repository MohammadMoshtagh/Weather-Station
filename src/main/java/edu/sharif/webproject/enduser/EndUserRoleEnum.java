package edu.sharif.webproject.enduser;

public enum EndUserRoleEnum {
    USER(0),
    ADMIN(1);

    public final int id;

    EndUserRoleEnum(int id) {
        this.id = id;
    }
}
