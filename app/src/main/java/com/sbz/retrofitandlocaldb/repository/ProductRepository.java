package com.sbz.retrofitandlocaldb.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sbz.retrofitandlocaldb.dao.ProductDAO;
import com.sbz.retrofitandlocaldb.database.MyDatabase;
import com.sbz.retrofitandlocaldb.model.Product;

import java.util.List;

public class ProductRepository {

    private MyDatabase database;
    private LiveData<List<Product>> getAllProducts;

    public ProductRepository(Application application) {
        database = MyDatabase.getDbInstance(application);
        getAllProducts = database.productDAO().getAllProducts();
    }

    public void insert(List<Product> productList) {
        new InsertAsyncTask(database).execute(productList);
    }

    public LiveData<List<Product>> getAllProducts() {
        return getAllProducts;
    }

    class InsertAsyncTask extends AsyncTask<List<Product>, Void, Void> {

        private ProductDAO productDAO;

        InsertAsyncTask(MyDatabase database) {
            productDAO = database.productDAO();
        }

        @Override
        protected Void doInBackground(List<Product>... lists) {
            productDAO.insert(lists[0]);
            return null;
        }
    }
}
