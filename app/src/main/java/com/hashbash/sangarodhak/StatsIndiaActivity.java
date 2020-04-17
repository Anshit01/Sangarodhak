package com.hashbash.sangarodhak;

import android.content.SharedPreferences;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hashbash.sangarodhak.Adapters.CountryDataRecyclerAdapter;
import com.hashbash.sangarodhak.Modals.CountryCaseDataModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StatsIndiaActivity extends AppCompatActivity {

    ArrayList<CountryCaseDataModal> allStates = new ArrayList<>();

    RecyclerView recyclerView;
    ProgressBar progressBar;

    Gson gson = new Gson();
    SharedPreferences statsPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_india);

        progressBar = findViewById(R.id.loading);
        recyclerView = findViewById(R.id.recycler_view);

        statsPreference = getSharedPreferences(getString(R.string.pref_stats_data), MODE_PRIVATE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        retrieveData();

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
                    allStates = new ArrayList<>();
                    for (int i = 0; i < len; i++) {
                        JSONObject state = statesData.getJSONObject(i);
                        allStates.add(new CountryCaseDataModal(state.getString("loc"),
                                "" + state.getInt("totalConfirmed"),
                                "" + (state.getInt("totalConfirmed") - state.getInt("discharged") - state.getInt("deaths")),
                                "" + state.getInt("discharged"),
                                "" + state.getInt("deaths")));
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
        recyclerView.setAdapter(new CountryDataRecyclerAdapter(this, allStates));
        saveAllData();
    }

    private void saveAllData(){
        String allData = gson.toJson(allStates);

        statsPreference.edit().putString(getString(R.string.pref_stats_country_data), allData).apply();
    }

    private void retrieveData(){

        String allData = statsPreference.getString(getString(R.string.pref_stats_country_data), "[]");

        if(!allData.equals("[]")){
            Type type = new TypeToken< ArrayList < CountryCaseDataModal >>() {}.getType();

            allStates = gson.fromJson(allData, type);

            showStats();
        }

    }

}
