package com.example.deprem_proje.Yetkili.UserInfo;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.Message.MessageActivity;
import com.example.deprem_proje.R;
import com.example.deprem_proje.publicFunctions.PublicFunctions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private TextView txtAdSoyad;
    private TextView txtEmail;
    private TextView txtTelNo;
    private TextView txtKanGrubu;
    private Button btnKurtarildi;
    private Button btnMesajAt;
    private GetUserInfo getUserInfo;
    private Intent intent;
    private FireStore fireStore;
    private PublicFunctions publicFunctions;
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setViews();
        String uid =intent.getStringExtra("Uid");
        getUserInfo.getuserInfo(uid,userInfo -> {
            txtAdSoyad.setText(userInfo.get("name").toString());
            txtEmail.setText(userInfo.get("email").toString());
            txtTelNo.setText(userInfo.get("phone").toString());
            txtKanGrubu.setText(userInfo.get("kanGrubu").toString());
        });

        btnKurtarildi.setOnClickListener(v -> {
            fireStore.removeUserLocation(uid, true, publicFunctions.getCurrenDate());
        });
        btnMesajAt.setOnClickListener(v -> {
            Intent intent = new Intent(this, MessageActivity.class);
            intent.putExtra("recieverUid", uid);
            startActivity(intent);
        });
    }



    private void setViews(){
        intent = getIntent();
        getUserInfo = new GetUserInfo();
        fireStore = new FireStore();
        auth = new Auth();
        publicFunctions = new PublicFunctions();
        txtAdSoyad = findViewById(R.id.txtAdSoyad);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelNo = findViewById(R.id.txtTelNo);
        txtKanGrubu = findViewById(R.id.txtKanGrubu);
        btnKurtarildi = findViewById(R.id.btnKurtarildi);
        btnMesajAt = findViewById(R.id.btnMesaj);
    }

}