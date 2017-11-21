package com.example.asus_pc.drawmyapp;

import com.example.asus_pc.drawmyapp.model.User;

import java.util.ArrayList;

public class PartyManager {
    private PartyManager()
    {}

    private static PartyManager INSTANCE = new PartyManager();

    public static PartyManager getInstance()
    {	return INSTANCE;
    }

    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private int currPlayer = 1;

    final ArrayList<User> usrList = new ArrayList<>();

    public User currUser;

    public GuessActivity guessActivity;

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public void UpdateImageView(String bmpString) {
        if (guessActivity != null)
            guessActivity.setImageView(bmpString);
    }

    public void nextPlayer() {
        if(currPlayer == 2) currPlayer = 1;
        else currPlayer = 2;
    }

    public void incScore() {
        if (currPlayer == 2) {
            scorePlayer2++;
        } else {
            scorePlayer1++;
        }
    }
}