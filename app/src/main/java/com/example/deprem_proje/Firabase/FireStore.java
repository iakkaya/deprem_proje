package com.example.deprem_proje.Firabase;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.example.deprem_proje.Kullanici.Kullanici;
import com.example.deprem_proje.Yetkili.Yetkili;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FireStore {
    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    Auth auth = new Auth();



    public void  setUserInfos(String userUid, String name, String phone, String email, String password, String kanGrubu, boolean isUser){
        final java.util.Map<String, Object> userInfos = new HashMap<>();
        userInfos.put("name", name);
        userInfos.put("phone", phone);
        userInfos.put("email", email);
        userInfos.put("password", password);
        userInfos.put("kanGrubu", kanGrubu);

        fireStore.collection( isUser == true ? "Users" : "Yetkili").document(userUid).set(userInfos);
    }
    public  void sendUserLocation(String userUid, Location location, boolean isSafe,String tarih){
        final java.util.Map<String, Object> userLocationInfos = new HashMap<>();
        userLocationInfos.put("latitude", location.getLatitude());
        userLocationInfos.put("longitude", location.getLongitude());
        userLocationInfos.put("tarih", tarih);
        userLocationInfos.put("isSafe", isSafe);
        fireStore.collection("Users").document(userUid).collection("Konumum").document("Konumum").set(userLocationInfos);
    }

    public void removeUserLocation(String userUid,boolean isSafe,String tarih){
        final java.util.Map<String, Object> userLocationInfos = new HashMap<>();
        userLocationInfos.put("latitude", null);
        userLocationInfos.put("longitude", null);
        userLocationInfos.put("tarih", tarih);
        userLocationInfos.put("isSafe", isSafe);
        fireStore.collection("Users").document(userUid).collection("Konumum").document("Konumum").set(userLocationInfos);
    }

    public void isUser(String userId, Bundle options, Context context) {
     fireStore.collection("Users").get().addOnSuccessListener(queryDocumentSnapshots -> {
        List<DocumentSnapshot> userSnapshot = queryDocumentSnapshots.getDocuments();
         List<String> userUids = new ArrayList<>();

         for (DocumentSnapshot document : userSnapshot) {
             String documentId = document.getId();
             userUids.add(documentId);

         }

         if (!userUids.isEmpty()){
             for (String uid:userUids) {
                 if(uid.equals(userId)){
                     System.out.println("user "+ uid.equals(userId));
                     FirebaseAuth.AuthStateListener mAuthStateListener = auth.mAuthStateListener(context,  Kullanici.class , options);
                     mAuthStateListener.onAuthStateChanged(auth._firebaseAuth);
                 }else{
                     //return;
                 }

             }
         }
      });
     fireStore.collection("Yetkili").get().addOnSuccessListener(queryDocumentSnapshots -> {
         List<DocumentSnapshot> yetkiliSnapshot = queryDocumentSnapshots.getDocuments();
         List<String> yetkiliUids = new ArrayList<>();
         for (DocumentSnapshot document : yetkiliSnapshot) {
             String documentId = document.getId();
             yetkiliUids.add(documentId);
         }
         if (!yetkiliUids.isEmpty()){
             for (String uid:yetkiliUids) {
                 if(uid.equals(userId)){
                     System.out.println("yetkili "+ uid.equals(userId));
                     FirebaseAuth.AuthStateListener mAuthStateListener = auth.mAuthStateListener(context,  Yetkili.class , options);
                     mAuthStateListener.onAuthStateChanged(auth._firebaseAuth);
                 }else{
                     //return;
                 }
             }
         }
     });
    }

    public Task<QuerySnapshot> taskUid(){
        List<String> userUids = new ArrayList<>();

        Task<QuerySnapshot> task = fireStore.collection("Users").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> userSnapshot = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot document : userSnapshot) {
                String documentId = document.getId();
                userUids.add(documentId);
            }
        });
       return  task;
    }

}
