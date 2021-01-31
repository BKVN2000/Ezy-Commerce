package com.example.ezycommerce.JavaClassObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer Id;

    @SerializedName("name")
    @Expose
    private String Name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private Float Price;

    @SerializedName("author")
    @Expose
    private String Author;

    @SerializedName("type")
    @Expose
    private String Type;

    @SerializedName("img")
    @Expose
    private String ImageURL;

    @SerializedName("category")
    @Expose
    private String Category;

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return Price;
    }

    public String getAuthor() {
        return Author;
    }

    public String getType() {
        return Type;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getCategory() {
        return Category;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
