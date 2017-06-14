package com.example.juste.hangedman_game_v2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.client.Firebase;
import java.util.HashMap;
import java.util.Map;



public class hangedmanGame extends FragmentActivity implements View.OnClickListener, SensorEventListener, SureUnsure_Fragment.SureUnsureListenerDone{

    HangedmanLogic logic = new HangedmanLogic();
    private SensorManager sensorMgr;
    private Sensor sensor;
    private SensorActivity sensorAc;


    private Button buttonA, buttonB, buttonC, buttonD, buttonE, buttonF,buttonG,buttonH, buttonI,buttonJ, buttonK, buttonL,
            buttonM, buttonN, buttonO, buttonP, buttonQ, buttonR, buttonS, buttonT, buttonU, buttonV, buttonW, buttonX,
            buttonY, buttonZ, buttonÆ,buttonØ, buttonÅ;
    private ImageView hangedmanImage;
    private TextView wordToGuess;
    private TextView guesses;
    private String name;
    Firebase myFBRef = new Firebase("https://hangedman-game.firebaseio.com/");
    int nummer;
    String language = "none";
    String[] alfabet = {"a", "b", "c","d", "e","f", "g","h", "i","j", "k","l", "m","n",
            "o","p", "q","r", "s","t", "u","v","w", "x","y", "z", "æ","ø", "å"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_hangedman_game);


        //sensor setup
        sensorMgr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorAc = new SensorActivity();
        sensorAc.setOnShakeListener(new SensorActivity.OnShakeListener() {
            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });


        //setup of bottons for the horizontal scrollview. thought about gallery list but Jacob said it was outdate.
        buttonA = (Button) findViewById(R.id.A);buttonB = (Button) findViewById(R.id.B);
        buttonC = (Button) findViewById(R.id.C);buttonD = (Button) findViewById(R.id.D);
        buttonE = (Button) findViewById(R.id.E);buttonF = (Button) findViewById(R.id.F);
        buttonG = (Button) findViewById(R.id.G);buttonH = (Button) findViewById(R.id.H);
        buttonI = (Button) findViewById(R.id.I);buttonJ = (Button) findViewById(R.id.J);
        buttonK = (Button) findViewById(R.id.K);buttonL = (Button) findViewById(R.id.L);
        buttonM = (Button) findViewById(R.id.M);buttonN = (Button) findViewById(R.id.N);
        buttonO = (Button) findViewById(R.id.O);buttonP = (Button) findViewById(R.id.P);
        buttonQ = (Button) findViewById(R.id.Q);buttonR = (Button) findViewById(R.id.R);
        buttonS = (Button) findViewById(R.id.S);buttonT = (Button) findViewById(R.id.T);
        buttonU = (Button) findViewById(R.id.U);buttonV = (Button) findViewById(R.id.V);
        buttonW = (Button) findViewById(R.id.W);buttonX = (Button) findViewById(R.id.X);
        buttonY = (Button) findViewById(R.id.Y);buttonZ = (Button) findViewById(R.id.Z);
        buttonÆ = (Button) findViewById(R.id.Æ);buttonØ = (Button) findViewById(R.id.Ø);
        buttonÅ = (Button) findViewById(R.id.Å);
        buttonA.setOnClickListener(this); buttonB.setOnClickListener(this); buttonC.setOnClickListener(this);buttonD.setOnClickListener(this); buttonE.setOnClickListener(this);
        buttonF.setOnClickListener(this); buttonG.setOnClickListener(this);buttonH.setOnClickListener(this);buttonI.setOnClickListener(this);buttonJ.setOnClickListener(this);
        buttonK.setOnClickListener(this);buttonL.setOnClickListener(this);buttonM.setOnClickListener(this);buttonN.setOnClickListener(this);buttonO.setOnClickListener(this);
        buttonP.setOnClickListener(this); buttonQ.setOnClickListener(this);buttonR.setOnClickListener(this);buttonS.setOnClickListener(this);buttonT.setOnClickListener(this);
        buttonU.setOnClickListener(this);buttonV.setOnClickListener(this);buttonW.setOnClickListener(this);buttonX.setOnClickListener(this);buttonY.setOnClickListener(this);
        buttonZ.setOnClickListener(this); buttonÆ.setOnClickListener(this);buttonØ.setOnClickListener(this); buttonÅ.setOnClickListener(this);

