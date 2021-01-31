package com.example.ezycommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.JavaClassObject.Transaction;
import com.example.ezycommerce.R;

import java.util.ArrayList;
import java.util.List;

public class OrderTransactionAdapter extends RecyclerView.Adapter<OrderTransactionAdapter.ViewHolder>{

    public interface OnOclickOrderButtonListener{
        void updateOrderTotalPriceText(Transaction transaction, int qyt);
    }

    private List<Transaction> transactions;
    private Context ctx;
    private OnOclickOrderButtonListener onOclickOrderButtonListener;
    public OrderTransactionAdapter(Context ctx,OnOclickOrderButtonListener onOclickOrderButtonListener)
    {
        this.ctx = ctx;
        this.transactions = new ArrayList<>();
        this.onOclickOrderButtonListener = onOclickOrderButtonListener;
    }

    public void setProducts(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.order_transaction_row ,parent,false);
        return new OrderTransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Transaction transaction = transactions.get(position);
        Product p = transaction.getProduct();

        holder.tvProductName.setText(p.getName());
        holder.tvProductPrice.setText(p.getPrice().toString());
        holder.tvQuantity.setText(Integer.toString(transaction.getQuantity()));

        Glide.with(ctx)
                .load(p.getImageURL())
                .into(holder.ivProductImage);

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qyt = transaction.getQuantity() - 1;
                if (qyt == 0){
                    transactions.remove(transaction);
                }

                onOclickOrderButtonListener.updateOrderTotalPriceText(transaction,qyt);
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qyt = transaction.getQuantity() + 1;
                onOclickOrderButtonListener.updateOrderTotalPriceText(transaction,qyt);
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvProductPrice;
        TextView tvProductName;
        TextView tvQuantity;
        ImageView ivProductImage;
        Button btnDecrease;
        Button btnIncrease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}
