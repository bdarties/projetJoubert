package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class anglais extends AppCompatActivity {
    TextView titreAng;
    Button commencer;
    TextView timer;
    TextView record;
    TextView score;
    CountDownTimer countdown;
    long tempsrestant = 120000; // 2 min
    TextView question;
    Button reponse1,reponse2,reponse3,reponse4;
    int nbrecord =0;
    int nbscore=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anglais);
        reponse1=findViewById(R.id.reponse1);
        reponse2=findViewById(R.id.reponse2);
        reponse3=findViewById(R.id.reponse3);
        reponse4=findViewById(R.id.reponse4);
        question=findViewById(R.id.question);
        record=findViewById(R.id.record);
        score=findViewById(R.id.score);
        timer=findViewById(R.id.timer);
        commencer=findViewById(R.id.commencer);
        titreAng=findViewById(R.id.titreAng);

        reponse1.setVisibility(View.INVISIBLE);
        reponse2.setVisibility(View.INVISIBLE);
        reponse3.setVisibility(View.INVISIBLE);
        reponse4.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        setRecord();
        commencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                afficherQuestion();
            }
        });

    }
void reponse (){
        countdown.cancel();
        setScore();
        setRecord();

}
void startTimer(){
    countdown = new CountDownTimer(tempsrestant, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tempsrestant=millisUntilFinished;
            updateTimer();
        }

        @Override
        public void onFinish() {

        }
    }.start();
    commencer.setText("PAUSE");
}
void setScore (){
        nbscore += (int) (10-tempsrestant);
        score.setText("Score actuel : "+ nbscore);
}

void updateTimer(){
    int minutes = (int) tempsrestant/60000;
    int secondes = (int) tempsrestant % 60000/1000;
    String tempsRestant;
    tempsRestant = "" + minutes;
    tempsRestant += ":";
    if (secondes < 10) tempsRestant += "0";
    tempsRestant += secondes;
    timer.setText(tempsRestant);
}
void afficherQuestion (){
    commencer.setVisibility(View.INVISIBLE);
    titreAng.setVisibility(View.INVISIBLE);
    reponse1.setVisibility(View.VISIBLE);
    reponse2.setVisibility(View.VISIBLE);
    reponse3.setVisibility(View.VISIBLE);
    reponse4.setVisibility(View.VISIBLE);
    question.setVisibility(View.VISIBLE);
}
public void setRecord() {
    SharedPreferences sp = getSharedPreferences("your_prefs", anglais.MODE_PRIVATE);
    nbrecord = sp.getInt("nbrecord", -1);
    if (nbscore>nbrecord) {
        nbrecord=nbscore;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("nbrecord", nbrecord);
        editor.commit();}
    record.setText("Votre record actuel est : " + nbrecord);
    }
}