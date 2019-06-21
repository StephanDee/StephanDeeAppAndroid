package de.stephandee.stephandeeappandroid;

import android.app.Activity;
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
    private List<Product> mProducts;

    public RecyclerViewAdapter(Context mContext, List<Product> mProduct) {
        this.mProducts = mProduct;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_product_listitem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        // Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.productName.setText(mProducts.get(i).getName());
        viewHolder.productDescription.setText(mProducts.get(i).getDescription());
        viewHolder.productPrice.setText(Float.toString(mProducts.get(i).getPrice()));

        viewHolder.buttonEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("product_id", mProducts.get(viewHolder.getAdapterPosition()).getId());
                ((Activity) mContext).startActivityForResult(intent, 2);
            }
        });

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(mProducts.get(viewHolder.getAdapterPosition()).getId(), viewHolder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
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

    public void deleteProduct(final String id, final ViewHolder viewHolder) {
        IProductService iProductService = APIUtils.getProductService();
        Call<Product> call = iProductService.deleteProduct(id);
        call.enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    // not in use but hold for documentation reasons
                    // Intent intent = new Intent("sendProductIndex");
                    // intent.putExtra("product_index", Integer.toString(viewHolder.getAdapterPosition()));
                    // LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

                    mProducts.remove(viewHolder.getAdapterPosition());
                    notifyItemRemoved(viewHolder.getAdapterPosition());
                    notifyItemRangeChanged(viewHolder.getAdapterPosition(), mProducts.size());
                    Toast.makeText(mContext, mContext.getString(R.string.toast_product_deleted), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
