package com.example.ezycommerce.DatabaseInstance;

import android.content.Context;

import com.example.ezycommerce.JavaClassObject.Transaction;

import java.util.ArrayList;

public class TransactionDBModel {
    private CartDatabaseRepository cartDatabaseRepository;

    public TransactionDBModel (Context ctx){
        this.cartDatabaseRepository = cartDatabaseRepository.getInstance(ctx);
    }

    public void updateProductQyt(Transaction tr, int qyt){
       if (qyt == 0){
           cartDatabaseRepository.deleteCart(tr);
           return;
       }

       cartDatabaseRepository.updateCartQyt(tr,qyt);
    }

    public void AddTransaction(Transaction t)
    {
        ArrayList<Transaction> trs = cartDatabaseRepository.getCart();
        boolean isExisted =  false;
        for (Transaction tt : trs){
            if (tt.getProduct().getName().equals(t.getProduct().getName()))
            {
                updateProductQyt(tt,tt.getQuantity() + 1);
                return;
            }
        }

        cartDatabaseRepository.insertCart(t);
    }

    public ArrayList<Transaction> getTransactions(){
        return cartDatabaseRepository.getCart();
    }

    public float GetTotalPrice()
    {
        float total = 0;
        ArrayList<Transaction> transactions =  getTransactions();

        for (Transaction t : transactions){
            total+=t.getSubPrice();
        }

        return total;
    }

    public void CheckOut(){
        cartDatabaseRepository.deleteAllCart();
    }
}
