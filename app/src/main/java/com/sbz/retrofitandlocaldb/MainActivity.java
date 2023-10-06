package com.sbz.retrofitandlocaldb;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sbz.retrofitandlocaldb.adapter.ProductAdapter;
import com.sbz.retrofitandlocaldb.api.API;
import com.sbz.retrofitandlocaldb.api.NetworkInstance;
import com.sbz.retrofitandlocaldb.model.Product;
import com.sbz.retrofitandlocaldb.repository.ProductRepository;
import com.sbz.retrofitandlocaldb.viewModel.ProductViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private API apiInstance;
    private ProductRepository productRepository;
    private RecyclerView rvProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        apiInstance = NetworkInstance.getApiInstance();
        productRepository = new ProductRepository(getApplication());
        networkCall();
        rvProductsList = findViewById(R.id.rv_itemsList);

        rvProductsList.hasFixedSize();
        rvProductsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, productList);
                rvProductsList.setAdapter(productAdapter);
            }
        });


    }

    private void networkCall() {
        Call<List<Product>> call = apiInstance.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}