package org.vidyarthimitra.vidyarthimitra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getne_sd_image())
                .into(holder.imageView);
        holder.textViewne_title.setText(product.getne_title());
        holder.textViewne_desc.setText(String.valueOf(product.getne_desc()));


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewne_title, textViewne_desc;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewne_title = itemView.findViewById(R.id.textViewTitle);
            // textViewne_publish_date = itemView.findViewById(R.id.publish_date);
            textViewne_desc = itemView.findViewById(R.id.Short_desc);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
