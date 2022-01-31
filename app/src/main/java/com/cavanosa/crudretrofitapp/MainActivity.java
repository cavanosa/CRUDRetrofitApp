package com.cavanosa.crudretrofitapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.cavanosa.crudretrofitapp.adapters.ProductsAdapter;
import com.cavanosa.crudretrofitapp.interfaces.CRUDInterface;
import com.cavanosa.crudretrofitapp.model.Product;
import com.cavanosa.crudretrofitapp.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Product> products;
    CRUDInterface crudInterface;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        getAll();
    }

    private void getAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<List<Product>> call = crudInterface.getAll();
        call.enqueue(new Callback<List<Product>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                products = response.body();
                ProductsAdapter productsAdapter = new ProductsAdapter(products, getApplicationContext());
                listView.setAdapter(productsAdapter);
                products.forEach(p -> Log.i("Prods: ", p.toString()));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }

}