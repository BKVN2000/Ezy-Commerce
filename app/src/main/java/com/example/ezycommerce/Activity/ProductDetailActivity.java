package com.example.ezycommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ezycommerce.DatabaseInstance.TransactionDBModel;
import com.example.ezycommerce.Fragment.ProductDetailFragment;
import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.JavaClassObject.Transaction;
import com.example.ezycommerce.R;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailFragment.ProductDetailFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        int productId =  (int)getIntent().getExtras().get("ID");
        productDetailFragment.LoadView(productId);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentProductDetail,productDetailFragment).addToBackStack(null).commit();
    }

    @Override
    public void onClickButtonBuy(Product p) {
        Intent intent = new Intent(this,CartOrderListActivity.class);
        TransactionDBModel transactionDBModel = new TransactionDBModel(this);

        transactionDBModel.AddTransaction(new Transaction(1,p));
        startActivity(intent);
    }
}