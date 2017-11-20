package com.example.asus_pc.drawmyapp.model;

public class Session {
    private String state, bitmap, next;

    public Session(String bitmap, String next, String state){
        this.bitmap = bitmap;
        this.next = next;
        this.state = state;
    }

    // for firebase
    public Session(){
        // empty
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }
}
