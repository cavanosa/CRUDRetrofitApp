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

public class EditActivity extends AppCompatActivity {

    Product product;
    EditText nameText;
    EditText priceText;
    Button editButton;
    
    CRUDInterface crudInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent detailIntent = getIntent();
        product = (Product) detailIntent.getSerializableExtra("product");
        //Log.i("prod: ", product.toString());
        nameText = findViewById(R.id.nameText);
        priceText = findViewById(R.id.priceText);
        nameText.setText(product.getName());
        priceText.setText(String.valueOf(product.getPrice()));
        editButton = findViewById(R.id.editButton);
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editButton.setEnabled(buttonEnabled());
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
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editButton.setEnabled(buttonEnabled());
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDto dto = new ProductDto(nameText.getText().toString(), Integer.valueOf(priceText.getText().toString()));
                edit(dto);
            }
        });
    }

    private void edit(ProductDto dto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        int id = product.getId();
        Call<Product> call = crudInterface.edit(id, dto);
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
                Toast toast = Toast.makeText(getApplicationContext(), product.getName() + " edited!!", Toast.LENGTH_LONG);
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