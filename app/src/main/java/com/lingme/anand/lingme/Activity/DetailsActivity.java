package com.lingme.anand.lingme.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.Adapters.DetailsRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.NecklaceRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Fragments.SignUpFragment;
import com.lingme.anand.lingme.Activity.Pojo.FavList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 6/11/2015.
 */
public class DetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String url = "http://wwwgyaampe.com/osaapasaa/details.php";
    private DetailsRecyclerAdapter detailsRecyclerAdapter;
    String id;
    String table;
    private List<ListProduct> listDetails = new ArrayList<ListProduct>();
    private ProgressDialog progressDialog;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private ImageView imageView;
    Toolbar toolbar;
    TextView textView;
    Menu menu;
    DatabaseHelper db;
    int badgecount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            setContentView(R.layout.list_details);
            db = new DatabaseHelper(getApplicationContext());
            toolbar = (Toolbar) findViewById(R.id.toolbar_details);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setLogo(R.drawable.logo);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_list_details);
            Bundle did = getIntent().getExtras();
            id = did.getString("id");
            table = did.getString("table");
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            details();
        }
        else{
            setContentView(R.layout.fragment_connection);
            Bundle did = getIntent().getExtras();
            id = did.getString("id");
            table = did.getString("table");
            imageView=(ImageView)findViewById(R.id.refresh);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(getApplicationContext(),DetailsActivity.class);
                    in.putExtra("id",id);
                    in.putExtra("table",table);
                    startActivity(in);
                }
            });
        }
    }

    public void details() {

        String path = url + "?table=" + table + "&id=" + id;
        Log.e("error",path);
        RequestQueue queue = Volley.newRequestQueue(this);
        showPd();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                hidePD();
                try {
                    JSONArray home = response.getJSONArray("home");
                    //  length = home.length();

                    for (int i = 0; i < home.length(); i++) {

                        JSONObject post = home.getJSONObject(i);

                        ListProduct h = new ListProduct();
                        String img1 = post.getString("img1");
                        String img2 = post.getString("img2");
                        String img3 = post.getString("img3");
                        h.setImg1("http://wwwgyaampe.com/img/" + table + img1);
                        h.setName(post.getString("name"));
                        h.setProductId(post.getString("product_id"));
                        h.setPrice(Integer.parseInt(post.getString("price")));
                        h.setImg2("http://wwwgyaampe.com/img/" + table + img2);
                        h.setImg3("http://wwwgyaampe.com/img/" + table + img3);
                        h.setBrand(post.getString("brand"));
                        h.setDescription(post.getString("description"));
                        h.setM(post.getString("m"));
                        h.setL(post.getString("l"));
                        h.setS(post.getString("s"));
                        h.setXl(post.getString("xl"));
                        h.setXxl(post.getString("xxl"));
                        h.setStock(post.getString("stock"));
                        h.setTable_name(table);
                        listDetails.add(h);
                    }
                    detailsRecyclerAdapter = new DetailsRecyclerAdapter(getApplicationContext(), listDetails, menu);
                    recyclerView.setAdapter(detailsRecyclerAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                 detailsRecyclerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePD();
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void showPd() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public void hidePD() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav_menu:
                ListProduct favList = listDetails.get(0);
                Boolean isInserted = db.insert(favList.getProductId(), favList.getBrand(), favList.getName(), favList.getPrice(), favList.getDescription(), favList.getStock(), favList.getTable_name(), favList.getImg1());
                if (isInserted == true)
                    Toast.makeText(getApplicationContext(), "Added to favourite", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Not Inserted",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bas_menu:
                ListProduct basList = listDetails.get(0);
                Boolean isInsertedBas = db.insertBas(basList.getProductId(), basList.getBrand(), basList.getName(), basList.getPrice(), basList.getDescription(), basList.getStock(), basList.getTable_name(), basList.getImg1());
                if (isInsertedBas == true)
                    Toast.makeText(getApplicationContext(), "Added to Basket", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Not Inserted",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buy_menu:
                Intent in = new Intent(this, HomeActivity.class);
                in.putExtra("fragment","LoginFragment");
                startActivity(in);
        }
        return false;
    }
}
