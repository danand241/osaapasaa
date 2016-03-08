package com.lingme.anand.lingme.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lingme.anand.lingme.Activity.Fragments.BasketFragment;
import com.lingme.anand.lingme.Activity.Fragments.DisplayingFragment;
import com.lingme.anand.lingme.Activity.Fragments.FavouriteFragment;
import com.lingme.anand.lingme.Activity.Fragments.HomeFragment;
import com.lingme.anand.lingme.Activity.Fragments.LoginFragment;
import com.lingme.anand.lingme.Activity.Fragments.SignUpFragment;
import com.lingme.anand.lingme.Activity.Fragments.UnderConstructionFragment;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 20/10/2015.
 */
public class HomeActivity extends AppCompatActivity {
    TextView menu, fav, bas, ac;
    DatabaseHelper db;
    private PopupMenu popupMenu;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    DisplayingFragment fragment;
    Bundle bundle;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        db=new DatabaseHelper(getApplicationContext());
        instanciate();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
        fragmentTransaction.commit();
    }

    public void instanciate() {
        menu = (TextView)findViewById(R.id.menu);
        fav = (TextView)findViewById(R.id.fav);
        bas = (TextView)findViewById(R.id.basket);
        ac = (TextView)findViewById(R.id.ac);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Osapasaa");
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(HomeActivity.this, v);
                popupMenu.setOnDismissListener(new OnDismissListener());
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
                bas.setSelected(false);
                fav.setSelected(false);
                ac.setSelected(false);
                menu.setSelected(true);

            }
        });
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LoginFragment fragment = new LoginFragment();
                fragmentTransaction.replace(R.id.fragments, fragment, LoginFragment.class.getName());
                fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                getSupportActionBar().setTitle("Account");
                fragmentTransaction.commit();
                ac.setSelected(true);
                bas.setSelected(false);
                fav.setSelected(false);
                menu.setSelected(false);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FavouriteFragment fragment = new FavouriteFragment();
        fragmentTransaction.replace(R.id.fragments, fragment, FavouriteFragment.class.getName());
        fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
        getSupportActionBar().setTitle("Favourites");
        fragmentTransaction.commit();
        ac.setSelected(false);
        bas.setSelected(false);
        fav.setSelected(true);
        menu.setSelected(false);
    }});
        bas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BasketFragment fragment = new BasketFragment();
                fragmentTransaction.replace(R.id.fragments, fragment, BasketFragment.class.getName());
                fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                getSupportActionBar().setTitle("Basket");
                fragmentTransaction.commit();
                bas.setSelected(true);
                fav.setSelected(false);
                ac.setSelected(false);
                menu.setSelected(false);
            }
        });
    }

    private class OnDismissListener implements PopupMenu.OnDismissListener {

        @Override
        public void onDismiss(PopupMenu menu) {
            // TODO Auto-generated method stub
        }

    }

    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            // TODO Auto-generated method stub
            switch (item.getItemId()) {
                case R.id.sales:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "sales");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Sales");
                    fragmentTransaction.commit();
                    break;
                case R.id.new_in:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "new_in");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("New In");
                    fragmentTransaction.commit();
                    break;
                case R.id.cnj:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "coats_jackets");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Coats and Jackets");
                    fragmentTransaction.commit();
                    break;
                case R.id.hns:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "hoodies_sweatshirt");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Hoodies and Sweatshirts");
                    fragmentTransaction.commit();
                    break;
                case R.id.jeans:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "jeans");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Jeans");
                    fragmentTransaction.commit();
                    break;
                case R.id.jumpers:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "jumpers");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Jumpers");
                    fragmentTransaction.commit();
                    break;
                case R.id.shirts:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "shirts");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Shirts");
                    fragmentTransaction.commit();
                    break;
                case R.id.tnp:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "tshirts_polos");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("T-shirt and Polos");
                    fragmentTransaction.commit();
                    break;
                case R.id.tnc:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "trousers_chinos");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Trousers and Chinos");
                    fragmentTransaction.commit();
                    break;
                case R.id.shoes:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "shoes");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Shoes");
                    fragmentTransaction.commit();
                    break;
                case R.id.uns:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "underwear_shocks");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Underwear and Shoes");
                    fragmentTransaction.commit();
                    break;
                case R.id.access:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "access");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Accessories");
                    fragmentTransaction.commit();
                    break;
                case R.id.accesw:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "accessw");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Accessories");
                    fragmentTransaction.commit();
                    break;
                case R.id.bnp:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "bags_purses");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Bags and Purses");
                    fragmentTransaction.commit();
                    break;
                case R.id.cnjw:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "coats_jacketsw");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Coats and Jackets");
                    fragmentTransaction.commit();
                    break;
                case R.id.dresses:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "dresses");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Dresses");
                    fragmentTransaction.commit();
                    break;
                case R.id.hnsw:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "hoodies_sweatshirtsw");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Hoodies and Sweatshirts");
                    fragmentTransaction.commit();
                    break;
                case R.id.jeansw:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "jeansw");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Jeans");
                    fragmentTransaction.commit();
                    break;
                case R.id.lingerie:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "lingerie");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Lingerie");
                    fragmentTransaction.commit();
                    break;
                case R.id.tnt:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "top_tshirts");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Top and T-shirts");
                    fragmentTransaction.commit();
                    break;
                case R.id.shorts:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "shorts");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Shorts");
                    fragmentTransaction.commit();
                    break;
                case R.id.tnl:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "trousers_leggings");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Trousers and Leggings");
                    fragmentTransaction.commit();
                    break;
                case R.id.skirt:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "skirts");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Skirts");
                    fragmentTransaction.commit();
                    break;
                case R.id.shoesw:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "shoes");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Shoes");
                    fragmentTransaction.commit();
                    break;
                case R.id.workwear:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    bundle = new Bundle();
                    bundle.putString("dbname", "workwear");
                    fragment = new DisplayingFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Workwear");
                    fragmentTransaction.commit();
                    break;
            }
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.fav_menu).getActionView();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle( fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName());
        }
    }
}
