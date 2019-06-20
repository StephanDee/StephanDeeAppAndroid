package de.stephandee.stephandeeappandroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.stephandee.stephandeeappandroid.dbaccess.APIUtils;
import de.stephandee.stephandeeappandroid.dbaccess.IProductService;
import de.stephandee.stephandeeappandroid.models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private List<Product> mProduct;

    public RecyclerViewAdapter(Context mContext, List<Product> mProduct) {
        this.mProduct = mProduct;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_product_listitem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        // Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.productName.setText(mProduct.get(i).getName());
        viewHolder.productDescription.setText(mProduct.get(i).getDescription());
        viewHolder.productPrice.setText(Float.toString(mProduct.get(i).getPrice()));

        viewHolder.buttonEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("product_id", mProduct.get(i).getId());
                mContext.startActivity(intent);
            }
        });

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(mProduct.get(i).getId());
            }
        });

        viewHolder.productLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Log.d(TAG, "onClick: clicked on:" + mProduct.get(i).getName());

                Toast.makeText(mContext, mProduct.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productDescription;
        TextView productPrice;
        ImageButton buttonEdit;
        ImageButton buttonDelete;
        LinearLayout productLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
            productLayout = itemView.findViewById(R.id.productLayout);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public void deleteProduct(final String id) {
        IProductService iProductService = APIUtils.getProductService();
        Call<Product> call = iProductService.deleteProduct(id);
        call.enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Produkt wurde gelöscht.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(mContext, "failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
