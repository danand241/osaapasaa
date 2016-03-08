package com.lingme.anand.lingme.Activity.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lingme.anand.lingme.R;

/**
 * Created by nepal on 3/11/2015.
 */
public class LoginFragment extends Fragment {
    EditText login_user_input,login_password_input;
    Button login,register;
    FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login,container,false);
        login_user_input=(EditText) view.findViewById(R.id.login_username_input);
        login_user_input.setHint("Username");
        login_password_input=(EditText) view.findViewById(R.id.login_password_input);
        login_password_input.setHint("Password");
        login=(Button) view.findViewById(R.id.login);
        register=(Button) view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SignUpFragment fragment = new SignUpFragment();
                fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
                fragmentTransaction.addToBackStack(((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString());
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("User Registration");
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
