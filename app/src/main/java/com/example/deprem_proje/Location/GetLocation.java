package com.example.deprem_proje.Location;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.Firabase.FireStore;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import java.util.List;


public class GetLocation extends AppCompatActivity implements LocationListener {

    private Context context;
    private Activity activity;
    private  FusedLocationProviderClient locationProviderClient;
    private FireStore fireStore;

    public GetLocation(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        fireStore = new FireStore();
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }
    public void getLocation(String userUid, boolean isSafe, String tarih){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            sendLocation(userUid, isSafe, tarih);
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }
    @SuppressLint("MissingPermission")
    private void sendLocation(String userUid, boolean isSafe, String tarih) {

        LocationManager manager = (LocationManager)  context.getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null){
            fireStore.sendUserLocation(userUid, location, isSafe, tarih);
        }else {
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }


}
