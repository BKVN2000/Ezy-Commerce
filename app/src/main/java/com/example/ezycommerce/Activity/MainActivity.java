package com.example.ezycommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ezycommerce.Adapter.CategoryAdapter;
import com.example.ezycommerce.Adapter.ProductAdapter;
import com.example.ezycommerce.DatabaseInstance.TransactionDBModel;
import com.example.ezycommerce.Fragment.ProductDetailFragment;
import com.example.ezycommerce.Fragment.ProductListFragment;
import com.example.ezycommerce.JavaClassObject.APIClient;
import com.example.ezycommerce.JavaClassObject.Category;
import com.example.ezycommerce.JavaClassObject.EzyCommerceService;
import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.JavaClassObject.ProductResponse;
import com.example.ezycommerce.JavaClassObject.Transaction;
import com.example.ezycommerce.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener, ProductAdapter.ProductAdapterOnClickHandler,ProductDetailFragment.ProductDetailFragmentListener {
    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;
    private FrameLayout fragmentProductList;
    private Fragment productListFragment;
    private ProductDetailFragment productDetailFragment;
    View fragmentProductDetailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentProductDetailContainer = findViewById(R.id.fragmentDetailContainer);
        int layout = 0;
        rvCategories = findViewById(R.id.rvCategories);
        layout = (fragmentProductDetailContainer == null)  ? LinearLayoutManager.HORIZONTAL :  LinearLayoutManager.VERTICAL;

        rvCategories.setLayoutManager(new LinearLayoutManager(this,layout, false));

        CategoryAdapter categoryAdapter = new CategoryAdapter(this,this);
        rvCategories.setAdapter(categoryAdapter);

        productListFragment = new ProductListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentProductList,new ProductListFragment()).addToBackStack(null).commit();

        Retrofit retrofit = APIClient.getRetrofit();
        EzyCommerceService service = retrofit.create(EzyCommerceService.class);
        Call<ProductResponse> call = service.getBooks("2201819002", "Benedict Kelvin");

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> listProducts = response.body().products;
                categoryAdapter.setCategories(listProducts);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("ror", "onFailure: " +t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    public void update(String categoryName) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();

        bundle.putString("CategoryName",categoryName);
        productListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentProductList,productListFragment).addToBackStack(null).commit();
    }

    @Override
    public void itemClicked(int ID) {
        View fragmentProductDetailContainer = findViewById(R.id.fragmentDetailContainer);

        //layar kecil
        if (fragmentProductDetailContainer != null){
            productDetailFragment = new ProductDetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            productDetailFragment.LoadView(ID);

            ft.replace(R.id.fragmentDetailContainer, productDetailFragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        else{
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("ID", ID);
            startActivity(intent);
        }
    }

    @Override
    public void onClickButtonBuy(Product p) {
        Intent intent = new Intent(this,CartOrderListActivity.class);
        TransactionDBModel transactionDBModel = new TransactionDBModel(this);

        transactionDBModel.AddTransaction(new Transaction(1,p));
        startActivity(intent);
    }
}