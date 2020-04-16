package com.hashbash.sangarodhak;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hashbash.sangarodhak.Adapters.DistrictDataRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatsStateActivity extends AppCompatActivity {


    private String state;
    private ArrayList<String> districtsList = new ArrayList<>();
    private ArrayList<String> confirmedList = new ArrayList<>();

    RecyclerView recyclerView;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_state);

        progressBar = findViewById(R.id.loading);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("log", "asdfd");
        if (savedInstanceState != null) {
            state = savedInstanceState.getString("state");
        } else {
            state = this.getSharedPreferences(getString(R.string.pref_case_data), MODE_PRIVATE).getString(getString(R.string.pref_case_data_state_name), "Himachal Pradesh");
        }
        fetchData();
    }

    private void fetchData() {
        String url = getString(R.string.url_all_districts);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("log", response.toString());
                try {
                    JSONObject districtsData = response.getJSONObject(state).getJSONObject("districtData");
                    JSONArray districts = districtsData.names();
                    assert districts != null;
                    int len = districts.length();
                    for (int i = 0; i < len; i++) {
                        districtsList.add(districts.getString(i));
                        confirmedList.add("" + districtsData.getJSONObject(districts.getString(i)).getInt("confirmed"));
                    }
                    showStats();
                } catch (JSONException e) {
                    Log.d("log", "" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log", "" + error.getMessage());
            }
        });
        queue.add(request);
    }

    private void showStats() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(new DistrictDataRecyclerAdapter(this, districtsList, confirmedList));
        for (int i = 0; i < districtsList.size(); i++) {
            Log.d("log", districtsList.get(i) + " " + confirmedList.get(i));
        }
    }
}
