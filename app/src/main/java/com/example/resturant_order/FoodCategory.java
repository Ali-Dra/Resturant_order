package com.example.resturant_order;

public class FoodCategory {
    private int id;
    private String name;
    private int imageResId;
    private int places;

    public FoodCategory( int id,String name, int imageResId, int places) {
        this.name = name;
        this.imageResId = imageResId;
        this.places = places;
        this.id=id;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public int getPlaces() { return places; }

    public int getId() { return id;
    }
}
