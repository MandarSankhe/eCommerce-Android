package com.example.project2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductsDAO {

    @Insert
    public void addProduct(ProductsDB product);
    @Update
    public void updateProduct(ProductsDB product);
    @Query("SELECT * FROM Products")
    public List<ProductsDB> listProducts();
    @Query("SELECT * FROM Products WHERE id=:id")
    public ProductsDB getProductById(Integer id);
    @Query("DELETE FROM Products WHERE id=:id")
    public void removeProduct(Integer id);

    @Query("SELECT id, mainImage, secondImage, thirdImage FROM Products WHERE id=:id")
    public List<ProductsDB> getImagesByProductId(Integer id);

}
