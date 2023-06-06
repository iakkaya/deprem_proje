package com.example.deprem_proje.Firabase;



import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.deprem_proje.MainActivity;
import com.example.deprem_proje.auth.GirisYap;
import com.example.deprem_proje.publicFunctions.PublicFunctions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Auth {


    final public FirebaseAuth _firebaseAuth = FirebaseAuth.getInstance();
    private PublicFunctions  publicFunctions = new PublicFunctions();
    private FirebaseUser user = _firebaseAuth.getCurrentUser();
    public FirebaseUser getUser() {
        return user;
    }


    public FirebaseAuth.AuthStateListener mAuthStateListener(Context context, Class<?> cls , Bundle options){
        FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser firebaseUser = _firebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseUser != null){
                    publicFunctions.GoPage(context, cls, options);
                }else if(firebaseUser == null){
                    publicFunctions.GoPage(context, MainActivity.class, options);
                }
            }
        };
        return  mAuthStateListener;
    }
    public Task<AuthResult> createUserWithEmailAndPassword(String email, String password) {
        Task<AuthResult> a =   _firebaseAuth.createUserWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(task -> {

        });
return  a;
    }
    public  void signInWithEmailAndPassword(String email, String password){
        _firebaseAuth.signInWithEmailAndPassword(email.trim(), password.trim());
    }
    public  void signOut(){
        _firebaseAuth.signOut();
    }
}
