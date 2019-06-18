package de.stephandee.stephandeeappandroid.dbaccess;

import java.util.List;

import de.stephandee.stephandeeappandroid.models.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IProductService {

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/{id}")
    Call<Product> getProduct();

    @POST("products")
    Call<Product> addProduct(@Body Product product);

    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("_id") String id, @Body Product product);

    @DELETE("products/{id}")
    Call<Product> deleteProduct(@Path("_id") String id);
}
