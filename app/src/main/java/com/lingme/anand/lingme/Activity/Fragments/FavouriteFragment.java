package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.lingme.anand.lingme.Activity.Listeners.EndlessRecyclerOnScrollListener;
import com.lingme.anand.lingme.Activity.Pojo.FavList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 22/02/2016.
 */
public class FavouriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavouriteRecyclerAdapter favouriteRecyclerAdapter;
    DatabaseHelper db ;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.favourite_list_product);
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);
        db = new DatabaseHelper(getActivity());

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager1) {
            @Override
            public void onLoadMore(int current_page) {
                int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                loadMore(current_page);

            }
        });
        updateList();
        return view;

    }

    public void updateList() {
        favouriteRecyclerAdapter = new FavouriteRecyclerAdapter(getActivity(), db.getProducts(1));
        recyclerView.setAdapter(favouriteRecyclerAdapter);
    }

    public void loadMore(int page) {
        favouriteRecyclerAdapter = new FavouriteRecyclerAdapter(getActivity(), db.getProducts(page));
        recyclerView.setAdapter(favouriteRecyclerAdapter);
    }
}
