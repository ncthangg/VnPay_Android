package com.example.vnpay.model;

import java.io.Serializable;

public class Drink implements Serializable {
    private String name;
    private String description;
    private int image;
    private int price;

    public Drink(String name, String description, int image, int price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
