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
import com.hashbash.sangarodhak.Adapters.StateDataRecyclerAdapter;
import com.hashbash.sangarodhak.Modals.GlobalCaseDataModal;
import com.hashbash.sangarodhak.Modals.StateCaseDataModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StatsStateActivity extends AppCompatActivity {

    private String state;

    private ArrayList<StateCaseDataModal> allDistricts = new ArrayList<>();

    RecyclerView recyclerView;
    ProgressBar progressBar;

    private TextView confirmedTextView;
    private TextView recoveredTextView;
    private TextView deathsTextView;

    private String confirmed;
    private String recovered;
    private String deaths;

    Gson gson = new Gson();
    SharedPreferences statsPreference, caseDataPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_state);

        progressBar = findViewById(R.id.loading);
        recyclerView = findViewById(R.id.recycler_view);

        confirmedTextView = findViewById(R.id.state_total_confirmed);
        recoveredTextView = findViewById(R.id.state_total_recovered);
        deathsTextView = findViewById(R.id.state_total_deaths);

        statsPreference = getSharedPreferences(getString(R.string.pref_stats_data), MODE_PRIVATE);
        caseDataPreference = getSharedPreferences(getString(R.string.pref_case_data), MODE_PRIVATE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (savedInstanceState != null) {
            state = savedInstanceState.getString("state");
        } else {
            state = this.getSharedPreferences(getString(R.string.pref_case_data), MODE_PRIVATE).getString(getString(R.string.pref_case_data_state_name), "Himachal Pradesh");
        }

        retrieveData();

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
                    allDistricts = new ArrayList<>();
                    int len = districts.length();
                    for (int i = 0; i < len; i++) {
                        allDistricts.add(new StateCaseDataModal(districts.getString(i), "" + districtsData.getJSONObject(districts.getString(i)).getInt("confirmed")));
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
        confirmedTextView.setText(confirmed);
        recoveredTextView.setText(recovered);
        deathsTextView.setText(deaths);
        recyclerView.setAdapter(new StateDataRecyclerAdapter(this, allDistricts));
        saveAllData();
    }

    private void saveAllData() {
        String allData = gson.toJson(allDistricts);

        statsPreference.edit().putString(getString(R.string.pref_stats_state_data), allData).apply();
    }

    private void retrieveData() {

        String allData = statsPreference.getString(getString(R.string.pref_stats_state_data), "[]");

        confirmed = caseDataPreference.getString(getString(R.string.pref_case_data_state_total_cases), "0");
        recovered = caseDataPreference.getString(getString(R.string.pref_case_data_state_recovered), "0");
        deaths = caseDataPreference.getString(getString(R.string.pref_case_data_state_dead), "0");

        if (!allData.equals("[]")) {
            Type type = new TypeToken<ArrayList<StateCaseDataModal>>() {
            }.getType();

            allDistricts = gson.fromJson(allData, type);

            showStats();
        }

    }

}
