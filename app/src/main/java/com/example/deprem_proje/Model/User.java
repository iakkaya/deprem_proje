package com.example.deprem_proje.Model;

public class User {

    private String id;
    private String userName;



    public User(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }
    public User() {
        // Parametresiz yapıcı
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}