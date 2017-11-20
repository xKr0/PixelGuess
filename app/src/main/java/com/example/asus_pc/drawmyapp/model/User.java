package com.example.asus_pc.drawmyapp.model;

public class User {
    private String pseudo, state;
    private int score;

    // for firebase
    public User(){
        //empty
    }

    public User(String pseudo, String state, int score){
        this.pseudo = pseudo;
        this.state = state;
        this.score = score;
    }

    public User(String pseudo, String state){
        this.pseudo = pseudo;
        this.state = state;
        this.score = 0;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
