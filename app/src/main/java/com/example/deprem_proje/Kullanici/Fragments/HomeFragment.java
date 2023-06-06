package com.example.deprem_proje.Kullanici.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.deprem_proje.R;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)  getChildFragmentManager().findFragmentById(R.id.userMap);
        mapFragment.getMapAsync(googleMap -> {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(20,20)).snippet("Adana").title("Fatih"));
        });

        return view;
    }


}