package com.cavanosa.crudretrofitapp.interfaces;

import com.cavanosa.crudretrofitapp.dto.ProductDto;
import com.cavanosa.crudretrofitapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CRUDInterface {

    @GET("product")
    Call<List<Product>> getAll();

    @GET("product/{id}")
    Call<Product> getOne(@Path("id") int id);

    @POST("product")
    Call<Product> create(@Body ProductDto dto);
}
