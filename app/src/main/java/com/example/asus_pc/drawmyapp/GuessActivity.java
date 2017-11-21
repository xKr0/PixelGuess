package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuessActivity extends DeleteOnDestroyActivity {

    // input where to put the answer
    private EditText answerText;

    // button to validate the answer
    private Button validateAnswer;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        setTitle("Guess what this is ?");

        // get the editText answer
        answerText = findViewById(R.id.answerText);

        Score.getInstance().guessActivity = this;

        // add the imageDraw to the layout
        imageView = new ImageView(this);
        if(getIntent().hasExtra("byteArray")) {
            LinearLayout layout = findViewById(R.id.layout_linear);

            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent()
                            .getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);

            layout.addView(imageView);
        }

        LinearLayout layout = findViewById(R.id.layout_linear);
        layout.addView(imageView);

        validateAnswer = findViewById(R.id.validateAnswer);

        // check on the validate method if the user was right
        validateAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correctAnswer = getIntent().getStringExtra("answer");
                String answer = String.valueOf(answerText.getText());

                correctAnswer = correctAnswer.toLowerCase();
                answer = answer.toLowerCase();

                // we compare the two strings
                boolean right = false;
                if (answer.equals(correctAnswer)) {
                    right = true;
                }

                // change to the finish activity
                Intent intent =  new Intent(GuessActivity.this, ResultActivity.class);
                intent.putExtra("right", right);
                startActivity(intent);
            }
        });

    }

    // Consume the event to not go back to the drawing activity
    @Override
    public void onBackPressed() {
        return;
    }

    public void setImageView(String bmpString) {
        imageView.setImageBitmap(stringToBitMap(bmpString));

        // refresh the view
        imageView.invalidate();
    }

    public Bitmap stringToBitMap(String encodedString){
        try {
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
