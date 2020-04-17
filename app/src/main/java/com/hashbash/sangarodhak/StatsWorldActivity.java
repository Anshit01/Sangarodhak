package com.hashbash.sangarodhak;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.hashbash.sangarodhak.Adapters.GlobalDataRecyclerAdapter;
import com.hashbash.sangarodhak.Modals.GlobalCaseDataModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StatsWorldActivity extends AppCompatActivity {

    ArrayList<GlobalCaseDataModal> allCountryData = new ArrayList<>();

    RecyclerView recyclerView;

    ProgressBar progressBar;

    private TextView confirmedTextView;
    private TextView recoveredTextView;
    private TextView deathsTextView;

    private int confirmed;
    private int recovered;
    private int deaths;

    Gson gson = new Gson();
    SharedPreferences statsPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_world);

        progressBar = findViewById(R.id.loading);
        recyclerView = findViewById(R.id.recycler_view);

        confirmedTextView = findViewById(R.id.global_total_confirmed);
        recoveredTextView = findViewById(R.id.global_total_recovered);
        deathsTextView = findViewById(R.id.global_total_deaths);

        statsPreference = getSharedPreferences(getString(R.string.pref_stats_data), MODE_PRIVATE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        retrieveData();

        fetchData();
    }

    private void fetchData() {
        String url = getString(R.string.url_world_stats);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray countriesData = response.getJSONArray("Countries");
                    int len = countriesData.length();
                    allCountryData = new ArrayList<>();
                    for (int i = 0; i < len; i++) {
                        JSONObject country = countriesData.getJSONObject(i);
                        allCountryData.add(new GlobalCaseDataModal(country.getString("CountryCode"), country.getString("Country"),
                                "" + country.getInt("TotalConfirmed"),
                                "" + (country.getInt("TotalConfirmed") - country.getInt("TotalRecovered") - country.getInt("TotalDeaths")),
                                "" + country.getInt("TotalRecovered"),
                                "" + country.getInt("TotalDeaths")));
                    }
                    JSONObject globalData = response.getJSONObject("Global");
                    confirmed = globalData.getInt("TotalConfirmed");
                    recovered = globalData.getInt("TotalRecovered");
                    deaths = globalData.getInt("TotalDeaths");
                    showStats();
                } catch (JSONException e) {
                    Log.d("World Stats", "" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("World Stats", "" + error.getMessage());
            }
        });
        queue.add(request);
    }

    private void showStats() {
        progressBar.setVisibility(View.GONE);
        confirmedTextView.setText("" + confirmed);
        recoveredTextView.setText("" + recovered);
        deathsTextView.setText("" + deaths);
        recyclerView.setAdapter(new GlobalDataRecyclerAdapter(this, allCountryData));
        saveAllData();
    }

    private void saveAllData() {
        String allData = gson.toJson(allCountryData);

        statsPreference.edit().putString(getString(R.string.pref_stats_global_data), allData)
                .putInt(getString(R.string.pref_case_data_global_confirmed), confirmed)
                .putInt(getString(R.string.pref_case_data_global_recovered), recovered)
                .putInt(getString(R.string.pref_case_data_global_deaths), deaths)
                .apply();
    }

    private void retrieveData() {

        String allData = statsPreference.getString(getString(R.string.pref_stats_global_data), "[]");

        if (!allData.equals("[]")) {
            Type type = new TypeToken<ArrayList<GlobalCaseDataModal>>() {
            }.getType();

            allCountryData = gson.fromJson(allData, type);

            confirmed = statsPreference.getInt(getString(R.string.pref_case_data_global_confirmed), 0);
            recovered = statsPreference.getInt(getString(R.string.pref_case_data_global_recovered), 0);
            deaths = statsPreference.getInt(getString(R.string.pref_case_data_global_deaths), 0);

            showStats();
        }

    }
}
