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

/**
 * The ProductActivity. This screen is used to create or update a product.
 */
public class ProductActivity extends AppCompatActivity {

    // Debug TAG
    private static final String TAG = "ProductActivity";

    // Attributes
    private IProductService iProductService;
    private Product mProduct;
    private EditText editName, editDescription, editPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize view and components
        setContentView(R.layout.activity_product);
        Toolbar toolbar = findViewById(R.id.productToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.product_title);

        editName = findViewById(R.id.editProductName);
        editDescription = findViewById(R.id.editProductDescription);
        editPrice = findViewById(R.id.editProductPrice);
        Button editResetButton = findViewById(R.id.editProductButtonReset);
        Button editSaveButton = findViewById(R.id.editProductButtonSave);

        // check if this activity is used to update a product
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
                    Toast.makeText(ProductActivity.this, ProductActivity.this.getString(R.string.toast_failed), Toast.LENGTH_LONG).show();
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
                // Product validation
                if (!editName.getText().toString().isEmpty() && !editPrice.getText().toString().isEmpty()) {
                    if (hasExtrasProductId) { // product update
                        mProduct.setName(editName.getText().toString());
                        String description = !editDescription.getText().toString().isEmpty() ? editDescription.getText().toString() : "";
                        mProduct.setDescription(description);
                        mProduct.setPrice(Float.parseFloat(editPrice.getText().toString()));
                        updateProduct(mProduct);
                    } else { // add new product
                        Product product = new Product(
                                editName.getText().toString(),
                                Float.parseFloat(editPrice.getText().toString())
                        );
                        if (!editDescription.toString().isEmpty()) {
                            product.setDescription(editDescription.getText().toString());
                        }
                        addProduct(product);
                    }
                } else {
                    Toast.makeText(ProductActivity.this, ProductActivity.this.getString(R.string.toast_product_edit_issue), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Resets the product to initial state.
     *
     * @param product The product
     */
    private void resetProduct(final Product product) {
        if (product != null) { // product exists - update
            editName.setText(product.getName());
            editDescription.setText(product.getDescription());
            editPrice.setText(Float.toString(product.getPrice()));
        } else { // products does not exists - add
            editName.setText("");
            editDescription.setText("");
            editPrice.setText("");
        }
    }

    /**
     * Add product.
     *
     * @param product the product.
     */
    private void addProduct(final Product product) {
        iProductService = APIUtils.getProductService();
        Call<Product> call = iProductService.createProduct(product);
        call.enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Intent intent = getPreviousIntentAndPutExtra(response);
                    setResult(1, intent);
                    finish();
                    Toast.makeText(
                            ProductActivity.this,
                            ProductActivity.this.getString(R.string.toast_product_field_1) +
                                    response.body().getName() +
                                    ProductActivity.this.getString(R.string.toast_product_field_2_created),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductActivity.this, ProductActivity.this.getString(R.string.toast_failed), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Update product.
     *
     * @param product The product.
     */
    private void updateProduct(final Product product) {
        iProductService = APIUtils.getProductService();
        Call<Product> call = iProductService.updateProduct(product.getId(), product);
        call.enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Intent previousIntent = getPreviousIntentAndPutExtra(response);
                    setResult(2, previousIntent);
                    finish();
                    Toast.makeText(
                            ProductActivity.this,
                            ProductActivity.this.getString(R.string.toast_product_field_1) +
                                    response.body().getName() +
                                    ProductActivity.this.getString(R.string.toast_product_field_2_updated),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductActivity.this, ProductActivity.this.getString(R.string.toast_failed), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Get data from the previous Intent and put extras.
     *
     * @param response The response product
     * @return the previous intent.
     */
    private Intent getPreviousIntentAndPutExtra(Response<Product> response) {
        Intent previousIntent = new Intent(getApplicationContext(), MainActivity.class);
        previousIntent.putExtra("product_id", response.body().getId());
        previousIntent.putExtra("product_name", response.body().getName());
        previousIntent.putExtra("product_description", response.body().getDescription());
        previousIntent.putExtra("product_price", Float.toString(response.body().getPrice()));

        return previousIntent;
    }
}
