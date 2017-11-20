package com.example.asus_pc.drawmyapp.model;

public class Session {
    private String nextToDraw;
    private String state, bitmap;

    public Session(String bitmap, String next, String state){
        this.bitmap = bitmap;
        this.nextToDraw = next;
        this.state = state;
    }

    // for firebase
    public Session(){
        // empty
    }

    public String getNextToDraw() {
        return nextToDraw;
    }

    public void setNextToDraw(String nextToDraw) {
        this.nextToDraw = nextToDraw;
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
