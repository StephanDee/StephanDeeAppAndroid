package de.stephandee.stephandeeappandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Product {
    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private float price;

    @SerializedName("__v")
    @Expose
    private int __v;

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return _id;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getV() {
        return __v;
    }
}
