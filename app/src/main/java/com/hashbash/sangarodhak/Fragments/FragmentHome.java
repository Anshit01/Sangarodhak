package com.hashbash.sangarodhak.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hashbash.sangarodhak.R;
import com.hashbash.sangarodhak.StatsIndiaActivity;
import com.hashbash.sangarodhak.StatsStateActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentHome extends Fragment implements LocationListener {


    private SharedPreferences sharedPreferences;
    private TextView state[] = new TextView[5];
    private TextView country[] = new TextView[5];

    private LinearLayout countryStatsLinearLayout;
    private LinearLayout stateStatsLinearLayout;

    private Context context;

    private LocationManager manager;
    private Geocoder geocoder;
    private List<Address> addresses;
    private SharedPreferences preferences;

    public FragmentHome() {
    }

    public FragmentHome(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        preferences = context.getSharedPreferences(getString(R.string.pref_case_data), Context.MODE_PRIVATE);

        state[0] = view.findViewById(R.id.state_name);
        state[1] = view.findViewById(R.id.state_total_cases);
        state[2] = view.findViewById(R.id.state_active);
        state[3] = view.findViewById(R.id.state_recovered);
        state[4] = view.findViewById(R.id.state_dead);

        country[0] = view.findViewById(R.id.country_name);
        country[1] = view.findViewById(R.id.country_total_cases);
        country[2] = view.findViewById(R.id.country_active);
        country[3] = view.findViewById(R.id.country_recovered);
        country[4] = view.findViewById(R.id.country_dead);

        countryStatsLinearLayout = view.findViewById(R.id.country_stats_layout);
        stateStatsLinearLayout = view.findViewById(R.id.state_stats_layout);

        countryStatsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatsIndiaActivity.class);
                startActivity(intent);
            }
        });

        stateStatsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatsStateActivity.class);
                startActivity(intent);
            }
        });

        getSharedPreferenceData();

        manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(context, Locale.getDefault());

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 0, this);
        } else if (!preferences.getString(context.getString(R.string.pref_case_data_state_total_cases), "hi").equals("hi"))
            Toast.makeText(context, "Location not Enabled, Showing last saved data", Toast.LENGTH_SHORT).show();

        return view;
    }

    private void getSharedPreferenceData() {
        state[0].setText(preferences.getString(context.getString(R.string.pref_case_data_state_name), "State"));
        state[1].setText(preferences.getString(context.getString(R.string.pref_case_data_state_total_cases), "0"));
        state[2].setText(preferences.getString(context.getString(R.string.pref_case_data_state_active), "0"));
        state[3].setText(preferences.getString(context.getString(R.string.pref_case_data_state_recovered), "0"));
        state[4].setText(preferences.getString(context.getString(R.string.pref_case_data_state_dead), "0"));

        country[0].setText(preferences.getString(context.getString(R.string.pref_case_data_country_name), "Country"));
        country[1].setText(preferences.getString(context.getString(R.string.pref_case_data_country_total_cases), "0"));
        country[2].setText(preferences.getString(context.getString(R.string.pref_case_data_country_active), "0"));
        country[3].setText(preferences.getString(context.getString(R.string.pref_case_data_country_recovered), "0"));
        country[4].setText(preferences.getString(context.getString(R.string.pref_case_data_country_dead), "0"));

    }


    private void setData(final String countryName, final String stateName) {
        state[0].setText(stateName);
        country[0].setText(countryName);

        final String[] stateValues = new String[4];
        final String[] countryValues = new String[4];

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = getString(R.string.url_india_all_states);

        JsonObjectRequest countryStatsRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject countryData = response.getJSONObject("data").getJSONObject("summary");
                                JSONArray allStatesData = response.getJSONObject("data").getJSONArray("regional");
                                JSONObject stateData = null;
                                for (int i = 0; i < allStatesData.length(); i++) {
                                    if (allStatesData.getJSONObject(i).getString("loc").equals(stateName)) {
                                        stateData = allStatesData.getJSONObject(i);
                                    }
                                }
                                if (stateData == null) {
                                    throw new JSONException("State " + stateName + " not found.");
                                }
                                countryValues[0] = "" + countryData.getInt("total");
                                countryValues[2] = "" + countryData.getInt("discharged");
                                countryValues[3] = "" + countryData.getInt("deaths");
                                countryValues[1] = "" + (Integer.parseInt(countryValues[0]) - Integer.parseInt(countryValues[2]) - Integer.parseInt(countryValues[3]));

                                stateValues[0] = "" + stateData.getInt("totalConfirmed");
                                stateValues[2] = "" + stateData.getInt("discharged");
                                stateValues[3] = "" + stateData.getInt("deaths");
                                stateValues[1] = "" + (Integer.parseInt(stateValues[0]) - Integer.parseInt(stateValues[2]) - Integer.parseInt(stateValues[3]));

                                country[1].setText(countryValues[0]);
                                country[2].setText(countryValues[1]);
                                country[3].setText(countryValues[2]);
                                country[4].setText(countryValues[3]);

                                state[1].setText(stateValues[0]);
                                state[2].setText(stateValues[1]);
                                state[3].setText(stateValues[2]);
                                state[4].setText(stateValues[3]);

                                preferences.edit().putString(context.getString(R.string.pref_case_data_state_name), stateName)
                                        .putString(context.getString(R.string.pref_case_data_state_total_cases), stateValues[0])
                                        .putString(context.getString(R.string.pref_case_data_state_active), stateValues[2])
                                        .putString(context.getString(R.string.pref_case_data_state_recovered), stateValues[2])
                                        .putString(context.getString(R.string.pref_case_data_state_dead), stateValues[3])
                                        .putString(context.getString(R.string.pref_case_data_country_name), countryName)
                                        .putString(context.getString(R.string.pref_case_data_country_total_cases), countryValues[0])
                                        .putString(context.getString(R.string.pref_case_data_country_active), countryValues[1])
                                        .putString(context.getString(R.string.pref_case_data_country_recovered), countryValues[2])
                                        .putString(context.getString(R.string.pref_case_data_country_dead), countryValues[3])
                                        .apply();

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

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Home", "Location Changed\n" + location);
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Log.d("Home", "Address Received\n" + addresses);
            setData(addresses.get(0).getCountryName(), addresses.get(0).getAdminArea());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Home", "Address Not Received");
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