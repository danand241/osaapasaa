package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingme.anand.lingme.Activity.Adapters.BasketRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.FavouriteRecyclerAdapter;
import com.lingme.anand.lingme.Activity.DatabaseHelper;
import com.lingme.anand.lingme.Activity.HomeActivity;
import com.lingme.anand.lingme.Activity.Listeners.Delete;
import com.lingme.anand.lingme.Activity.Listeners.EndlessRecyclerOnScrollListener;
import com.lingme.anand.lingme.Activity.Pojo.BasList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 23/02/2016.
 */
public class BasketFragment extends Fragment implements Delete{

    private RecyclerView recyclerView;
    private BasketRecyclerAdapter basketRecyclerAdapter;
    DatabaseHelper db,getDb,deldata;
    TextView buy_msg,basket_empty;
    FancyButton btn_buy;
    ArrayList<BasList> lists = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.basket, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.basket_list_product);
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);
        db = new DatabaseHelper(getActivity());
        buy_msg = (TextView)view.findViewById(R.id.buy_msg);
        btn_buy = (FancyButton)view.findViewById(R.id.btn_buy);
        basket_empty = (TextView)view.findViewById(R.id.basket_msg);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "OsaapasaaText-Regular.ttf");
        basket_empty.setTypeface(tf);
        buy_msg.setTypeface(tf);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager1) {
            @Override
            public void onLoadMore(int current_page) {
                int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                loadMore(current_page);

            }
        });
        updateList();
        total();
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), HomeActivity.class);
                in.putExtra("fragment","LoginFragment");
                startActivity(in);
            }
        });
        return view;

    }

    public void updateList() {
        basketRecyclerAdapter = new BasketRecyclerAdapter(getActivity(),  db.getProductsBas(1),this);
        recyclerView.setAdapter(basketRecyclerAdapter);
    }


    public void loadMore(int page) {
        basketRecyclerAdapter = new BasketRecyclerAdapter(getActivity(), db.getProductsBas(page),this);
        recyclerView.setAdapter(basketRecyclerAdapter);
    }
    public void total() {
        getDb = new DatabaseHelper(getActivity());
        lists = getDb.getAllProducts();
        if(lists.isEmpty()) {
            buy_msg.setVisibility(View.GONE);
            btn_buy.setVisibility(View.GONE);
            basket_empty.setVisibility(View.VISIBLE);
        }
        else {
            int sum = 0;
            for (int i = 0; i<lists.size();i++)
            {
                sum = sum + lists.get(i).getPrice();
            }
            buy_msg.setText("Total: Rs."+sum );
            buy_msg.setVisibility(View.VISIBLE);
            btn_buy.setVisibility(View.VISIBLE);
            basket_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void delete() {
        deldata = new DatabaseHelper(getActivity());
        lists = deldata.getAllProducts();
        if(lists.isEmpty()) {
            buy_msg.setVisibility(View.GONE);
            btn_buy.setVisibility(View.GONE);
            basket_empty.setVisibility(View.VISIBLE);
        }
        else {
            int sum = 0;
            for (int i = 0; i<lists.size();i++)
            {
                sum = sum + lists.get(i).getPrice();
            }
            buy_msg.setText("Total: Rs."+sum );
            buy_msg.setVisibility(View.VISIBLE);
            btn_buy.setVisibility(View.VISIBLE);
            basket_empty.setVisibility(View.GONE);
        }
    }
}
