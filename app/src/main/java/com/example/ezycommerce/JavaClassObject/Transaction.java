package com.example.ezycommerce.JavaClassObject;

import java.io.Serializable;
import java.util.UUID;

public class Transaction implements Serializable {
    private int quantity;
    private Product product;
    private UUID transactionID;

    public Transaction(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public Transaction(){
    }
    public UUID getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(UUID cartID) {
        this.transactionID = cartID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getSubPrice() {
        return product.getPrice() * this.quantity;
    }
}
