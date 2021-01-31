package com.example.ezycommerce.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ezycommerce.JavaClassObject.APIClient;
import com.example.ezycommerce.JavaClassObject.EzyCommerceService;
import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.JavaClassObject.ProductResponse;
import com.example.ezycommerce.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProductDetailFragment extends Fragment {
    public interface ProductDetailFragmentListener{
        void onClickButtonBuy(Product p);
    }

    TextView tvDescriptionContent;
    Button btnBuy;
    TextView tvProductPrice;
    TextView tvProductName;
    ImageView ivProductImage;
    ProductDetailFragmentListener listener;
    View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        return view;
    }

    public void LoadView(int ID){
        Retrofit retrofit = APIClient.getRetrofit();
        EzyCommerceService service = retrofit.create(EzyCommerceService.class);
        Call<ProductResponse> call = service.getBookDetail(ID,"2201819002", "Benedict Kelvin");

        call.enqueue(new Callback<ProductResponse>(){
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Product p = response.body().products.get(0);
                tvProductName = view.findViewById(R.id.tvProductName);
                tvProductName.setText(p.getName());

                tvProductPrice = view.findViewById(R.id.tvProductPrice);
                tvProductPrice.setText(p.getPrice().toString());

                tvDescriptionContent = view.findViewById(R.id.tvDescriptionContent);
                tvDescriptionContent.setText(p.getDescription());

                ivProductImage = view.findViewById(R.id.ivProductImage);
                Glide.with(requireActivity())
                        .load(p.getImageURL())
                        .into(ivProductImage);

                btnBuy = view.findViewById(R.id.btnBuy);
                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickButtonBuy(p);
                    }
                });
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("Error", "onFailure: error in ProductDetailFragment");
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ProductDetailFragmentListener) {
            listener = (ProductDetailFragmentListener) context;
        }
    }
}