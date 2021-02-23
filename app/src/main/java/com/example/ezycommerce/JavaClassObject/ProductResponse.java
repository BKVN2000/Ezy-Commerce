package com.example.ezycommerce.JavaClassObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    @SerializedName("nim")
    @Expose
    private String NIM;

    @SerializedName("nama")
    @Expose
    private String Name;

    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public String getNIM() {
        return NIM;
    }

    public void setNIM(String NIM) {
        this.NIM = NIM;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
