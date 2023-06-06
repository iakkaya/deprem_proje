package com.example.deprem_proje.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.Kullanici.Kullanici;
import com.example.deprem_proje.R;
import com.example.deprem_proje.publicFunctions.PublicFunctions;
import com.google.firebase.auth.FirebaseAuth;

public class GirisYap extends AppCompatActivity {
    private Button btnGirisYap;
    private Button btnKayitOl;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Auth auth;
private PublicFunctions publicFunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);
        auth = new Auth();
        publicFunctions = new PublicFunctions();
        btnGirisYap = findViewById(R.id.buttonLogin);
        btnKayitOl = findViewById(R.id.buttonRegister);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);

        btnGirisYap.setOnClickListener(v -> {
            signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString());
            onAuthStateChanged(savedInstanceState);
        });
        btnKayitOl.setOnClickListener(v -> {
            publicFunctions.GoPage(GirisYap.this, KayitOl.class ,savedInstanceState);
        });
    }




    private  void signInWithEmailAndPassword(String email, String password){
        auth.signInWithEmailAndPassword(email, password);
    }
    private  void onAuthStateChanged(Bundle options){
        FirebaseAuth.AuthStateListener mAuthStateListener = auth.mAuthStateListener(GirisYap.this, Kullanici.class, options);
        mAuthStateListener.onAuthStateChanged(auth._firebaseAuth);
    }

}