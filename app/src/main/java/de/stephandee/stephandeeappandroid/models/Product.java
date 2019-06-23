package de.stephandee.stephandeeappandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * The product model.
 */
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

    /**
     * The constructor of product.
     *
     * @param name The name
     * @param price The price
     */
    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    public String getId() {
        return _id;
    }

    /**
     * Sets the ID.
     *
     * @param id The ID
     */
    public  void setId(String id) { this._id = id; }

    /**
     * Gets the created/modified date.
     *
     * @return The Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the name.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product price.
     *
     * @return The price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price The price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Gets the version.
     *
     * @return The version
     */
    public int getV() {
        return __v;
    }
}
