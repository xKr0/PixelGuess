package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoadScreenActivity extends AppCompatActivity
{
    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        play = (Button)findViewById(R.id.play_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoadScreenActivity.this, MainActivity.class));
            }
        });
    }
}