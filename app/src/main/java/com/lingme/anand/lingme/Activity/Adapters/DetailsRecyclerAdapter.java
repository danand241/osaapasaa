package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.DatabaseHelper;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelectedListener;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 6/11/2015.
 */
public class DetailsRecyclerAdapter extends RecyclerView.Adapter<DetailsRecyclerAdapter.ListHolderView> {
    int x=1,y=2,z=0;
    private List<ListProduct> list;
    private ImageLoader mImageLoader;
    private Context context;
    private String checked;
    public DetailsRecyclerAdapter(Context context, List<ListProduct> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ListHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details, null);
        ListHolderView holderView = new ListHolderView(view);
        return holderView;
    }

    @Override
    public void onBindViewHolder(final ListHolderView holder, int position) {

        final ListProduct details = list.get(position);
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.networkImageView1.setImageUrl(details.getImg1(), mImageLoader);
        holder.networkImageView1.setDefaultImageResId(R.drawable.logo);
        holder.name_product.setText("Name: " + details.getName().toString());
        holder.price.setText("Price: Rs."+details.getPrice() + "");
        holder.brand.setText("Brand: "+details.getBrand());
        holder.stock.setText("Stock: "+details.getStock());
        holder.description_detail.setText(details.getDescription());
        holder.m.setText(details.getM());
        holder.s.setText(details.getS());
        holder.l.setText(details.getL());
        holder.xl.setText(details.getXl());
        holder.xxl.setText(details.getXxl());
        holder.product_code.setText("Product Code: " + details.getProductId());
        holder.networkImageView2.setImageUrl(details.getImg2(), mImageLoader);
        holder.networkImageView2.setDefaultImageResId(R.drawable.logo);
        holder.networkImageView3.setImageUrl(details.getImg3(), mImageLoader);
        holder.networkImageView3.setDefaultImageResId(R.drawable.logo);
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "OsaapasaaAmpersand-Regular.ttf");
        holder.name_product.setTypeface(tf);
        holder.product_code.setTypeface(tf);
        holder.price.setTypeface(tf);
        holder.brand.setTypeface(tf);
        holder.description_detail.setTypeface(tf);
        holder.m.setTypeface(tf);
        holder.s.setTypeface(tf);
        holder.l.setTypeface(tf);
        holder.xxl.setTypeface(tf);
        holder.xl.setTypeface(tf);
        holder.networkImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (x == y) {
                    holder.networkImageView1.setImageUrl(details.getImg1(), mImageLoader);
                    holder.networkImageView2.setImageUrl(details.getImg2(), mImageLoader);
                    y = z;
                } else {
                    holder.networkImageView1.setImageUrl(details.getImg2(), mImageLoader);
                    holder.networkImageView2.setImageUrl(details.getImg1(), mImageLoader);
                    y = x;
                }
            }
        });
        holder.networkImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x == y) {
                    holder.networkImageView1.setImageUrl(details.getImg1(), mImageLoader);
                    holder.networkImageView3.setImageUrl(details.getImg3(), mImageLoader);
                    y = z;
                } else {
                    holder.networkImageView1.setImageUrl(details.getImg3(), mImageLoader);
                    holder.networkImageView3.setImageUrl(details.getImg1(), mImageLoader);
                    y = x;
                }
            }
        });
        if (holder.l.isEnabled()){
          checked = details.getL();
        }
        if (holder.m.isEnabled()){
            checked = details.getM();
        }
        if (holder.s.isEnabled()){
            checked = details.getS();
        }
        if (holder.xl.isEnabled()){
            checked = details.getXl();
        }
        if (holder.xxl.isEnabled()){
            checked = details.getXxl();
        }

        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                Boolean isInserted = db.insert(details.getProductId(), details.getBrand(), details.getName(), details.getPrice(), details.getDescription(), details.getStock(), details.getTable_name(), details.getImg1());
                if (isInserted == true)
                    Toast.makeText(context, "Added to favourite",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Not Inserted",Toast.LENGTH_SHORT).show();
            }
        });
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                Boolean isInserted = db.insertBas(details.getProductId(), details.getBrand(), details.getName(), details.getPrice(), details.getDescription(), details.getStock(), details.getTable_name(), details.getImg1());
                if (isInserted == true)
                    Toast.makeText(context, "Added to Basket",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Not Inserted",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }


    public class ListHolderView extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected NetworkImageView networkImageView1, networkImageView2, networkImageView3;
        protected TextView name_product, price, brand, stock, description_detail,product_code;
        protected CheckBox m,l,s,xl,xxl;
        protected ImageButton buy,viber,favourite;

        public ListHolderView(View itemview) {
            super(itemview);
            this.networkImageView1 = (NetworkImageView) itemview.findViewById(R.id.details_image);
            this.networkImageView2 = (NetworkImageView) itemview.findViewById(R.id.back_image);
            this.networkImageView3 = (NetworkImageView) itemview.findViewById(R.id.middle_image);
            this.name_product = (TextView) itemview.findViewById(R.id.name_product);
            this.price = (TextView) itemview.findViewById(R.id.price);
            this.stock = (TextView) itemview.findViewById(R.id.stock);
            this.brand = (TextView) itemview.findViewById(R.id.brand);
            this.description_detail = (TextView) itemview.findViewById(R.id.description_detail);
            this.m = (CheckBox) itemview.findViewById(R.id.m);
            this.s = (CheckBox) itemview.findViewById(R.id.s);
            this.l = (CheckBox) itemview.findViewById(R.id.l);
            this.xl = (CheckBox) itemview.findViewById(R.id.xl);
            this.xxl = (CheckBox) itemview.findViewById(R.id.xxl);
            this.buy = (ImageButton) itemview.findViewById(R.id.btn_preview);
            this.product_code = (TextView) itemview.findViewById(R.id.product_code);
            this.viber = (ImageButton) itemview.findViewById(R.id.viber);
            this.favourite = (ImageButton) itemview.findViewById(R.id.favourite);
            viber.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.viber:
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                    smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    smsIntent.setPackage("com.viber.voip");
                    smsIntent.setData(Uri.parse("sms:+9779860806513"));
                    smsIntent.putExtra("address", "+9779860806513");
                    smsIntent.putExtra("sms_body", "body  text");
                    v.getContext().startActivity(smsIntent);
                break;
            }
        }
    }

}
