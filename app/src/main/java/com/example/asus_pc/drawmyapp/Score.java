package com.example.asus_pc.drawmyapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.asus_pc.drawmyapp.model.User;

public class Score {
    private Score()
    {}

    private static Score INSTANCE = new Score();

    public static Score getInstance()
    {	return INSTANCE;
    }

    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;

    private int currPlayer = 1;

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
