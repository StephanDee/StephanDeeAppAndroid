package de.stephandee.stephandeeappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.stephandee.stephandeeappandroid.dbaccess.APIUtils;
import de.stephandee.stephandeeappandroid.dbaccess.IProductService;
import de.stephandee.stephandeeappandroid.models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private IProductService iProductService;
    private List<Product> mProduct = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.product_title);
        // toolbar.setSubtitle("StephanDeeApp");
        // toolbar.setLogo(android.R.drawable.);

        FloatingActionButton fab = findViewById(R.id.addProductButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                MainActivity.this.startActivityForResult(intent, 1);
            }
        });

        iProductService = APIUtils.getProductService();
        Call<List<Product>> call = iProductService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    mProduct = response.body();
                    initRecyclerView(mProduct);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Es ist ein Fehler aufgetreten.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String productId = null;
        String productName = null;
        String productDescription = null;
        String productPrice = null;

        if (resultCode == 1 || resultCode == 2) {
            productId = data.getStringExtra("product_id");
            productName = data.getStringExtra("product_name");
            productDescription = data.getStringExtra("product_description");
            productPrice = data.getStringExtra("product_price");
        }

        switch (resultCode) {
            case 1: {
                Product product = new Product(productName, Float.parseFloat(productPrice));
                product.setDescription(productDescription);
                mProduct.add(product);
                recyclerView.getAdapter().notifyItemInserted(mProduct.size() -1);
                break;
            }
            case 2: {

                for (int i = 0; i < mProduct.size(); i++) {
                    Product product = mProduct.get(i);
                    if (product.getId().equals(productId)) {
                        product.setName(productName);
                        product.setDescription(productDescription);
                        product.setPrice(Float.parseFloat(productPrice));
                        recyclerView.getAdapter().notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(List<Product> products) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.productList);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, products);
        recyclerView.setAdapter(adapter);
    }
}
