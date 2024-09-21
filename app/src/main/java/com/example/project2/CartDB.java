package com.example.project2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartDB {

    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name="user")
    String user;

    @ColumnInfo(name="productId")
    int productId;

    @ColumnInfo(name="quantity")
    int quantity;

    public CartDB(String user, int productId, int quantity) {
        this.user = user;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
