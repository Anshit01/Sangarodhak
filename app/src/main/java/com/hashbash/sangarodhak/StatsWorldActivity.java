package com.hashbash.sangarodhak;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
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
import java.util.Comparator;

public class StatsWorldActivity extends AppCompatActivity {

    ArrayList<GlobalCaseDataModal> allCountryData = new ArrayList<>();

    RecyclerView recyclerView;

    ProgressBar progressBar;

    Switch sortSwitch;

    private TextView confirmedTextView;
    private TextView recoveredTextView;
    private TextView deathsTextView;

    private String confirmed, recovered, deaths;

    Gson gson = new Gson();
    SharedPreferences statsPreference, caseDataPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_world);

        progressBar = findViewById(R.id.loading);
        recyclerView = findViewById(R.id.recycler_view);
        sortSwitch = findViewById(R.id.sort_switch);

        confirmedTextView = findViewById(R.id.global_total_confirmed);
        recoveredTextView = findViewById(R.id.global_total_recovered);
        deathsTextView = findViewById(R.id.global_total_deaths);

        statsPreference = getSharedPreferences(getString(R.string.pref_stats_data), MODE_PRIVATE);
        caseDataPreference = getSharedPreferences(getString(R.string.pref_case_data), MODE_PRIVATE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sortSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showStats();
            }
        });

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
        sortArrayList();
        recyclerView.setAdapter(new GlobalDataRecyclerAdapter(this, allCountryData));
        saveAllData();
    }

    private void saveAllData() {
        String allData = gson.toJson(allCountryData);

        statsPreference.edit().putString(getString(R.string.pref_stats_global_data), allData).apply();
    }

    private void retrieveData() {

        confirmed = caseDataPreference.getString(getString(R.string.pref_case_data_global_confirmed), "0");
        recovered = caseDataPreference.getString(getString(R.string.pref_case_data_global_recovered), "0");
        deaths = caseDataPreference.getString(getString(R.string.pref_case_data_global_deaths), "0");

        String allData = statsPreference.getString(getString(R.string.pref_stats_global_data), "[]");

        if (!allData.equals("[]")) {
            Type type = new TypeToken<ArrayList<GlobalCaseDataModal>>() {
            }.getType();

            allCountryData = gson.fromJson(allData, type);

            showStats();
        }

    }

    private void sortArrayList(){
        if(sortSwitch.isChecked()) {
            allCountryData.sort(new SortCountriesByConfirmedCases());
        }
        else{
            allCountryData.sort(new SortCountriesByName());
        }
    }

}

class SortCountriesByConfirmedCases implements Comparator<GlobalCaseDataModal>
{
    public int compare(GlobalCaseDataModal a, GlobalCaseDataModal b){
        return Integer.parseInt(b.getTotalCases()) - Integer.parseInt(a.getTotalCases());
    }
}

class SortCountriesByName implements Comparator<GlobalCaseDataModal>
{
    public int compare(GlobalCaseDataModal a, GlobalCaseDataModal b){
        return a.getCountryName().compareTo(b.getCountryName());
    }
}
