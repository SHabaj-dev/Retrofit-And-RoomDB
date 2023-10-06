package com.sbz.retrofitandlocaldb.api;

import com.sbz.retrofitandlocaldb.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("products")
    Call<List<Product>> getAllProducts();

}
