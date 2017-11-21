package com.example.asus_pc.drawmyapp;

import android.support.v7.app.AppCompatActivity;

import com.example.asus_pc.drawmyapp.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteOnDestroyActivity extends AppCompatActivity
{
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public String next = "";

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Score.getInstance().currUser != null){
            ref.child("users").child(Score.getInstance().currUser.getPseudo()).removeValue();
        }
    }

    protected void addUserToDatabase(String pseudo){
        Score.getInstance().currUser = new User(pseudo, "ready");

        // we change the user to play
        ref.child("session").child("next").setValue(pseudo);

        // we also had the user into the database Firebase
        ref.child("users").child(Score.getInstance().currUser.getPseudo()).setValue(Score.getInstance().currUser);
    }

}
