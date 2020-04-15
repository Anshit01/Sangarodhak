package com.hashbash.sangarodhak.Fragments;

import android.Manifest;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

    public FragmentHome(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        state[0] = view.findViewById(R.id.state_name);
        state[1] = view.findViewById(R.id.state_total_cases);
        state[2] = view.findViewById(R.id.state_recovered);
        state[3] = view.findViewById(R.id.state_dead);

        country[0] = view.findViewById(R.id.country_name);
        country[1] = view.findViewById(R.id.country_total_cases);
        country[2] = view.findViewById(R.id.country_recovered);
        country[3] = view.findViewById(R.id.country_dead);

        manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(context, Locale.getDefault());

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        }

        return view;
    }

    private void setData(String countryName, String stateName) {
        state[0].setText(stateName);
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