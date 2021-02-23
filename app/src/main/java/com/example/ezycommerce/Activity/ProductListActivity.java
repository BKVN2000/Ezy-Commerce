package com.example.ezycommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ezycommerce.JavaClassObject.APIClient;
import android.os.Bundle;
import android.util.Log;

import com.example.ezycommerce.Adapter.ProductAdapter;
import com.example.ezycommerce.JavaClassObject.EzyCommerceService;
import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.JavaClassObject.ProductResponse;
import com.example.ezycommerce.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductListActivity extends AppCompatActivity {
    RecyclerView rvProducts;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager( new GridLayoutManager(this, 2));

        productAdapter = new ProductAdapter(this,null);
        rvProducts.setAdapter(productAdapter);

        Retrofit retrofit = APIClient.getRetrofit();
        EzyCommerceService service = retrofit.create(EzyCommerceService.class);
        Call<ProductResponse> call = service.getBooks("2201819002", "Benedict Kelvin");

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> listBooks = response.body().getProducts();
                Log.d("debug", "onResponse: " + productAdapter.getItemCount());
                productAdapter.setProducts(listBooks);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("ror", "onFailure: " +t.getMessage());
                call.cancel();
            }
        });
    }


}