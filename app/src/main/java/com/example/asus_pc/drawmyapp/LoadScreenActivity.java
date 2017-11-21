package com.example.asus_pc.drawmyapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoadScreenActivity extends DeleteOnDestroyActivity
{
    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        // get the array of words
        GuessWordsList.setWordsList(getResources().getStringArray(R.array.wordsArray));

        play = findViewById(R.id.play_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPseudoDialog();
            }
        });
    }

    // choose a pseudo and put it in firebase database
    private void showPseudoDialog(){
        final AlertDialog.Builder pseudoDialog = new AlertDialog.Builder(LoadScreenActivity.this);
        pseudoDialog.setTitle("Choose your pseudo");

        // pseudo editable
        final EditText pseudo = new EditText(LoadScreenActivity.this);
        pseudo.setHint("Your Artist Name");
        pseudoDialog.setView(pseudo);


        pseudoDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

                if (!pseudo.getText().toString().equals("")) {
                    // add user to firebase
                    addUserToDatabase(pseudo.getText().toString());

                    dialog.dismiss();
                    startActivity(new Intent(LoadScreenActivity.this, LobbyActivity.class));
                }
                else {
                    handleEmptyPseudo();
                }
            }
        });
        pseudoDialog.show();
    }

    // show new dialog if you tried to enter an empty pseudo
    private void handleEmptyPseudo(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(LoadScreenActivity.this);
        alert.setTitle("Wrong");
        alert.setMessage("You have entered an empty pseudo.\nPlease retry.");

        alert.setPositiveButton("Retry", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
                showPseudoDialog();
            }
        });
        alert.show();
    }
}
