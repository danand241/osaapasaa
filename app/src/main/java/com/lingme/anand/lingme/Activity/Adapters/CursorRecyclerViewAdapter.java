package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.R;

/**
 * Created by skyfishjy on 10/31/14.
 */

public class CursorRecyclerViewAdapter extends RecyclerView.Adapter<CursorRecyclerViewAdapter.ViewHolder> {
    protected NetworkImageView networkImageView;
    protected TextView name_product, fav_price, brand, stock, description_detail, product_code;
    protected ImageButton buy;
    private ImageLoader mImageLoader;
    // PATCH: Because RecyclerView.Adapter in its current form doesn't natively support
    // cursors, we "wrap" a CursorAdapter that will do all teh job
    // for us
    CursorAdapter mCursorAdapter;

    Context mContext;
    Cursor cursor;
    public CursorRecyclerViewAdapter(Context context, Cursor c) {
        this.cursor =c;
        mContext = context;

        mCursorAdapter = new CursorAdapter(mContext, c, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_recyc, null);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                cursor = cursor;
                mImageLoader = MySingleton.getInstance(context).getImageLoader();
                networkImageView = (NetworkImageView) view.findViewById(R.id.fav_image);
                name_product = (TextView) view.findViewById(R.id.fav_name);
                fav_price = (TextView) view.findViewById(R.id.price_fav);
                brand = (TextView) view.findViewById(R.id.fav_brand);
                stock = (TextView) view.findViewById(R.id.fav_stock);
                description_detail = (TextView) view.findViewById(R.id.fav_description);
                buy = (ImageButton) view.findViewById(R.id.fav_list);
                networkImageView.setImageUrl(cursor.getString(7), mImageLoader);
                networkImageView.setDefaultImageResId(R.drawable.logo);
                brand.setText(cursor.getString(1));
                name_product.setText(cursor.getString(2));
                fav_price.setText(Integer.parseInt(cursor.getString(3)));
                description_detail.setText(cursor.getString(4));
                stock.setText(cursor.getString(5));
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Passing the binding operation to cursor loader
        mCursorAdapter.getCursor().moveToPosition(position); //EDITED: added this line as suggested in the comments below, thanks :)
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        return new ViewHolder(v);
    }
}