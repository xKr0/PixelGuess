package com.example.asus_pc.drawmyapp.model;

public class User {
    private String pseudo, state, score;

    // for firebase
    public User(){
        //empty
    }

    public User(String pseudo, String state, String score){
        this.pseudo = pseudo;
        this.state = state;
        this.score = score;
    }

    public User(String pseudo, String state){
        this.pseudo = pseudo;
        this.state = state;
        this.score = "0";
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void incrementScore(){
        int points = Integer.valueOf(score);
        points++;
        score = Integer.toString(points);
    }

    // for indexof methods in nexttodraw
    @Override
    public String toString(){
        return this.pseudo + " has " + this.score + " points";
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return user.getPseudo().equals(pseudo);
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + pseudo.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + score.hashCode();
        return result;
    }
}
