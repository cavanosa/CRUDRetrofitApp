package com.cavanosa.crudretrofitapp.interfaces;

import com.cavanosa.crudretrofitapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CRUDInterface {

    @GET("product")
    Call<List<Product>> getAll();
}
