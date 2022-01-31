package com.cavanosa.crudretrofitapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cavanosa.crudretrofitapp.R;
import com.cavanosa.crudretrofitapp.activities.DetailActivity;
import com.cavanosa.crudretrofitapp.model.Product;

import java.util.List;

public class ProductsAdapter extends BaseAdapter {

    List<Product> products;

    Context context;
    TextView nameText;
    Button viewButton;

    public ProductsAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.product_list, viewGroup, false);
        }
        nameText = view.findViewById(R.id.nameText);
        nameText.setText(products.get(position).getName());
        viewButton = view.findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDetail(products.get(position).getId());
            }
        });
        return view;
    }

    private void callDetail(int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
