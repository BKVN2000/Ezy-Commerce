package com.example.ezycommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce.Fragment.ProductListFragment;
import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> products;
    private Context ctx;
    private ProductListFragment.ProductListFragmentListener handler;
    public ProductAdapter(Context ctx, ProductListFragment.ProductListFragmentListener handler)
    {
        this.ctx = ctx;
        this.products = new ArrayList<>();
        this.handler = handler;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.product_row ,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductAuthor.setText(product.getAuthor());
        holder.tvProductPrice.setText(product.getPrice().toString());
        Glide.with(ctx)
                .load(product.getImageURL())
                .into(holder.ivProductImage);

        holder.ProductLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.itemClicked(product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvProductPrice;
        TextView tvProductName;
        TextView tvProductAuthor;
        CardView ProductLayout;
        ImageView ivProductImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductAuthor = itemView.findViewById(R.id.tvProductAuthor);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            ProductLayout = itemView.findViewById(R.id.ProductLayout);
        }
    }
}
