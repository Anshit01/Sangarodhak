package com.hashbash.sangarodhak;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class GPSLocationTracker implements LocationListener {

    Context context;

    public GPSLocationTracker(Context context) {
        this.context = context;
    }

    public Location getLocation(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            return null;
        }
        LocationManager manager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        else if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            return manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        else{
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
