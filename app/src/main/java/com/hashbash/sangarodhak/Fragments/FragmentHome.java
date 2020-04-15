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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.hashbash.sangarodhak.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentHome extends Fragment implements LocationListener {

    TextView state[] = new TextView[4];
    TextView country[] = new TextView[4];

    Context context;

    LocationManager manager;
    Geocoder geocoder;
    List<Address> addresses;
    private SharedPreferences preferences;

    public FragmentHome(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        preferences = context.getSharedPreferences(getString(R.string.pref_case_data), Context.MODE_PRIVATE);

        state[0] = view.findViewById(R.id.state_name);
        state[1] = view.findViewById(R.id.state_total_cases);
        state[2] = view.findViewById(R.id.state_recovered);
        state[3] = view.findViewById(R.id.state_dead);

        country[0] = view.findViewById(R.id.country_name);
        country[1] = view.findViewById(R.id.country_total_cases);
        country[2] = view.findViewById(R.id.country_recovered);
        country[3] = view.findViewById(R.id.country_dead);

        getSharedPreferenceData();

        manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(context, Locale.getDefault());

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30 * 1000, 10, this);
        } else if (!preferences.getString(getString(R.string.pref_case_data_state_total_cases), "hi").equals("hi"))
            Toast.makeText(context, "Location not Enabled, Showing last saved data", Toast.LENGTH_SHORT).show();

        return view;
    }

    private void getSharedPreferenceData() {
        state[0].setText(preferences.getString(getString(R.string.pref_case_data_state_name), "State"));
        state[1].setText("Total Cases " + preferences.getString(getString(R.string.pref_case_data_state_total_cases), "Total Cases"));
        state[2].setText("Recovered " + preferences.getString(getString(R.string.pref_case_data_state_recovered), "Recovered"));
        state[3].setText("Dead " + preferences.getString(getString(R.string.pref_case_data_state_dead), "Dead"));

        country[0].setText(preferences.getString(getString(R.string.pref_case_data_country_name), "Country"));
        country[1].setText("Total Cases " + preferences.getString(getString(R.string.pref_case_data_country_total_cases), "Total Cases"));
        country[2].setText("Recovered " + preferences.getString(getString(R.string.pref_case_data_country_recovered), "Recovered"));
        country[3].setText("Dead " + preferences.getString(getString(R.string.pref_case_data_country_dead), "Dead"));
    }

    private void setData(String countryName, String stateName) {
        state[0].setText(stateName);
        country[0].setText(countryName);

        String stateTotalCases = "", stateRecovered = "", stateDead = "", countryTotalCases = "", countryRecovered = "", countryDead = "";

        state[1].setText("Total Cases " + stateTotalCases);
        state[2].setText("Recovered " + stateRecovered);
        state[3].setText("Dead " + stateDead);

        country[1].setText("Total Cases " + countryTotalCases);
        country[2].setText("Recovered " + countryRecovered);
        country[3].setText("Dead " + countryDead);

        preferences.edit().putString(getString(R.string.pref_case_data_state_name), stateName)
                .putString(getString(R.string.pref_case_data_state_total_cases), stateTotalCases)
                .putString(getString(R.string.pref_case_data_state_recovered), stateRecovered)
                .putString(getString(R.string.pref_case_data_state_dead), stateDead)
                .putString(getString(R.string.pref_case_data_country_name), countryName)
                .putString(getString(R.string.pref_case_data_country_total_cases), countryTotalCases)
                .putString(getString(R.string.pref_case_data_country_recovered), countryRecovered)
                .putString(getString(R.string.pref_case_data_country_dead), countryDead)
                .apply();
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