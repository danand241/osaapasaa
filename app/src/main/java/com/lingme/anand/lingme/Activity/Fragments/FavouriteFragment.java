package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.Adapters.CursorRecyclerViewAdapter;
import com.lingme.anand.lingme.Activity.Adapters.FavouriteRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.NecklaceRecyclerAdapter;
import com.lingme.anand.lingme.Activity.DatabaseHelper;
import com.lingme.anand.lingme.Activity.DetailsActivity;
import com.lingme.anand.lingme.Activity.HomeActivity;
import com.lingme.anand.lingme.Activity.Listeners.Delete;
import com.lingme.anand.lingme.Activity.Listeners.EndlessRecyclerOnScrollListener;
import com.lingme.anand.lingme.Activity.Pojo.FavList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 22/02/2016.
 */
public class FavouriteFragment extends Fragment implements Delete{
    private RecyclerView recyclerView;
    private FavouriteRecyclerAdapter favouriteRecyclerAdapter;
    DatabaseHelper db , getDb, deldata;
    Drawable bas_drawable;
    int badgecount = 0;
    TextView favorite_msg;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite, container, false);
        bas_drawable = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_shop_black_24dp);
        recyclerView = (RecyclerView) view.findViewById(R.id.favourite_list_product);
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);
        db = new DatabaseHelper(getActivity());
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "OsaapasaaText-Regular.ttf");
        favorite_msg = (TextView) view.findViewById(R.id.favourite_msg);
        favorite_msg.setTypeface(tf);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager1) {
            @Override
            public void onLoadMore(int current_page) {
                int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                loadMore(current_page);

            }
        });
        updateList();
        display();
        return view;

    }

    public void updateList() {
        favouriteRecyclerAdapter = new FavouriteRecyclerAdapter(getActivity(), db.getProducts(1), this);
        recyclerView.setAdapter(favouriteRecyclerAdapter);
    }

    public void loadMore(int page) {
        favouriteRecyclerAdapter = new FavouriteRecyclerAdapter(getActivity(), db.getProducts(page), this);
        recyclerView.setAdapter(favouriteRecyclerAdapter);
    }

    public void display() {
        getDb = new DatabaseHelper(getActivity());
        if(getDb.getAllProductsFav().isEmpty()) {
            favorite_msg.setVisibility(View.VISIBLE);
        }
        else {
            favorite_msg.setVisibility(View.GONE);
        }
    }
    @Override
    public void delete() {
        deldata = new DatabaseHelper(getActivity());
        if(deldata.getAllProductsFav().isEmpty()) {
            favorite_msg.setVisibility(View.VISIBLE);
        }
        else {
            favorite_msg.setVisibility(View.GONE);
        }
    }
}
