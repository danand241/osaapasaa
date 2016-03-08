package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
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

import com.lingme.anand.lingme.Activity.Adapters.BasketRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.FavouriteRecyclerAdapter;
import com.lingme.anand.lingme.Activity.DatabaseHelper;
import com.lingme.anand.lingme.Activity.Listeners.EndlessRecyclerOnScrollListener;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 23/02/2016.
 */
public class BasketFragment extends Fragment {

    private RecyclerView recyclerView;
    private BasketRecyclerAdapter basketRecyclerAdapter;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.basket, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.basket_list_product);
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
        basketRecyclerAdapter = new BasketRecyclerAdapter(getActivity(),  db.getProductsBas(1));
        recyclerView.setAdapter(basketRecyclerAdapter);
    }


    public void loadMore(int page) {
        basketRecyclerAdapter = new BasketRecyclerAdapter(getActivity(), db.getProductsBas(page));
        recyclerView.setAdapter(basketRecyclerAdapter);
    }
}
