package com.example.asus_pc.drawmyapp.model;

public class User {
    private String pseudo, state;

    public User(String pseudo, String state){
        this.pseudo = pseudo;
        this.state = state;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
