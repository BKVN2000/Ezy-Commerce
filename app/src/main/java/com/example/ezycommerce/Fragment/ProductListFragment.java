package com.example.ezycommerce.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce.Adapter.ProductAdapter;
import com.example.ezycommerce.JavaClassObject.APIClient;
import com.example.ezycommerce.JavaClassObject.EzyCommerceService;
import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.JavaClassObject.ProductResponse;
import com.example.ezycommerce.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductListFragment extends Fragment {
    private RecyclerView rvProducts;
    private ProductAdapter productAdapter;
    private ProductAdapter.ProductAdapterOnClickHandler listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        rvProducts = view.findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        String categoryName = (getArguments() == null) ? "" : getArguments().getString("CategoryName");

        productAdapter = new ProductAdapter(requireContext(),listener);
        rvProducts.setAdapter(productAdapter);

        Retrofit retrofit = APIClient.getRetrofit();
        EzyCommerceService service = retrofit.create(EzyCommerceService.class);
        Call<ProductResponse> call = service.getBooks("2201819002", "Benedict Kelvin");

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> listBooks = response.body().products;
                List<Product> tempList = new ArrayList<>();

                if (!categoryName.equals("")){
                    for(Product p : listBooks){
                        if (p.getCategory().equals(categoryName)){
                            tempList.add(p);
                        }
                    }

                    productAdapter.setProducts(tempList);
                    return;
                }

                productAdapter.setProducts(listBooks);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("error", "onFailure: " + t.getMessage());
                call.cancel();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProductAdapter.ProductAdapterOnClickHandler){
            listener = (ProductAdapter.ProductAdapterOnClickHandler)context;
        }
    }
}
