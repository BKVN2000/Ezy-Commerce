package com.example.ezycommerce.JavaClassObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    @SerializedName("nim")
    @Expose
    public String NIM;

    @SerializedName("nama")
    @Expose
    public String Name;

    @SerializedName("products")
    @Expose
    public List<Product> products = null;
}
