package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;

import com.example.asus_pc.drawmyapp.model.Session;
import com.example.asus_pc.drawmyapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LobbyActivity extends DeleteOnDestroyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setIntentArray();
        getNextUserToDraw();
        Log.d("lobby::", PartyManager.getInstance().next);

        startParty();
    }

    protected void setIntentArray(){
        intentArray = new Intent[3];
        intentArray[0] = new Intent(LobbyActivity.this, MainActivity.class);
        intentArray[1] = new Intent(LobbyActivity.this, GuessActivity.class);
        intentArray[2] = new Intent(LobbyActivity.this, ResultActivity.class);
    }

    // Consume the event to not go back to the drawing activity
    @Override
    public void onBackPressed() {return;}
}
