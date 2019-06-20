package de.stephandee.stephandeeappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.stephandee.stephandeeappandroid.dbaccess.APIUtils;
import de.stephandee.stephandeeappandroid.dbaccess.IProductService;
import de.stephandee.stephandeeappandroid.models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "ProductActivity";

    private IProductService iProductService;
    private Product mProduct;
    private EditText editName, editDescription, editPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);
        Toolbar toolbar = findViewById(R.id.productToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.product_title);

        editName = findViewById(R.id.editProductName);
        editDescription = findViewById(R.id.editProductDescription);
        editPrice = findViewById(R.id.editProductPrice);
        Button editResetButton = findViewById(R.id.editProductButtonReset);
        Button editSaveButton = findViewById(R.id.editProductButtonSave);

        final boolean hasExtrasProductId = getIntent().hasExtra("product_id");
        if (hasExtrasProductId) {
            // UPDATE product
            String productId = getIntent().getExtras().getString("product_id");

            iProductService = APIUtils.getProductService();
            Call<Product> call = iProductService.getProduct(productId);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        mProduct = response.body();
                        editName.setText(mProduct.getName());
                        editDescription.setText(mProduct.getDescription());
                        editPrice.setText(Float.toString(mProduct.getPrice()));
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Toast.makeText(ProductActivity.this, "Es ist ein Fehler aufgetreten.", Toast.LENGTH_LONG).show();
                }
            });
        }

        editResetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                resetProduct(mProduct);
            }
        });

        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editName.getText().toString().isEmpty() && !editPrice.getText().toString().isEmpty()) {
                    if (hasExtrasProductId) {
                        mProduct.setName(editName.getText().toString());
                        mProduct.setDescription(editDescription.getText().toString());
                        mProduct.setPrice(Float.parseFloat(editPrice.getText().toString()));
                        updateProduct(mProduct);
                    } else {
                        Product product = new Product(
                                editName.getText().toString(),
                                Float.parseFloat(editPrice.getText().toString())
                        );
                        addProduct(product);
                    }
                } else {
                    Toast.makeText(ProductActivity.this, "Name und Preis sind Pflichtfelder.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void resetProduct(final Product product) {
        if (product != null) {
            editName.setText(product.getName());
            editDescription.setText(product.getDescription());
            editPrice.setText(Float.toString(product.getPrice()));
        } else {
            editName.setText("");
            editDescription.setText("");
            editPrice.setText("");
        }
    }

    private void addProduct(final Product product) {
        iProductService = APIUtils.getProductService();
        Call<Product> call = iProductService.createProduct(product);
        call.enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Intent intent = getPriviousIntent(response);
                    setResult(1, intent);
                    finish();
                    Toast.makeText(ProductActivity.this, "Produkt " + response.body().getName() + " wurde erstellt.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Es ist ein Fehler aufgetreten.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateProduct(final Product product) {
        iProductService = APIUtils.getProductService();
        Call<Product> call = iProductService.updateProduct(product.getId(), product);
        call.enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Intent previousIntent = getPriviousIntent(response);
                    setResult(2, previousIntent);
                    finish();
                    Toast.makeText(ProductActivity.this, "Produkt " + response.body().getName() + " wurde aktualisiert.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Es ist ein Fehler aufgetreten.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Intent getPriviousIntent(Response<Product> response) {
        Intent previousIntent = new Intent(getApplicationContext(), MainActivity.class);
        previousIntent.putExtra("product_id", response.body().getId());
        previousIntent.putExtra("product_name", response.body().getName());
        previousIntent.putExtra("product_description", response.body().getDescription());
        previousIntent.putExtra("product_price", Float.toString(response.body().getPrice()));

        return previousIntent;
    }
}
