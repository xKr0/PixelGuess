package com.example.asus_pc.drawmyapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus_pc.drawmyapp.model.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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

        PartyManager.getInstance().guessActivity = this;

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

        getAnswerFromDatabase();

        // check on the validate method if the user was right
        validateAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserScore();
                // disable the user input (because he only got one try)
                validateAnswer.setEnabled(false);
                answerText.setEnabled(false);
            }
        });

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

    private void getAnswerFromDatabase(){
        ref.child("session").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Session session = dataSnapshot.getValue(Session.class);
                PartyManager.getInstance().answer = session.getAnswer();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void updateUserScore(){
        String answerUsr = String.valueOf(answerText.getText());
        answerUsr = answerUsr.toLowerCase();

        // we compare the two strings
        if (answerUsr.equals(PartyManager.getInstance().answer)) {
            // add 1 to currUser score
            PartyManager.getInstance().currUser.incrementScore();

            //Log.d("if_loop::", "right answer");
            updateUserScoreInDatabase();
        }
    }

    // Consume the event to not go back to the drawing activity
    @Override
    public void onBackPressed() {
        return;
    }
}
