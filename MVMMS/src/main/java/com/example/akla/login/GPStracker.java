package com.example.akla.login;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;


public class GPStracker implements LocationListener{
    Context context;
    private GoogleMap mMap;

    public GPStracker(Context c){
        context = c;
    }


    public Location getLocation(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 10 , this);
            Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return  l;
        }else{
            Toast.makeText(context, "Please enable GPS", Toast.LENGTH_LONG).show();
        }
        return null;
    }
    @Override
    public void onLocationChanged(Location location) {
        /**get the latitude*/
        double latitude = location.getLatitude();

        /**get the longitude*/
        double longitude = location.getLongitude();

        /**Instantiate LatLng class*/
        LatLng latLng = new LatLng(latitude, longitude);

        /**Instantiate Geocoder class*/
        Geocoder geocoder = new Geocoder(context.getApplicationContext());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            String string = addressList.get(0).getLocality() + " , ";
            string += addressList.get(0).getCountryName();
            //mMap.addMarker(new MarkerOptions().position(latLng).title(string));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
        } catch (IOException e) {
            e.printStackTrace();
        }
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