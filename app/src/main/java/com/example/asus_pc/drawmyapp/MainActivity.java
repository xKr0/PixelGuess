package com.example.asus_pc.drawmyapp;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.asus_pc.drawmyapp.model.User;

public class MainActivity extends DeleteOnDestroyActivity implements View.OnClickListener
{
    // custom instance in view
    private DrawingView drawView;
    // paint color button in the palette, drawing button, erase button, new button
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn;

    // brush sizes
    private float smallBrush, mediumBrush, largeBrush;

    // end time
    int timeMax = 20000;

    // display of the timer
    private TextView timerText;

    private String wordToGuess;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Ready? Draw!");

        setIntentArray();

        drawView = findViewById(R.id.drawing);

        // retrieve the first paint color button in the palette area
        LinearLayout paintLayout = findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);

        // to show the selected color paint
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        //currPaint.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.paint_pressed));

        timerText = findViewById(R.id.timer_text);

        // instanciate brushes
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        drawBtn = findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);

        // set brush size
        drawView.setBrushSize(mediumBrush);

        eraseBtn = findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        newBtn = findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        showInitDialog();
    }

    private void handleTimer(){
        new CountDownTimer(timeMax, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText(millisUntilFinished / 1000 + " secondes restantes");
                //here you can have your logic to set text to edittext

                // update the image online
                drawView.updateOnline();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            public void onFinish() {
                timerText.setText("Done!");

                // create a alert
                // verifying the user mean to restart
                AlertDialog.Builder newDialog = new AlertDialog.Builder(MainActivity.this);
                newDialog.setTitle("Time Out");
                newDialog.setMessage("Now it's time to see the result?");
                newDialog.setPositiveButton("Ready", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });

                newDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        // we change the state of the game play
                        ref.child("session").child("state").setValue("result");
                        PartyManager.getInstance().state = "result";

                        for (User u : PartyManager.getInstance().usrList) {
                            if (!u.getPseudo().equals(PartyManager.getInstance().currUser.getPseudo())) {
                                u.setState("result");
                                ref.child("users").child(u.getPseudo()).setValue(u);
                            }
                        }

                        PartyManager.getInstance().currUser.setState("result");
                        ref.child("users").child(PartyManager.getInstance().currUser.getPseudo()).setValue(PartyManager.getInstance().currUser);
                        changeActivity();
                    }
                });
                newDialog.show();
            }

        }.start();
    }

    protected void setIntentArray(){
        intentArray = new Intent[3];
        intentArray[0] = new Intent(MainActivity.this, MainActivity.class);
        intentArray[1] = new Intent(MainActivity.this, GuessActivity.class);
        intentArray[2] = new Intent(MainActivity.this, ResultActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showInitDialog(){
        wordToGuess = GuessWordsList.pichAWord();
        PartyManager.getInstance().answer = wordToGuess;
        setAnswerInDatabase();

        AlertDialog.Builder wordDialog = new AlertDialog.Builder(MainActivity.this);
        wordDialog.setTitle("Your Word");
        wordDialog.setMessage("Draw me a(n) " + wordToGuess + " please.");
        wordDialog.setPositiveButton("Draw", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });
        wordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                handleTimer();
            }
        });
        wordDialog.show();
    }

    @Override
    public void onClick(View view){
        // draw button clicked
        if(view.getId()==R.id.draw_btn){
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush size:");

            // set layout
            brushDialog.setContentView(R.layout.brush_chooser);

            // listen for clicks on the three size buttons
            ImageButton smallBtn = brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    setBrushSize(smallBrush, smallBrush);
                    brushDialog.dismiss();
                }
            });

            ImageButton mediumBtn = brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    setBrushSize(mediumBrush, mediumBrush);
                    brushDialog.dismiss();
                }
            });

            ImageButton largeBtn = brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    setBrushSize(largeBrush, largeBrush);
                    brushDialog.dismiss();
                }
            });
            // show dialog
            brushDialog.show();
        }
        // erase button clicked
        else if(view.getId()==R.id.erase_btn){
            //switch to erase - choose size
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Eraser size:");
            brushDialog.setContentView(R.layout.brush_chooser);

            // to choose the size of the erase brush
            ImageButton smallBtn = brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    setEraseSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    setEraseSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    setEraseSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();
        }
        // new button clicked
        else if(view.getId()==R.id.new_btn){
            // verifying the user mean to restart
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
    }

    public void setBrushSize(float newSize, float oldSize) {
        drawView.setBrushSize(newSize);
        drawView.setLastBrushSize(oldSize);
        drawView.setErase(false);
    }

    public void setEraseSize(float newSize) {
        drawView.setErase(true);
        drawView.setBrushSize(newSize);
    }

    public void paintClicked(View view){
        // switch back to drawing
        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());

        // if chose color != selected color
        if(view!=currPaint){
            // get the color's tag
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();

            // set to the new color
            drawView.setColor(color);

            // change appearances of the new and old color in view
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }
    }

    // Consume the event to not go back to the drawing activity
    @Override
    public void onBackPressed() {
        return;
    }
}
