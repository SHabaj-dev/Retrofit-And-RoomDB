package com.sbz.retrofitandlocaldb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sbz.retrofitandlocaldb.model.Product;
import com.sbz.retrofitandlocaldb.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> getAllProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        getAllProducts = productRepository.getAllProducts();
    }

    public void insert(List<Product> productList) {
        productRepository.insert(productList);
    }

    public LiveData<List<Product>> getAllProducts() {
        return getAllProducts;
    }
}
