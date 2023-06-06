package com.example.deprem_proje.Firabase;

import androidx.annotation.NonNull;

import com.example.deprem_proje.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealtimeDatabase {

    DatabaseReference usersReference =  FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference chatsReference =  FirebaseDatabase.getInstance().getReference("Chats");


    public void setUserId(String userUid, String name, String phone, String email, String password, String kanGrubu){
        Map<String, Object> info = new HashMap<>();
        info.put("id", userUid);
        info.put("userName", name);
        info.put("phone", phone);
        info.put("email", email);
        info.put("password", password);
        info.put("kanGrubu", kanGrubu);
         usersReference.child(userUid).setValue(info);
    }

    public void sendMessage(String sender, String reciever, String message){
        Map<String, Object> messageInfos = new HashMap<>();
        messageInfos.put("sender", sender);
        messageInfos.put("reciever", reciever);
        messageInfos.put("message", message);
        chatsReference.push().setValue(messageInfos);
    }


    public void getUsers(String currentUserId, List<User> mUsers){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (!user.getId().equals(currentUserId)){
                        mUsers.add(user);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
