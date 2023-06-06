package com.example.deprem_proje.Kullanici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.deprem_proje.Firabase.RealtimeDatabase;
import com.example.deprem_proje.Message.ChatFragment;
import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.Kullanici.Fragments.HomeFragment;
import com.example.deprem_proje.Location.GetLocation;
import com.example.deprem_proje.Model.User;
import com.example.deprem_proje.R;
import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.databinding.ActivityKullaniciBinding;
import com.example.deprem_proje.publicFunctions.PublicFunctions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Kullanici extends AppCompatActivity    {

    private ActivityKullaniciBinding binding;
    private Auth auth;
    private  boolean isSafe;
    private GetLocation getLocation;
    private FireStore fireStore;
    private PublicFunctions publicFunctions = new PublicFunctions();
    private String date;
    private Toolbar toolbar;
    private  Bundle options;
    private RealtimeDatabase realtimeDatabase;
    private List<User> mUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKullaniciBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.userBottomNavigationView.setBackground(null);
        binding.userBottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.chat) {
                replaceFragment(new ChatFragment());
            }
            return true;
        });

        setViews();
        options = savedInstanceState;
        realtimeDatabase.getUsers(auth.getUser().getUid(), mUsers);
    }



    private void setViews(){

        auth = new Auth();
        toolbar = findViewById(R.id.appBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fireStore = new FireStore();
        getLocation = new GetLocation(this, this);
        isSafe = true;
        mUsers = new ArrayList<>();
        realtimeDatabase = new RealtimeDatabase();
    }

    private void signOut() {
        auth.signOut();
    }

    private void onAuthStateChanged(Bundle options) {
        FirebaseAuth.AuthStateListener mAuthStateListener = auth.mAuthStateListener(Kullanici.this, Kullanici.class, options);
        mAuthStateListener.onAuthStateChanged(auth._firebaseAuth);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.user_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void guvendeyim(View view) {
        date = publicFunctions.getCurrenDate();
        isSafe = true;
       fireStore.removeUserLocation(auth.getUser().getUid(), isSafe, date);
    }

    public void guvendeDegilim(View view){
        date = publicFunctions.getCurrenDate();

        isSafe = false;
        getLocation.getLocation(auth.getUser().getUid(), isSafe,date);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            date = publicFunctions.getCurrenDate();
            getLocation.getLocation(auth.getUser().getUid(), isSafe,date);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signOut) {
            signOut();
            onAuthStateChanged(options);
        }
        return true;
    }



}


