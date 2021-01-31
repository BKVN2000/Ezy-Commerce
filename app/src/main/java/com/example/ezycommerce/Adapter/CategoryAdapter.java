package com.example.ezycommerce.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce.JavaClassObject.Category;
import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    public interface OnCategoryClickListener{
        void update(String categoryName);
    }

    List<Category> categories;
    Context ctx;
    OnCategoryClickListener onClick;

    public CategoryAdapter(Context ctx,OnCategoryClickListener onClick)
    {
        this.ctx = ctx;
        this.categories = new ArrayList<>();
        this.onClick = onClick;
    }

    public void setCategories(List<Product> products) {
        List<Category> tempCategories = new ArrayList<>();
        List<String> tempCategoryString = new ArrayList<>();

        Log.d("err", "setCategories: " + products.size());
        for(Product p : products){
            if (!tempCategoryString.contains(p.getCategory())){
                tempCategories.add(new Category(p.getCategory()));
                tempCategoryString.add(p.getCategory());
            }
        }

        Log.d("err", "setCategoriesasdfdsf: " + tempCategories.size());
        this.categories = tempCategories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.category_row ,parent,false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.tvCategoryName.setText(category.name);

        holder.tvCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.update(category.name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView tvCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}
