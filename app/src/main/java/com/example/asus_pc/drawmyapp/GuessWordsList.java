package com.example.asus_pc.drawmyapp;

import android.util.Log;

public class GuessWordsList {

    private static String[] wordsList;

    public static void setWordsList(String[] wordsList) {
        GuessWordsList.wordsList = wordsList;
        //print();
    }

    private static void print(){
        String a = "";

        for (int i=0; i<wordsList.length; i++){
            a += wordsList[i] + " / ";
        }
        Log.d("array::", a);
    }

    public static String pichAWord(){
        // default
        String word = wordsList[0];

        //Generate number between 0-(last index of wordlist)
        int index = (int)(Math.random()*wordsList.length);

        word = wordsList[index];

        return word;
    }
}
