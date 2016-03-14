package com.lingme.anand.lingme.Activity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.HomeActivity;
import com.lingme.anand.lingme.Activity.Pojo.User;
import com.lingme.anand.lingme.Activity.UserLocalStore;
import com.lingme.anand.lingme.R;

import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 29/10/2015.
 */
public class SignUpFragment extends Fragment {
    EditText username_input,password_input,name,address,phoneNumber,email;
    FancyButton submit, logout;
    String inserUrl= "http://192.168.0.100/lingme/insert.php";
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        username_input=(EditText)view.findViewById(R.id.username_input);
        password_input=(EditText)view.findViewById(R.id.password_input);
        phoneNumber=(EditText)view.findViewById(R.id.contact_input);
        name=(EditText)view.findViewById(R.id.name_input);
        address=(EditText)view.findViewById(R.id.address_input);
        email=(EditText)view.findViewById(R.id.email_input);
        username_input.setHint("Username");
        password_input.setHint("Password");
        phoneNumber.setHint("Phone Number");
        name.setHint("Name");
        address.setHint("Address");
        email.setHint("Email");
        submit=(FancyButton)view.findViewById(R.id.submit);
        logout=(FancyButton)view.findViewById(R.id.logout);
        username_input.setFocusableInTouchMode(true);
        password_input.setFocusableInTouchMode(true);
        phoneNumber.setFocusableInTouchMode(true);
        name.setFocusableInTouchMode(true);
        address.setFocusableInTouchMode(true);
        email.setFocusableInTouchMode(true);
        check();
        requestQueue= Volley.newRequestQueue(getActivity());
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
              sendToServer();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });
        return view;
    }

    private void sendToServer()
    {
        StringRequest request = new StringRequest(Request.Method.POST, inserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", username_input.getText().toString());
                parameters.put("password", password_input.getText().toString());
                parameters.put("name",name.getText().toString());
                parameters.put("address",address.getText().toString());
                parameters.put("phoneNumber",phoneNumber.getText().toString());
                parameters.put("email",email.getText().toString());
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void check()
    {
        UserLocalStore userLocalStore = new UserLocalStore(getActivity());
        User loggedUser = new User();
        if(userLocalStore.getUserLogIn() == true) {
            loggedUser = userLocalStore.getLoggedUser();
            username_input.setText(loggedUser.getUsername());
            password_input.setText(loggedUser.getPassword());
            address.setText(loggedUser.getAddress());
            email.setText(loggedUser.getAddress());
            phoneNumber.setText(loggedUser.getPhoneNumber()+"");
            name.setText(loggedUser.getName());
            logout.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }else {
            logout.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        }
    }

    private void userLogout()
    {
        UserLocalStore userLocalStore = new UserLocalStore(getActivity());
        userLocalStore.clearUserData();
        userLocalStore.setUserLogIn(false);
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }
}
