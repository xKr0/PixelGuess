package com.example.asus_pc.drawmyapp;

import android.support.v7.app.AppCompatActivity;

import com.example.asus_pc.drawmyapp.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteOnDestroyActivity extends AppCompatActivity
{
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public User currUser;

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (currUser != null){
            ref.child("users").child(currUser.getPseudo()).removeValue();
        }
    }

    protected void addUserToDatabase(String pseudo){
        currUser = new User(pseudo, "none");

        // we also had the user into the database Firebase
        ref.child("users").child(currUser.getPseudo()).setValue(currUser);
    }
}
