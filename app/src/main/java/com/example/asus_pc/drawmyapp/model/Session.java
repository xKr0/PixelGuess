package com.example.asus_pc.drawmyapp.model;

public class Session {
    private int nextToDraw;
    private String state, bitmap;

    public Session(String bitmap, String next, String state){
        this.bitmap = bitmap;
        this.nextToDraw = Integer.parseInt(next);
        this.state = state;
    }

    // for firebase
    public Session(){
        // empty
    }

    public int getNextToDraw() {
        return nextToDraw;
    }

    public void setNextToDraw(int nextToDraw) {
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
