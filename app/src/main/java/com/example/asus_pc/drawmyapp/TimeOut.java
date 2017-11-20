package com.example.asus_pc.drawmyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.TimerTask;

public class TimeOut extends TimerTask {

    Context context;
    public TimeOut(Context c){
        context = c;
    }

    public void run() {
        // verifying the user mean to restart
        AlertDialog.Builder newDialog = new AlertDialog.Builder(context);
        newDialog.setTitle("Time Out");
        newDialog.setMessage("Ready to show your art?");
        newDialog.setPositiveButton("Ready", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });
        newDialog.show();
    }
}
