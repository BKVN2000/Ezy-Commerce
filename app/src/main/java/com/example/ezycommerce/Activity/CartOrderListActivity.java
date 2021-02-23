package com.example.ezycommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ezycommerce.Adapter.OrderTransactionAdapter;
import com.example.ezycommerce.DatabaseInstance.TransactionDBModel;
import com.example.ezycommerce.JavaClassObject.Transaction;
import com.example.ezycommerce.R;

public class CartOrderListActivity extends AppCompatActivity implements OrderTransactionAdapter.OnOclickOrderButtonListener {
    private RecyclerView rvOrderTransaction;
    private TextView tvTotalPrice;
    private TransactionDBModel transactionDBModel;
    private OrderTransactionAdapter orderTransactionListAdapter;
    private Button btnPayNow;
    private Button btnOrderMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_order_list);
        transactionDBModel = new TransactionDBModel(this);

        rvOrderTransaction = findViewById(R.id.rvOrderTransaction);
        btnPayNow = findViewById(R.id.btnPayNow);
        btnOrderMore = findViewById(R.id.btnOrderMore);

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText(Float.toString(transactionDBModel.GetTotalPrice()));

        orderTransactionListAdapter  = new OrderTransactionAdapter(this,this);
        orderTransactionListAdapter.setProducts(transactionDBModel.getTransactions());
        rvOrderTransaction.setAdapter(orderTransactionListAdapter);

        rvOrderTransaction.setLayoutManager(new LinearLayoutManager(this));

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionDBModel transactionDBModel = new TransactionDBModel(CartOrderListActivity.this);
                transactionDBModel.CheckOut();

                Intent intent= new Intent(CartOrderListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnOrderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CartOrderListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void updateOrderTotalPriceText(Transaction transaction,int qyt) {
        transactionDBModel.updateProductQyt(transaction,qyt);
        transaction.setQuantity(qyt);

        orderTransactionListAdapter.notifyDataSetChanged();

        Float totalPrice = transactionDBModel.GetTotalPrice();
        tvTotalPrice.setText(Float.toString(totalPrice));

        if (totalPrice ==0){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

}