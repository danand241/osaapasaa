package com.lingme.anand.lingme.Activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lingme.anand.lingme.Activity.Fragments.BasketFragment;
import com.lingme.anand.lingme.Activity.Fragments.DisplayingFragment;
import com.lingme.anand.lingme.Activity.Fragments.FavouriteFragment;
import com.lingme.anand.lingme.Activity.Fragments.HomeFragment;
import com.lingme.anand.lingme.Activity.Fragments.LoginFragment;
import com.lingme.anand.lingme.Activity.Fragments.SignUpFragment;
import com.lingme.anand.lingme.Activity.Fragments.UnderConstructionFragment;
import com.lingme.anand.lingme.Activity.Listeners.Delete;
import com.lingme.anand.lingme.Activity.Listeners.Select;
import com.lingme.anand.lingme.Activity.Pojo.Home;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 20/10/2015.
 */
public class HomeActivity extends AppCompatActivity{
    TextView menu, fav, bas, ac;
    DatabaseHelper db;
    private PopupMenu popupMenu;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    Drawable fav_drawable, bas_drawable;
    DisplayingFragment fragment;
    Bundle bundle, input;
    FragmentTransaction fragmentTransaction;
    String fragmentName;
    public static int bas_badge = 0;
    public static int fav_badge = 0;
    Menu menu_item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        db = new DatabaseHelper(getApplicationContext());
        UserLocalStore userLocalStore = new UserLocalStore(this);
        input = getIntent().getExtras();
        instanciate();
        if (input == null) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment fragment = new HomeFragment();
            fragmentTransaction.replace(R.id.fragments, fragment, HomeFragment.class.getName());
            fragmentTransaction.commit();
        } else {
            fragmentName = input.getString("fragment");

            if (fragmentName.equals("LoginFragment")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                LoginFragment fragment = new LoginFragment();
                fragmentTransaction.replace(R.id.fragments, fragment, LoginFragment.class.getName());
                getSupportActionBar().setTitle("User Login");
                fragmentTransaction.commit();
            }
            else
            {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SignUpFragment fragment = new SignUpFragment();
                fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
                getSupportActionBar().setTitle("Account Details");
                fragmentTransaction.commit();
            }
        }

    }

    public void instanciate() {
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "OsaapasaaAmpersand-Regular.ttf");
        menu = (TextView) findViewById(R.id.menu);
        fav = (TextView) findViewById(R.id.fav);
        bas = (TextView) findViewById(R.id.basket);
        ac = (TextView) findViewById(R.id.ac);
        menu.setTypeface(tf);
        fav.setTypeface(tf);
        bas.setTypeface(tf);
        ac.setTypeface(tf);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fav_drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_favorite_black_24dp);
        bas_drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_shop_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
                    UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
                if(userLocalStore.getUserLogIn() == true){
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SignUpFragment fragment = new SignUpFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Account Details");
                    fragmentTransaction.commit();
                }else {
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    LoginFragment fragment = new LoginFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, LoginFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("User Login");
                    fragmentTransaction.commit();
                }
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
                fav_badge = 0;
                getSupportActionBar().invalidateOptionsMenu();
            }
        });
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
                bas_badge = 0;
                getSupportActionBar().invalidateOptionsMenu();
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
        UserLocalStore userLocalStore = new UserLocalStore(this);
            ActionItemBadge.update(this, menu.findItem(R.id.fav_badge), fav_drawable, ActionItemBadge.BadgeStyles.DARK_GREY, fav_badge);
            ActionItemBadge.update(this, menu.findItem(R.id.bas_badge), bas_drawable, ActionItemBadge.BadgeStyles.DARK_GREY, bas_badge);

            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav_badge:
                fav_badge = 0;
                ActionItemBadge.update(item, fav_badge);
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
                break;
            case R.id.bas_badge:
                bas_badge = 0;
                ActionItemBadge.update(item, bas_badge);
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransactionBas = fragmentManager.beginTransaction();
                BasketFragment fragmentBas = new BasketFragment();
                fragmentTransactionBas.replace(R.id.fragments, fragmentBas, BasketFragment.class.getName());
                fragmentTransactionBas.addToBackStack(getSupportActionBar().getTitle().toString());
                getSupportActionBar().setTitle("Basket");
                fragmentTransactionBas.commit();
                ac.setSelected(false);
                bas.setSelected(true);
                fav.setSelected(false);
                menu.setSelected(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName());
        }
    }
}
