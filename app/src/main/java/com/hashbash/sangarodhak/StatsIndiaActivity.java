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
import com.hashbash.sangarodhak.Adapters.StateDataRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatsIndiaActivity extends AppCompatActivity {

    ArrayList<String> statesList = new ArrayList<>();
    ArrayList<String> confirmedCasesList = new ArrayList<>();
    ArrayList<String> recoveredCasesList = new ArrayList<>();
    ArrayList<String> deathsList = new ArrayList<>();
    ArrayList<String> activeCasesList = new ArrayList<>();

    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_india);

        progressBar = findViewById(R.id.loading);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
    }

    private void fetchData() {
        String url = getString(R.string.url_india_all_states);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray statesData = response.getJSONObject("data").getJSONArray("regional");
                    int len = statesData.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject state = statesData.getJSONObject(i);
                        statesList.add(state.getString("loc"));
                        confirmedCasesList.add("" + state.getInt("totalConfirmed"));
                        recoveredCasesList.add("" + state.getInt("discharged"));
                        deathsList.add("" + state.getInt("deaths"));
                        activeCasesList.add("" + (state.getInt("totalConfirmed") - state.getInt("discharged") - state.getInt("deaths")));
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
        recyclerView.setAdapter(new StateDataRecyclerAdapter(this, statesList, confirmedCasesList, activeCasesList, recoveredCasesList, deathsList));
    }

}