        hangedmanImage = (ImageView) findViewById(R.id.imageView2);
        guesses = (TextView) findViewById(R.id.LettersUsed);
        wordToGuess = (TextView) findViewById(R.id.WordToGuess);
        wordToGuess.setText(logic.getVisableWord());
        hangedmanImage.setImageResource(R.drawable.galge);
        name = getIntent().getStringExtra("name");
        language = getIntent().getStringExtra("langauge");
        Log.d("String", "langauge ="+language + " name ="+name);
        if (language == null) {
            //If language is null the game will continue with the words stored in the Hangedman Logic.
        }else if(language.equals("english")){
            getWordGuardian();
            wordToGuess.setText(logic.getVisableWord());
        }else if(language.equals("danish")){
            getWordDR();
            wordToGuess.setText(logic.getVisableWord());
        }
    }
    public void Leave(View v){
        SureUnsure_Fragment su = new SureUnsure_Fragment();
        su.show(getFragmentManager(),"sureUnsure");

    }

    private void handleShakeEvent(int count) {
        wordToGuess.setText("Game has been reset you had " + logic.getScore() + " points");
        guesses.setText("Letters Used: ");
        logic.refresh();

    }




    public void runGame(int i){
        String charecter;
        nummer = i;
        Log.d("String", "langauge =" + language + " name =" + name);
        charecter = alfabet[nummer];
        if (logic.getUsedLetter().contains(charecter)) {
            wordToGuess.setText("you have already guessed on this letter");
        } else {
            logic.guessLetter(charecter);
            wordToGuess.setText(logic.getVisableWord());
            if (!logic.isLastLetterCorrect()) {
                guesses.setText(guesses.getText() + " " + charecter);
                logic.minusPoints();
            }
            if (logic.isTheGameWon()) {
                wordToGuess.setText("You have guessed the word: " + logic.getWord() + " and score 100 points, please do continue");
                logic.softReset();
                logic.plusPoints();
                guesses.setText("Letters Used: ");
                hangedmanImage.setImageResource(R.drawable.galge);
            }
            if (logic.isTheGameLost()) {
                pushToFB();
                Intent gameLost = new Intent(this, GameIsLostActivity.class);
                gameLost.putExtra("word", logic.getWord());
                gameLost.putExtra("score", logic.getScore());
                startActivity(gameLost);
            }
            logic.logStatus();

        }
        switch(logic.getNumberOfWrongWords()) {
            case 1:
                hangedmanImage.setImageResource(R.drawable.forkert1);
                break;
            case 2:
                hangedmanImage.setImageResource(R.drawable.forkert2);
                break;
            case 3:
                hangedmanImage.setImageResource(R.drawable.forkert3);
                break;
            case 4:
                hangedmanImage.setImageResource(R.drawable.forkert4);
                break;
            case 5:
                hangedmanImage.setImageResource(R.drawable.forkert5);
                break;
            case 6:
                hangedmanImage.setImageResource(R.drawable.forkert6);
                break;

        }

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.A: runGame(0);
                break;
            case R.id.B: runGame(1);
                break;
            case R.id.C: runGame(2);
                break;
            case R.id.D: runGame(3);
                break;
            case R.id.E: runGame(4);
                break;
            case R.id.F: runGame(5);
                break;
            case R.id.G: runGame(6);
                break;
            case R.id.H: runGame(7);
                break;
            case R.id.I: runGame(8);
                break;
            case R.id.J: runGame(9);
                break;
            case R.id.K: runGame(10);
                break;
            case R.id.L: runGame(11);
                break;
            case R.id.M: runGame(12);
                break;
            case R.id.N: runGame(13);
                break;
            case R.id.O: runGame(14);
                break;
            case R.id.P: runGame(15);
                break;
            case R.id.Q: runGame(16);
                break;
            case R.id.R: runGame(17);
                break;
            case R.id.S: runGame(18);
                break;
            case R.id.T: runGame(19);
                break;
            case R.id.U: runGame(20);
                break;
            case R.id.V: runGame(21);
                break;
            case R.id.W: runGame(22);
                break;
            case R.id.X: runGame(23);
                break;
            case R.id.Y: runGame(24);
                break;
            case R.id.Z: runGame(25);
                break;
            case R.id.Æ: runGame(26);
                break;
            case R.id.Ø: runGame(27);
                break;
            case R.id.Å: runGame(28);
                break;
        }
    }

    public void pushToFB(){
        String push = "" + logic.getScore();
        Map<String, String> highscore = new HashMap<>();
        highscore.put("score", push);
        highscore.put("name", name);
        myFBRef.push().setValue(highscore);
    }

    public void getWordDR ()  {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    logic.getWordFromDR();
                    return "Words picked up properly from DR";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Words are not picked up properly from DR: " + e;
                }
            }

            @Override
            protected void onPostExecute(Object resultat) {
                Log.d("fra DR", "resultat: \n" + resultat);
            }



        }.execute();

    }

    public void getWordGuardian ()  {

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    logic.getWordFromTimes();
                    return "Words picked up properly from Times";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Words are not picked up properly from Times: " + e;
                }
            }


            @Override
            protected void onPostExecute(Object resultat) {
                Log.d("from Guardian", "resultat: \n" + resultat);
            }



        }.execute();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorMgr.registerListener(sensorAc, sensor,SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorMgr.unregisterListener(sensorAc);
    }

    @Override
    public void onComplete(String word) {
        if(word.equals("Yes")){
            pushToFB();
            onBackPressed();
        }
        //if the word isn't equal to yes then the program return to the game
    }
}
