package com.example.ezycommerce.JavaClassObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EzyCommerceService {

    @GET("book/{id}/users")
    Call<ProductResponse> getBookDetail(
        @Path("id") int bookId,
        @Query(value="nim") String Nim,
        @Query(value="nama") String Name
    );

    @GET("book")
    Call<ProductResponse> getBooks(
        @Query(value="nim") String Nim,
        @Query(value="nama") String Name
    );
}
