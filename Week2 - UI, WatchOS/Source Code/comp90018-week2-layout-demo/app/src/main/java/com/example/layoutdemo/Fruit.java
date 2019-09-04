package com.example.layoutdemo;


// Fruit class contains fruitImage and fruitName
// With automatically generated Setter and Getter
public class Fruit {
    // fruitImage to store the resource id if fruit image
    private int fruitImage;
    // fruitName to store the string of fruit name
    private String fruitName;

    public Fruit(int fruitImage, String fruitName) {
        this.fruitImage = fruitImage;
        this.fruitName = fruitName;
    }

    public int getFruitImage() {
        return fruitImage;
    }

    public void setFruitImage(int fruitImage) {
        this.fruitImage = fruitImage;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }
}
