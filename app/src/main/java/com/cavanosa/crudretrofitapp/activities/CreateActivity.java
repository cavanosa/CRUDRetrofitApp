package com.cavanosa.crudretrofitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cavanosa.crudretrofitapp.MainActivity;
import com.cavanosa.crudretrofitapp.R;
import com.cavanosa.crudretrofitapp.dto.ProductDto;
import com.cavanosa.crudretrofitapp.interfaces.CRUDInterface;
import com.cavanosa.crudretrofitapp.model.Product;
import com.cavanosa.crudretrofitapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {

    EditText nameText;
    EditText priceText;
    Button createButton;

    CRUDInterface crudInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        nameText = findViewById(R.id.nameText);
        priceText = findViewById(R.id.priceText);
        createButton = findViewById(R.id.createButton);
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        priceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        createButton.setEnabled(buttonEnabled());
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDto dto = new ProductDto(nameText.getText().toString(), Integer.valueOf(priceText.getText().toString()));
                create(dto);
            }
        });
    }

    private void create(ProductDto dto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Product> call = crudInterface.create(dto);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }
                Product product = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), product.getName() + " created!!", Toast.LENGTH_LONG);
                toast.show();
                callMain();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private boolean buttonEnabled() {
        return nameText.getText().toString().trim().length() > 0 && priceText.getText().toString().trim().length() > 0;
    }
}