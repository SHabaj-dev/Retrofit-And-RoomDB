package com.sbz.retrofitandlocaldb.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sbz.retrofitandlocaldb.model.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Product> productList);

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAllProducts();

    @Query("DELETE FROM product")
    public void deleteAll();
}
