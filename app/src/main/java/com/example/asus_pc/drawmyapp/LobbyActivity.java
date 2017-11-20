package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.asus_pc.drawmyapp.DeleteOnDestroyActivity;
import com.example.asus_pc.drawmyapp.model.Session;
import com.example.asus_pc.drawmyapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

public class LobbyActivity extends DeleteOnDestroyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        getNextUserToDraw();
        startParty();
    }

    protected void startParty(){
        final ArrayList<User> usrList = new ArrayList<>();

        ref.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> eventIterable = dataSnapshot.getChildren();
                User newValue = null;
                for(DataSnapshot eventSnap : eventIterable) {

                    Map eventMap = (Map) eventSnap.getValue();

                    User usr = eventSnap.getValue(User.class);
                    if (usr.getPseudo().equals(Score.getInstance().currUser.getPseudo()))
                        newValue = usr;

                    usrList.add(usr);

                }
                // CASE : YOUR NEXT
                // user has to :
                //      - update the session
                //      - update itself to drawing
                //      - updates all users to watching
                if (usrList.size() >= 2 && usrList.get(nextToDraw).getPseudo().equals(Score.getInstance().currUser.getPseudo())
                        && Score.getInstance().currUser.getState().equals("ready")){
                    for (User u : usrList) {
                        if (!u.getPseudo().equals(Score.getInstance().currUser.getPseudo())) {
                            u.setState("watching");
                            ref.child("users").child(u.getPseudo()).setValue(u);
                        }
                    }
                    Score.getInstance().currUser.setState("drawing");
                    ref.child("users").child(Score.getInstance().currUser.getPseudo()).setValue(Score.getInstance().currUser);
                    changeActivity();
                } else {
                    //Log.d("user:::", Score.getInstance().currUser.getPseudo());
                    if (newValue.getState().equals(Score.getInstance().currUser.getState())){
                        Score.getInstance().currUser.setState(newValue.getState());
                        changeActivity();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void changeActivity() {
        // send you to the activity based on your state
        switch (Score.getInstance().currUser.getState()){
            case "drawing" :
                startActivity(new Intent(LobbyActivity.this, MainActivity.class));
                break;
            case "watching" :
                startActivity(new Intent(LobbyActivity.this, GuessActivity.class));
                break;
            case "results" :
                startActivity(new Intent(LobbyActivity.this, ResultActivity.class));
                break;
            case "ready" :
                break;
            default :
                break;
        }
    }

    protected void getNextUserToDraw(){
        ref.child("session").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Session session = dataSnapshot.getValue(Session.class);
                nextToDraw = session.getNextToDraw();
                Log.d("sess", session.getBitmap() + " " + session.getState() + " " + session.getNextToDraw());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}