package com.example.deprem_proje.Message;

import com.example.deprem_proje.publicFunctions.PublicFunctions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendMessage {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private PublicFunctions publicFunctions = new PublicFunctions();


    public  void sendMessage(String senderUid, String recieverUid, String message){

    }
}
