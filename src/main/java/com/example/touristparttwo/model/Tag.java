package com.example.touristparttwo.model;

public enum Tag {
    ANIMALS("Animals"),
    ENTERTAINMENT("Entertainment"),
    INDOORS("Indoors"),
    OUTDOORS("Outdoors"),
    ART("Art"),
    MUSEUM("Museum"),
    RIDES("Rides"),
    HISTORICAL("Historical"),
    RELIGION("Religion");

    private final String name;

    Tag(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
