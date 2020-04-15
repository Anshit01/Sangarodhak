package com.hashbash.sangarodhak.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hashbash.sangarodhak.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentHome extends Fragment implements LocationListener {


    private SharedPreferences sharedPreferences;
    TextView state[] = new TextView[5];
    TextView country[] = new TextView[5];

    Context context;

    LocationManager manager;
    Geocoder geocoder;
    List<Address> addresses;

    public FragmentHome(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.pref_data), Context.MODE_PRIVATE);

        state[0] = view.findViewById(R.id.state_name);
        state[1] = view.findViewById(R.id.state_total_cases);
        state[2] = view.findViewById(R.id.state_recovered);
        state[3] = view.findViewById(R.id.state_dead);
        state[4] = view.findViewById(R.id.state_active);

        country[0] = view.findViewById(R.id.country_name);
        country[1] = view.findViewById(R.id.country_total_cases);
        country[2] = view.findViewById(R.id.country_recovered);
        country[3] = view.findViewById(R.id.country_dead);
        country[4] = view.findViewById(R.id.country_active);

        updateStatsFromPrefs();
        updateStats();

        manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(context, Locale.getDefault());

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        }

        return view;
    }

    private void updateStats(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = getString(R.string.url_india_all_states);

        JsonObjectRequest countryStatsRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            if(response.getBoolean("success")) {
                                String stateName = sharedPreferences.getString(getString(R.string.pref_state), "Himachal Pradesh");
                                JSONObject countryData = response.getJSONObject("data").getJSONObject("summary");
                                JSONArray allStatesData = response.getJSONObject("data").getJSONArray("regional");
                                JSONObject stateData = null;
                                for(int i = 0; i < allStatesData.length(); i++){
                                    if(allStatesData.getJSONObject(i).getString("loc").equals(stateName)){
                                        stateData = allStatesData.getJSONObject(i);
                                    }
                                }
                                if(stateData == null){
                                    throw new JSONException("State " + stateName + " not found.");
                                }
                                int countryConfirmed = countryData.getInt("total");
                                int countryRecovered = countryData.getInt("discharged");
                                int countryDeaths = countryData.getInt("deaths");
                                int countryActive = countryConfirmed - countryRecovered - countryDeaths;

                                int stateConfirmed = stateData.getInt("totalConfirmed");
                                int stateRecovered = stateData.getInt("discharged");
                                int stateDeaths = stateData.getInt("deaths");
                                int stateActive = stateConfirmed - stateRecovered - stateDeaths;

                                country[1].setText("Confirmed: " + countryConfirmed);
                                country[2].setText("Recovered: " + countryRecovered);
                                country[3].setText("Deaths: " + countryDeaths);
                                country[4].setText("Active: " + countryActive);

                                state[1].setText("Confirmed: " + stateConfirmed);
                                state[2].setText("Recovered: " + stateRecovered);
                                state[3].setText("Deaths: " + stateDeaths);
                                state[4].setText("Active: " + stateActive);

                                //TODO: store stats in pref


//                                JSONObject countryStats = response.getJSONObject(2);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putInt(getString(R.string.pref_country_confirmed), countryStats.getInt("Confirmed"));
//                                editor.putInt(getString(R.string.pref_country_recovered), countryStats.getInt("Recovered"));
//                                editor.putInt(getString(R.string.pref_country_dead), countryStats.getInt("Deaths"));
//                                editor.putInt(getString(R.string.pref_country_active), countryStats.getInt("Active"));
//                                editor.apply();
//                                country[1].setText("Confirmed: " + countryStats.getInt("Confirmed"));
//                                country[2].setText("Recovered: " + countryStats.getInt("Recovered"));
//                                country[3].setText("Deaths: " + countryStats.getInt("Deaths"));

                            }
                        } catch (JSONException e) {
                            Log.d("log", "Error: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("log", "ERROR in making request: " + error.toString());
                    }
                });
        queue.add(countryStatsRequest);
    }

    private void updateStatsFromPrefs(){
        //TODO
    }

    private void setData(String countryName, String stateName) {
        state[0].setText(stateName);
        sharedPreferences.edit().putString(getString(R.string.pref_state), stateName);
        country[0].setText(countryName);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Home", "Location Changed\n" + location);
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            setData(addresses.get(0).getCountryName(), addresses.get(0).getAdminArea());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Home", provider + " Status Changed: " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Home", provider + " Enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Home", provider + " Disabled");
    }
}