package com.example.resturant_order;


public class FoodItem {
    private int id;
    private String name;
    private double price;
    private int imageResId;
    private int categoryId;

    public FoodItem(int id, String name, double price, int imageResId, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public int getCategoryId() { return categoryId; }
}
