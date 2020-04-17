package com.hashbash.sangarodhak.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hashbash.sangarodhak.R;
import com.hashbash.sangarodhak.StatsIndiaActivity;
import com.hashbash.sangarodhak.StatsStateActivity;
import com.hashbash.sangarodhak.StatsWorldActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentHome extends Fragment implements LocationListener {

    private final int GET_LOCATION = 12;

    private String stateName;
    private String countryName;

    private SharedPreferences sharedPreferences;
    private TextView state[] = new TextView[5];
    private TextView country[] = new TextView[5];

    private TextView globalConfirmedTextView;
    private TextView globalRecoveredTextView;
    private TextView globalDeathsTextView;

    private LinearLayout globalStatsLinearLayout;
    private LinearLayout countryStatsLinearLayout;
    private LinearLayout stateStatsLinearLayout;

    private LocationManager manager;
    private Geocoder geocoder;
    private List<Address> addresses;
    private SharedPreferences preferences;

    public FragmentHome() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        preferences = getActivity().getSharedPreferences(getString(R.string.pref_case_data), Context.MODE_PRIVATE);

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

        globalConfirmedTextView = view.findViewById(R.id.global_confirmed);
        globalRecoveredTextView = view.findViewById(R.id.global_recovered);
        globalDeathsTextView = view.findViewById(R.id.global_deaths);

        globalStatsLinearLayout = view.findViewById(R.id.global_stats_layout);
        countryStatsLinearLayout = view.findViewById(R.id.country_stats_layout);
        stateStatsLinearLayout = view.findViewById(R.id.state_stats_layout);

        globalStatsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StatsWorldActivity.class);
                startActivity(intent);
            }
        });

        countryStatsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StatsIndiaActivity.class);
                startActivity(intent);
            }
        });

        stateStatsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StatsStateActivity.class);
                startActivity(intent);
            }
        });

        getSharedPreferenceData();
        setGlobalData();

        manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 50, this);
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 50, this);
            } else
                askGPSTurnOn();
        } else
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GET_LOCATION);

        return view;
    }

    private void getSharedPreferenceData() {
        state[0].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_state_name), "State"));
        state[1].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_state_total_cases), "0"));
        state[2].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_state_active), "0"));
        state[3].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_state_recovered), "0"));
        state[4].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_state_dead), "0"));

        country[0].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_country_name), "Country"));
        country[1].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_country_total_cases), "0"));
        country[2].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_country_active), "0"));
        country[3].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_country_recovered), "0"));
        country[4].setText(preferences.getString(getActivity().getString(R.string.pref_case_data_country_dead), "0"));

        globalConfirmedTextView.setText(preferences.getString(getActivity().getString(R.string.pref_case_data_global_confirmed), "0"));
        globalRecoveredTextView.setText(preferences.getString(getActivity().getString(R.string.pref_case_data_global_recovered), "0"));
        globalDeathsTextView.setText(preferences.getString(getActivity().getString(R.string.pref_case_data_global_deaths), "0"));

    }


    private void setData(final String stateName, final String countryName) {
        state[0].setText(stateName);
        country[0].setText(countryName);

        final String[] stateValues = new String[4];
        final String[] countryValues = new String[4];

        RequestQueue queue = Volley.newRequestQueue(getActivity());

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

                                preferences.edit()
                                        .putString(getContext().getString(R.string.pref_case_data_state_name), stateName)
                                        .putString(getContext().getString(R.string.pref_case_data_state_total_cases), stateValues[0])
                                        .putString(getContext().getString(R.string.pref_case_data_state_active), stateValues[1])
                                        .putString(getContext().getString(R.string.pref_case_data_state_recovered), stateValues[2])
                                        .putString(getContext().getString(R.string.pref_case_data_state_dead), stateValues[3])
                                        .putString(getContext().getString(R.string.pref_case_data_country_name), countryName)
                                        .putString(getContext().getString(R.string.pref_case_data_country_total_cases), countryValues[0])
                                        .putString(getContext().getString(R.string.pref_case_data_country_active), countryValues[1])
                                        .putString(getContext().getString(R.string.pref_case_data_country_recovered), countryValues[2])
                                        .putString(getContext().getString(R.string.pref_case_data_country_dead), countryValues[3])
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

    private void setGlobalData() {
        String url = getString(R.string.url_world_stats);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject globalData = response.getJSONObject("Global");
                    String confirmed = "" + globalData.getInt("TotalConfirmed");
                    String recovered = "" + globalData.getInt("TotalRecovered");
                    String deaths = "" + globalData.getInt("TotalDeaths");

                    globalConfirmedTextView.setText(confirmed);
                    globalRecoveredTextView.setText(recovered);
                    globalDeathsTextView.setText(deaths);

                    preferences.edit().putString(getString(R.string.pref_case_data_global_confirmed), confirmed)
                            .putString(getString(R.string.pref_case_data_global_recovered), recovered)
                            .putString(getString(R.string.pref_case_data_global_deaths), deaths)
                            .apply();
                } catch (JSONException e) {
                    Log.d("log", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log", error.toString());
            }
        });
        queue.add(request);
    }

    private void setDistrictData() {
        final String district = preferences.getString(getString(R.string.pref_case_data_district_name), null);
        final String state = preferences.getString(getString(R.string.pref_case_data_state_name), null);
        if (district == null || state == null) {

            return;
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.url_all_districts);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject districtsData = response.getJSONObject(state).getJSONObject("districtData");
                    if (districtsData.has(district)) {
                        int confirmed = districtsData.getJSONObject(district).getInt("confirmed");
                        preferences.edit().putInt(getString(R.string.pref_case_data_district_confirmed), confirmed).apply();
                        Log.d("log", "Cases in " + district + ": " + confirmed);
                        //TODO
                    } else {
                        //TODO
                        Log.d("log", "No cases in " + district + "!");
                    }
                } catch (JSONException e) {
                    Log.d("log", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void onLocationChanged(final Location location) {
        Log.d("Home", "Location Changed\n" + location);
        new Runnable() {
            @Override
            public void run() {
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Log.d("Home", "Address Received\n" + addresses);
                    countryName = addresses.get(0).getCountryName();
                    stateName = addresses.get(0).getAdminArea();
                    setData(stateName, countryName);
                    preferences.edit().putString(getString(R.string.pref_case_data_district_name), addresses.get(0).getSubAdminArea()).apply();
                    setDistrictData();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Home", "Address Not Received");
                }
            }
        }.run();
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

    private void askGPSTurnOn() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(getActivity()).checkLocationSettings(builder.build());


        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        getActivity(),
                                        LocationRequest.PRIORITY_HIGH_ACCURACY);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }
}