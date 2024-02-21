package com.example.touristparttwo.model;

public enum Location {
    COPENHAGEN("Copenhagen"),
    HILLERØD("Hillerød"),
    JELLINGE("Jellinge"),
    BILLUND("Billund"),
    ROSKILDE("Roskilde");

    private final String name;

    Location(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
