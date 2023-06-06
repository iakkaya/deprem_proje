package com.example.deprem_proje.Yetkili.UserInfo;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GetUserInfo {

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    public void getuserInfo(String uid,UserInfoCallback callback){
        Map<String, Object> userInfo = new HashMap<>();
        fireStore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()){
                userInfo.put("name", documentSnapshot.getData().get("name"));
                userInfo.put("email", documentSnapshot.getData().get("email"));
                userInfo.put("phone", documentSnapshot.getData().get("phone"));
                userInfo.put("kanGrubu", documentSnapshot.getData().get("kanGrubu"));
            }
            callback.onDataReceived(userInfo);
        });
    }


    public interface UserInfoCallback {
        void onDataReceived(Map<String, Object> userInfo);
    }
}
