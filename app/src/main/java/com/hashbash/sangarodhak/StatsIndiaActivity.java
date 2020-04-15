package com.hashbash.sangarodhak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class StatsIndiaActivity extends AppCompatActivity {

    ArrayList<String> statesList = new ArrayList<>();
    ArrayList<Integer> confirmedCasesList = new ArrayList<>();
    ArrayList<Integer> recoveredCasesList = new ArrayList<>();
    ArrayList<Integer> deathsList = new ArrayList<>();
    ArrayList<Integer> activeCasesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_india);

        fetchData();



    }

    private void fetchData(){
        String url = getString(R.string.url_india_all_states);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray statesData = response.getJSONObject("data").getJSONArray("regional");
                    int len = statesData.length();
                    for(int i = 0; i < len; i++){
                        JSONObject state = statesData.getJSONObject(i);
                        statesList.add(state.getString("loc"));
                        confirmedCasesList.add(state.getInt("totalConfirmed"));
                        recoveredCasesList.add(state.getInt("discharged"));
                        deathsList.add(state.getInt("deaths"));
                        activeCasesList.add(confirmedCasesList.get(i) - recoveredCasesList.get(i) - deathsList.get(i));
                    }
                    showStats();
                } catch (JSONException e) {
                    Log.d("log", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log", error.getMessage());
            }
        });
        queue.add(request);
    }

    private void showStats(){
        //TODO
    }

}
