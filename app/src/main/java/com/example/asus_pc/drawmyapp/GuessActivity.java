package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayInputStream;
import java.util.Base64;

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

        // add the imageDraw to the layout

        if(getIntent().hasExtra("byteArray")) {
            LinearLayout layout = findViewById(R.id.layout_linear);
            imageView = new ImageView(this);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setImageView(String bmpString) {

        imageView = new ImageView(this);

        byte[] byteArray = Base64.getDecoder().decode(bmpString);

        Bitmap b = BitmapFactory.decodeByteArray(
                byteArray,0, byteArray.length);
        imageView.setImageBitmap(b);

        // refresh the view
        imageView.invalidate();
    }
}
