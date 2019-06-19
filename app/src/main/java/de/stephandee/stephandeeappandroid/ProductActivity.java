package de.stephandee.stephandeeappandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.stephandee.stephandeeappandroid.dbaccess.APIUtils;
import de.stephandee.stephandeeappandroid.dbaccess.IProductService;
import de.stephandee.stephandeeappandroid.models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "ProductActivity";

//    private IProductService iProductService;
//    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.product_title);

//        iProductService = APIUtils.getProductService();
//        Call<Product> call = iProductService.getProduct();
//        call.enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
//                if (response.isSuccessful()) {
//                    mProduct = response.body();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//                Toast.makeText(ProductActivity.this, "Es ist ein Fehler aufgetreten.", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
