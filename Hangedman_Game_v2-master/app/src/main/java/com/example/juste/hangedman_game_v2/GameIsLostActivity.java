package com.example.juste.hangedman_game_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameIsLostActivity extends hangedmanGame implements View.OnClickListener{
    private TextView gameIsLostText;
    private Button returnToMain;
    String word;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_is_lost);
        word = getIntent().getStringExtra("word");
        score = getIntent().getIntExtra("score", 0);
        gameIsLostText = (TextView) findViewById(R.id.LostGameText);
        returnToMain = (Button) findViewById(R.id.ReturnToMain);
        returnToMain.setOnClickListener(this);
        gameIsLostText.setText("You have failed to guess the word: " + word + " and lost." +
                " Your total score is " + score +
                " press the button to return to the main menu");

    }

    public void onClick(View v){
        if(v==returnToMain) {
            Intent backToMain = new Intent(this, MainActivity.class);
            backToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(backToMain);
        }
    }

}
