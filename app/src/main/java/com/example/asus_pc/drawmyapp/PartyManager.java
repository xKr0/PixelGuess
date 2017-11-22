package com.example.asus_pc.drawmyapp;

import android.util.Log;

import com.example.asus_pc.drawmyapp.model.User;

import java.util.ArrayList;

public class PartyManager {
    private PartyManager()
    {}

    private static PartyManager INSTANCE = new PartyManager();

    public static PartyManager getInstance()
    {	return INSTANCE;
    }

    final ArrayList<User> usrList = new ArrayList<>();

    public String answer;

    public User currUser;

    public String next = "";

    public String state = "";

    public GuessActivity guessActivity;

    public void UpdateImageView(String bmpString) {
        if (guessActivity != null)
            guessActivity.setImageView(bmpString);
    }

    public void SetNextToDraw() {
        int index = usrList.indexOf(currUser);

        Log.d("index::", Integer.toString(index));
        index++;

        if (index >= usrList.size()) {
            index = 0;
        }

        Log.d("index_next::", Integer.toString(index));
        next = usrList.get(index).getPseudo();
    }

}
