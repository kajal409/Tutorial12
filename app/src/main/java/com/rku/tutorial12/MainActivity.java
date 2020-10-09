package com.rku.tutorial12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcvUsers;
    RequestQueue requestQueue;
    JsonArrayRequest jsonArrayRequest;
    LinearLayoutManager layoutManager;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvUsers = findViewById(R.id.rcvUsers);
        rcvUsers.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(rcvUsers.getContext(), LinearLayoutManager.VERTICAL);
        rcvUsers.addItemDecoration(itemDecoration);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        rcvUsers.setLayoutAnimation(animation);

        volleyNetworkCallAPI();
    }

    private void volleyNetworkCallAPI() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                MyUtil.URL_USERS,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        MyUtil.userdata = response;
                        userAdapter = new UserAdapter(response);
                        rcvUsers.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                );
        requestQueue.add(jsonArrayRequest);
    }
}