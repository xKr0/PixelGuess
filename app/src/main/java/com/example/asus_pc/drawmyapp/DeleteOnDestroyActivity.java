package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.example.asus_pc.drawmyapp.model.Session;
import com.example.asus_pc.drawmyapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteOnDestroyActivity extends AppCompatActivity
{
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Intent intentArray[];

    protected void setIntentArray(){}

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (PartyManager.getInstance().currUser != null){
            ref.child("users").child(PartyManager.getInstance().currUser.getPseudo()).removeValue();
        }
    }

    protected void addUserToDatabase(String pseudo){
        PartyManager.getInstance().currUser = new User(pseudo, "ready");

        // we change the user to play
        ref.child("session").child("next").setValue(pseudo);

        // we also had the user into the database Firebase
        ref.child("users").child(PartyManager.getInstance().currUser.getPseudo()).setValue(PartyManager.getInstance().currUser);
    }

    protected void setAnswerInDatabase(){
        ref.child("session").child("answer").setValue(PartyManager.getInstance().answer);
    }

    protected void updateUserScoreInDatabase(){
        ref.child("users").child(PartyManager.getInstance().currUser.getPseudo()).child("score").setValue(PartyManager.getInstance().currUser.getScore());
    }

    protected void updateUserStateInDatabase(){
        ref.child("users").child(PartyManager.getInstance().currUser.getPseudo()).child("state").setValue(PartyManager.getInstance().currUser.getState());
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
                if (PartyManager.getInstance().usrList.size() >= 2 && PartyManager.getInstance().next.equals(PartyManager.getInstance().currUser.getPseudo())
                        && PartyManager.getInstance().currUser.getState().equals("ready") && !PartyManager.getInstance().state.equals("")
                        && (!PartyManager.getInstance().state.equals("in_progress") || !PartyManager.getInstance().state.equals("result"))){

                    // we change the state of the game play
                    ref.child("session").child("state").setValue("in_progress");

                    for (User u : PartyManager.getInstance().usrList) {
                        if (!u.getPseudo().equals(PartyManager.getInstance().currUser.getPseudo())) {
                            u.setState("watching");
                            ref.child("users").child(u.getPseudo()).setValue(u);
                        }
                    }
                    PartyManager.getInstance().currUser.setState("drawing");
                    ref.child("users").child(PartyManager.getInstance().currUser.getPseudo()).setValue(PartyManager.getInstance().currUser);
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

    protected void getNextUserToDraw(){
        ref.child("session").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Session session = dataSnapshot.getValue(Session.class);
                PartyManager.getInstance().next = session.getNext();
                PartyManager.getInstance().state = session.getState();
                PartyManager.getInstance().UpdateImageView(session.getBitmap());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    protected void changeActivity() {
        // send you to the activity based on your state
        switch (PartyManager.getInstance().currUser.getState()){
            case "drawing" :
                startActivity(intentArray[0]);
                break;
            case "watching" :
                startActivity(intentArray[1]);
                break;
            case "result" :
                startActivity(intentArray[2]);
                break;
            case "ready" :
                break;
            default :
                break;
        }
    }

    protected void updateUsersList(){

        ref.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PartyManager.getInstance().usrList.clear();

                Iterable<DataSnapshot> eventIterable = dataSnapshot.getChildren();

                for(DataSnapshot eventSnap : eventIterable) {
                    User usr = eventSnap.getValue(User.class);
                    PartyManager.getInstance().usrList.add(usr);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
