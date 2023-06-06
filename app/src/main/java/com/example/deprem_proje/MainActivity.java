package com.example.deprem_proje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.auth.GirisYap;
import com.example.deprem_proje.auth.KayitOl;
import com.example.deprem_proje.publicFunctions.PublicFunctions;

public class MainActivity extends AppCompatActivity {
    private Button btnkayitOl;
    private Button btnGirisYap;
    private Auth auth;
    private PublicFunctions publicFunctions;
    private FireStore fireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = new Auth();
        fireStore = new FireStore();
        onAuthStateChanged(savedInstanceState);
        publicFunctions = new PublicFunctions();
        btnkayitOl = findViewById(R.id.btnKayıtOl);
        btnGirisYap = findViewById(R.id.btnGirisYap);
        btnGirisYap.setOnClickListener(v -> {
            publicFunctions.GoPage(MainActivity.this, GirisYap.class, savedInstanceState);
        });
        btnkayitOl.setOnClickListener(v -> {
            publicFunctions.GoPage(MainActivity.this, KayitOl.class, savedInstanceState);
        });


    }

    private  void onAuthStateChanged(Bundle savedInstanceState){
        if(auth != null && auth.getUser() != null){
            fireStore.isUser(auth.getUser().getUid(), savedInstanceState, MainActivity.this);
        }
    }
}