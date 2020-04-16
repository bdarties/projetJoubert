package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
    long tempsrestant = 10000; // 10 secondes
    TextView question;
    Button reponse1,reponse2,reponse3,reponse4;
    int nbrecord =0;
    int nbscore=0;
    int nbaleatoire=5; // à changer pour la taille de la bdd
    private ArrayList<Integer> nombres=new ArrayList<Integer>();
    SQLiteDatabase maBaseang;

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

        try {
            maBaseang = openOrCreateDatabase("maBaseDeDonneesQuestion",MODE_PRIVATE,null);
            // on cree la table question si elle n'existait pas
            maBaseang.execSQL("CREATE TABLE IF NOT EXISTS questionA(" +
                    " id INTEGER PRIMARY KEY," +
                    " question text NOT NULL," +
                    " reponse1 text NOT NULL," +
                    " reponse2 text,"+
                    " reponse3 text ,"+
                    " reponse4 text);"
            );
            // on la vide (sinon on recréerait a chaque fois les questions a chaque nouveau lancement)
            maBaseang.execSQL(" delete from questionA where 1;");
            // on la remplit de quelques elements  la table question
            maBaseang.execSQL("insert into questionA (id,question, reponse1, reponse2, reponse3, reponse4) values (1,'What is the opposite of easy ? ', 'Difficult', 'Different', 'Dumb', 'Crazy');");
            maBaseang.execSQL("insert into questionA (id,question, reponse1, reponse2, reponse3, reponse4) values (2,'What is the word for : fleur ?', 'Flower', 'Bathroom','Towel','Tree');");

            Log.i("BDD","Opération réussi");

        } catch (SQLException e) {
            Log.e("execSQL","Erreur SQL : " +e.getMessage());
        }
        // on crée un tableau de string appelé results qui va contenir les questions de la base
        final ArrayList<Question> results = new ArrayList<>();
        try {
            // on execute la requete SQL et on récupère les résultats dans un Cursor c
            Cursor c = maBaseang.rawQuery("Select question from questionA order by id asc;", null);
            // on ajoute chaque ligne du cursor dans le tableau results
            while (c.moveToNext()) {
                String a = c.getString(c.getColumnIndex("question"));
                String b = c.getString(c.getColumnIndex("reponse1"));
                String d = c.getString(c.getColumnIndex("reponse2"));
                String e = c.getString(c.getColumnIndex("reponse3"));
                String f = c.getString(c.getColumnIndex("reponse4"));
                Question q = new Question(a,b,d,e,f);
                results.add(q);
                Log.i("Cursor","Opération réussi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        setRecord();
}
void setScore (){ // détermine le score en temps réel
        nbscore += (int) (tempsrestant/10); // cacul du score en centième de secondes
        score.setText("Score actuel : "+ nbscore);
    }
    // partie compte à rebourd
void startTimer(){ // démarre le compte a rebourd
    countdown = new CountDownTimer(tempsrestant, 10) { // on compte en millièmes de secondes
        @Override
        public void onTick(long millisUntilFinished) {
            tempsrestant=millisUntilFinished;
            updateTimer();
        }
        @Override
        public void onFinish() {
            tempsrestant=0;
            updateTimer();
            reponse();
            tempsrestant=10000;
        }
    }.start();

}

void updateTimer()
{       // mise à jour du timer
    int secondes = (int) tempsrestant /1000;
    int milisec = (int) tempsrestant %1000;
    String tempsRestant;
    tempsRestant =""+secondes;
    tempsRestant+=":"+milisec;
    timer.setText(tempsRestant);
}
void afficherQuestion (){
        // affiche les questions tour à tour
    commencer.setVisibility(View.INVISIBLE);
    titreAng.setVisibility(View.INVISIBLE);
                                               //mettre id pour récup la question
    question.setVisibility(View.VISIBLE);
    reponse1.setVisibility(View.VISIBLE);
    reponse2.setVisibility(View.VISIBLE);
    reponse3.setVisibility(View.VISIBLE);
    reponse4.setVisibility(View.VISIBLE);
    for (final int[] i = {0}; i[0] <10;) {
        int numq=getPif();

        question.setText(""); // est censé choisir aléatoirement une question puis à faire les questions
        reponse1.setText(""); // mettre une reponse en aléatoire du tableau
        reponse2.setText("");
        reponse3.setText("");
        reponse4.setText("");

        reponse1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reponse();
                if (reponse1.toString().equals("") ){ // mettre l'id de la reponse juste
                    reponse1.setBackgroundResource(R.color.vert);
                    setScore();
                    CountDownTimer time = new CountDownTimer(3000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            reponse1.setBackgroundResource(R.color.bleu);
                        }
                    }.start();
                }
                else {reponse1.setBackgroundResource(R.color.rouge);
                    if (reponse2.toString().equals("")) reponse2.setBackgroundResource(R.color.vert);
                    if (reponse3.toString().equals("")) reponse3.setBackgroundResource(R.color.vert);
                    if (reponse4.toString().equals("")) reponse4.setBackgroundResource(R.color.vert);
                CountDownTimer time = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        reponse1.setBackgroundResource(R.color.bleu);
                        reponse2.setBackgroundResource(R.color.bleu);
                        reponse3.setBackgroundResource(R.color.bleu);
                        reponse4.setBackgroundResource(R.color.bleu);
                    }
                }.start();
                }
                tempsrestant=10000;
                i[0]++;
            }});
    reponse2.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            reponse();
            if (reponse2.toString().equals("") ){ // mettre l'id de la reponse juste
                reponse2.setBackgroundResource(R.color.vert);
                setScore();
                CountDownTimer time = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        reponse2.setBackgroundResource(R.color.bleu);
                    }
                }.start();
            }
            else {reponse2.setBackgroundResource(R.color.rouge);
                if (reponse1.toString().equals("")) reponse1.setBackgroundResource(R.color.vert);
                if (reponse3.toString().equals("")) reponse3.setBackgroundResource(R.color.vert);
                if (reponse4.toString().equals("")) reponse4.setBackgroundResource(R.color.vert);
                CountDownTimer time = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        reponse1.setBackgroundResource(R.color.bleu);
                        reponse2.setBackgroundResource(R.color.bleu);
                        reponse3.setBackgroundResource(R.color.bleu);
                        reponse4.setBackgroundResource(R.color.bleu);
                    }
                }.start();
            }
            tempsrestant=10000;
            i[0]++;
        }});
    reponse3.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            reponse();
            if (reponse3.toString().equals("") ){ // mettre l'id de la reponse juste
                reponse3.setBackgroundResource(R.color.vert);
                setScore();
                CountDownTimer time = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        reponse3.setBackgroundResource(R.color.bleu);
                    }
                }.start();
            }
            else {reponse3.setBackgroundResource(R.color.rouge);
                if (reponse2.toString().equals("reponse2")) reponse2.setBackgroundResource(R.color.vert);
                if (reponse1.toString().equals("")) reponse1.setBackgroundResource(R.color.vert);
                if (reponse4.toString().equals("")) reponse4.setBackgroundResource(R.color.vert);
                CountDownTimer time = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        reponse1.setBackgroundResource(R.color.bleu);
                        reponse2.setBackgroundResource(R.color.bleu);
                        reponse3.setBackgroundResource(R.color.bleu);
                        reponse4.setBackgroundResource(R.color.bleu);
                    }
                }.start();
            }
            tempsrestant=10000;
            i[0]++;
        }});
    reponse4.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            reponse();
            if (reponse4.toString().equals("") ){ // mettre l'id de la reponse juste
                reponse4.setBackgroundResource(R.color.vert);
                setScore();
                CountDownTimer time = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        reponse4.setBackgroundResource(R.color.bleu);
                    }
                }.start();
            }
            else {reponse4.setBackgroundResource(R.color.rouge);
                if (reponse2.toString().equals("")) reponse2.setBackgroundResource(R.color.vert);
                if (reponse3.toString().equals("")) reponse3.setBackgroundResource(R.color.vert);
                if (reponse1.toString().equals("")) reponse1.setBackgroundResource(R.color.vert);
                CountDownTimer time = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        reponse1.setBackgroundResource(R.color.bleu);
                        reponse2.setBackgroundResource(R.color.bleu);
                        reponse3.setBackgroundResource(R.color.bleu);
                        reponse4.setBackgroundResource(R.color.bleu);
                    }
                }.start();
            }
            tempsrestant=10000;
            i[0]++;
        }});

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
    for(int i=1;i<=nbaleatoire;i++) {nombres.add(i);}
}
public Integer getPif()
{
    if(nombres.size()==0) {setTableau();} // si le tableau de nombre est vide on le réinitialise
    int i=pif(1,nombres.size());
    int retour=nombres.get(i);
    nombres.remove(i);
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