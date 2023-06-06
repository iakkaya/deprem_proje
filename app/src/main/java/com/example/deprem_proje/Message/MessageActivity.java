package com.example.deprem_proje.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deprem_proje.Adapter.MessageAdapter;
import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.Firabase.RealtimeDatabase;
import com.example.deprem_proje.Model.Chat;
import com.example.deprem_proje.Model.User;
import com.example.deprem_proje.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {


    private TextView userName;

    private Auth auth;
    private Intent intent;
    private ImageButton btnSendMessage;
    private RealtimeDatabase realtimeDatabase;
    private EditText txtEditMessage;
    private String currentUser;
    private MessageAdapter messageAdapter;
    private List<Chat> mChat;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        recyclerView = findViewById(R.id.recycler_view_chat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        userName = findViewById(R.id.userName);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        txtEditMessage = findViewById(R.id.txtEditMessage);
        intent = getIntent();
        String recieverUid = intent.getStringExtra("recieverUid");
        auth = new Auth();
        realtimeDatabase = new RealtimeDatabase();
        currentUser = auth.getUser().getUid();

        getUserName();
        readMessages(currentUser, recieverUid);
        btnSendMessage.setOnClickListener(v -> {
            if (!txtEditMessage.getText().toString().equals("")){
                realtimeDatabase.sendMessage(currentUser, recieverUid, txtEditMessage.getText().toString());
            }else{
                Toast.makeText(this,"Mesaj boş olamaz", Toast.LENGTH_SHORT).show();
            }
            txtEditMessage.setText("");
        });

    }

    private void readMessages(String myId, String userId){
        mChat = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getReciever().equals(myId) && chat.getSender().equals(userId) ||
                    chat.getReciever().equals(userId) && chat.getSender().equals(myId)){
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this,mChat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getUserName(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userName.setText(user.getUserName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}