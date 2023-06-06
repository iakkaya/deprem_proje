package com.example.deprem_proje.Yetkili.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deprem_proje.R;
import com.example.deprem_proje.Yetkili.Location.GetLocation;
import com.example.deprem_proje.Yetkili.UserInfo.UserInfoActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import  com.google.android.gms.maps.OnMapReadyCallback;


public class YetkiliHomeFragment extends Fragment implements  OnMapReadyCallback  {

    private GetLocation getLocation;
    private GoogleMap map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getLocation = new GetLocation();
        View view = inflater.inflate(R.layout.fragment_yetkili_home, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)  getChildFragmentManager().findFragmentById(R.id.yetkiliMap);
        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        getLocation.deneme(markerOptions -> {
            for (MarkerOptions markerOption : markerOptions) {
                map.addMarker(markerOption);
                System.out.println(markerOption.getTitle());
            }
        });

        map.setOnInfoWindowClickListener(marker -> {
            String uid = marker.getSnippet();
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            intent.putExtra("Uid", uid);
            startActivity(intent);
        });
    }
}