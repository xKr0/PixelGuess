package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;

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

        getNextUserToDraw();
        startParty();
    }

    protected void startParty(){

        ref.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PartyManager.getInstance().usrList.clear();

                Iterable<DataSnapshot> eventIterable = dataSnapshot.getChildren();
                User newValue = null;
                for(DataSnapshot eventSnap : eventIterable) {
                    User usr = eventSnap.getValue(User.class);
                    if (usr.getPseudo().equals(PartyManager.getInstance().currUser.getPseudo()))
                        newValue = usr;

                    PartyManager.getInstance().usrList.add(usr);
                }
                // CASE : YOUR NEXT
                // user has to :
                //      - update the session
                //      - update itself to drawing
                //      - updates all users to watching
                if (PartyManager.getInstance().usrList.size() >= 2 && next.equals(PartyManager.getInstance().currUser.getPseudo())
                        && PartyManager.getInstance().currUser.getState().equals("ready") && !state.equals("")
                        && (!state.equals("in_progress") || !state.equals("result"))){
                    for (User u : PartyManager.getInstance().usrList) {
                        if (!u.getPseudo().equals(PartyManager.getInstance().currUser.getPseudo())) {
                            u.setState("watching");
                            ref.child("users").child(u.getPseudo()).setValue(u);
                        }
                    }
                    PartyManager.getInstance().currUser.setState("drawing");
                    ref.child("users").child(PartyManager.getInstance().currUser.getPseudo()).setValue(PartyManager.getInstance().currUser);

                    // we change the state of the game play
                    ref.child("session").child("state").setValue("in_progress");

                    changeActivity();
                } else {
                    if (!newValue.getState().equals(PartyManager.getInstance().currUser.getState())){
                        PartyManager.getInstance().currUser.setState(newValue.getState());
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
        switch (PartyManager.getInstance().currUser.getState()){
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Session session = dataSnapshot.getValue(Session.class);
                next = session.getNext();
                state = session.getState();
                PartyManager.getInstance().UpdateImageView(session.getBitmap());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
