package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


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
    int nbaleatoire=0; // à changer pour la taille de la bdd
    private ArrayList<Integer> nombres=new ArrayList<Integer>();


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
        setTableau(); // rentre les nombres pour la taille de la bdd dans un tableau
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
void setScore (){ // détermine le score en temps réel
        nbscore += (int) (10-tempsrestant);
        score.setText("Score actuel : "+ nbscore);
    }
    // partie compte à rebourd
void startTimer(){ // démarre le compte a rebourd
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

void updateTimer()
{       // mise à jour du timer
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
        // affiche les questions tour à tour
    commencer.setVisibility(View.INVISIBLE);
    titreAng.setVisibility(View.INVISIBLE);
    for (int i=0;i<10;i++) {
        question.setVisibility(View.VISIBLE);
        question.setText(getPif()); // est censé choisir aléatoirement une question puis à faire les questions
        reponse1.setVisibility(View.VISIBLE);
        reponse2.setVisibility(View.VISIBLE);
        reponse3.setVisibility(View.VISIBLE);
        reponse4.setVisibility(View.VISIBLE);
    }
}
public void setRecord() {
        // vérifie si le record est battu et le change si oui
    SharedPreferences sp = getSharedPreferences("your_prefs", anglais.MODE_PRIVATE);
    nbrecord = sp.getInt("nbrecord", -1);
    if (nbscore>nbrecord) {
        nbrecord=nbscore;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("nbrecord", nbrecord);
        editor.commit();}
    record.setText("Votre record actuel est : " + nbrecord);
    }
// partie tirage nb aléatoire sans repetition
private void setTableau()
{   // On rempli le tableau "nombres" de 1 à nb
    for(int i=0;i<nbaleatoire;i++) {nombres.add(i);}
}
public Integer getPif()
{
    if(nombres.size()==0) {setTableau();} // (3)
    int i=pif(0,nombres.size()-1); // (1)
    int retour=nombres.get(i);
    nombres.remove(i); // (2)
    return retour;
}
// Tirage au sort
// ENTREE : la valeur min (ex : 0) et la valeur max (ex : 63)
// SORTIE : un nombre entier compris entre min et max
public static int pif(int min,int max)
{
    Random rand=new Random();
    return rand.nextInt((max - min) + 1) + min;
}



}