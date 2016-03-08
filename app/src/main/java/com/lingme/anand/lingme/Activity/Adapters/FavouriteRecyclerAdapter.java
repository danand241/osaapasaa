package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.DatabaseHelper;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelectedListener;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.FavList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 22/02/2016.
 */
public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.HolderView> {
    private ArrayList<FavList> list;
    private ImageLoader mImageLoader;
    private Context context;

    public FavouriteRecyclerAdapter(Context context, ArrayList<FavList> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_recyc, null);
        HolderView holderView = new HolderView(view);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        final FavList details = list.get(position);
        Log.i("asdf", list.get(position).toString() + "");
        holder.getLayoutPosition();
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.networkImageView.setImageUrl(details.getImg1(), mImageLoader);
        holder.networkImageView.setDefaultImageResId(R.drawable.logo);
        holder.name_product.setText("Name: " + details.getName());
        holder.brand.setText("Brand: " + details.getBrand());
        holder.stock.setText("Stock: " + details.getStock());
        holder.fav_price.setText("Rs. " + details.getPrice() + "");
        holder.description_detail.setText(details.getDescription());
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "OsaapasaaText-Regular.ttf");
        holder.name_product.setTypeface(tf);
        Typeface tf1 = Typeface.createFromAsset(context.getAssets(),
                "OsaapasaaAmpersand-Bold.ttf");
        holder.fav_price.setTypeface(tf1);
        holder.brand.setTypeface(tf);
        holder.stock.setTypeface(tf);
        holder.description_detail.setTypeface(tf);
        Log.i("msssg", details.getImg1());
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                Boolean isInserted = db.insertBas(details.getProductId(), details.getBrand(), details.getName(), details.getPrice(), details.getDescription(), details.getStock(), details.getTable_name(), details.getImg1());
                if (isInserted == true) {
                    Toast.makeText(context, "Added to Basket", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clearAdapter() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class HolderView extends RecyclerView.ViewHolder {

        protected NetworkImageView networkImageView;
        protected TextView name_product, fav_price, brand, stock, description_detail, product_code;
        protected ImageButton buy;

        public HolderView(View itemView) {
            super(itemView);
            this.networkImageView = (NetworkImageView) itemView.findViewById(R.id.fav_image);
            this.name_product = (TextView) itemView.findViewById(R.id.fav_name);
            this.fav_price = (TextView) itemView.findViewById(R.id.price_fav);
            this.brand = (TextView) itemView.findViewById(R.id.fav_brand);
            this.stock = (TextView) itemView.findViewById(R.id.fav_stock);
            this.description_detail = (TextView) itemView.findViewById(R.id.fav_description);
            this.buy = (ImageButton) itemView.findViewById(R.id.fav_list);
        }
    }
}
