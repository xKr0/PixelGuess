package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus_pc.drawmyapp.model.User;

import java.util.ArrayList;

public class ResultActivity extends DeleteOnDestroyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("Results");

        // show a list of player and their points
        showResultsList();
        // set next player to draw if you're the current player
        nextPlayer();

        setIntentArray();
        setNewUserState();

        // on the play again button launch a new round
        Button playAgain = findViewById(R.id.playAgainButton);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();
            }
        });
    }

    private void showResultsList(){
        ListView resultsList = findViewById(R.id.resultsList);

        updateUsersList();

        ArrayList<String> stringResultList = new ArrayList<>();
        for (User u : PartyManager.getInstance().usrList) {
            stringResultList.add(u.toString());
        }

        final ArrayAdapter adapter = new ArrayAdapter<>(ResultActivity.this,
                android.R.layout.simple_list_item_1, stringResultList);
        resultsList.setAdapter(adapter);
    }

    private void setNewUserState(){
        if (PartyManager.getInstance().next.equals(PartyManager.getInstance().currUser.getPseudo())){
            PartyManager.getInstance().currUser.setState("drawing");
        }
        else {
            PartyManager.getInstance().currUser.setState("watching");
        }

        updateUserStateInDatabase();
    }

    public void nextPlayer() {
        // if we're in state=result && you're the drawing player
        if (PartyManager.getInstance().state.equals("result") && PartyManager.getInstance().currUser.getPseudo().equals(PartyManager.getInstance().next)) {

            PartyManager.getInstance().SetNextToDraw();
            ref.child("session").child("next").setValue(PartyManager.getInstance().next);

            PartyManager.getInstance().state = "ready";
            ref.child("session").child("state").setValue("ready");
        }
    }

    protected void setIntentArray(){
        intentArray = new Intent[3];
        intentArray[0] = new Intent(ResultActivity.this, MainActivity.class);
        intentArray[1] = new Intent(ResultActivity.this, GuessActivity.class);
        intentArray[2] = new Intent(ResultActivity.this, ResultActivity.class);
    }

    // Consume the event to not go back to the drawing activity
    @Override
    public void onBackPressed() {
        return;
    }
}
