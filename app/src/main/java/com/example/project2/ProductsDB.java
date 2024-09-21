package com.example.project2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Products")
public class ProductsDB implements Serializable {

    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name="mainImage")
    String mainImage;

    @ColumnInfo(name="secondImage")
    String secondImage;

    @ColumnInfo(name="thirdImage")
    String thirdImage;

    @ColumnInfo(name="name")
    String name;

    @ColumnInfo(name="price")
    Double price;

    @ColumnInfo(name="shortDescription")
    String shortDescription;

    @ColumnInfo(name="longDescription")
    String longDescription;

    @ColumnInfo(name="type")
    String type;

    public ProductsDB(
            String mainImage,
            String secondImage,
            String thirdImage,
            String name,
            Double price,
            String shortDescription,
            String longDescription,
            String type)
    {
        this.mainImage = mainImage;
        this.secondImage = secondImage;
        this.thirdImage = thirdImage;
        this.name = name;
        this.price = price;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.type = type;
        this.id = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setSecondImage(String secondImage) {
        this.secondImage = secondImage;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public void setThirdImage(String thirdImage) {
        this.thirdImage = thirdImage;
    }

    public String getThirdImage() {
        return thirdImage;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

}
