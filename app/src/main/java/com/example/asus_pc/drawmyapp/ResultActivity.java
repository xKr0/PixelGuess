package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends DeleteOnDestroyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        boolean right = getIntent().getBooleanExtra("right", false);

        // display if the player won or not
        TextView result = findViewById(R.id.resultText);
        if (right) {
            result.setText("You got it right !!!");

            // inc the player score
            Score.getInstance().incScore();
        } else {
            result.setText("Wrong answer, you will do better nex Time!");
        }

        // display the two player scores
        TextView scoreTextPlayer1 = findViewById(R.id.scorePlayer1);
        scoreTextPlayer1.setText("Player 1 : " + Score.getInstance().getScorePlayer1());

        TextView scoreTextPlayer2 = findViewById(R.id.scorePlayer2);
        scoreTextPlayer2.setText("Player 2 :" + Score.getInstance().getScorePlayer2());

        // on the play again button launch a new round
        Button playAgain = findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass to the new player
                Score.getInstance().nextPlayer();

                // go to the MainActivity
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Consume the event to not go back to the drawing activity
    @Override
    public void onBackPressed() {
        return;
    }
}
