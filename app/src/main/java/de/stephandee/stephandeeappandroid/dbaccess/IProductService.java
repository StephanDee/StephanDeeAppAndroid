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

    @GET("products/")
    Call<List<Product>> getProducts();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") String id);

    @POST("products/")
    Call<Product> createProduct(@Body Product product);

    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("id") String id, @Body Product product);

    @DELETE("products/{id}")
    Call<Product> deleteProduct(@Path("id") String id);
}
