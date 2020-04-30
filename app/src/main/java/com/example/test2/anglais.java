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
    Button reponse1, reponse2, reponse3, reponse4, questionsuivante;
    int nbrecord = 0;
    int nbscore = 0, nbreponses = 4;
    int taillebdd = 5; // à changer pour la taille de la bdd
    private ArrayList<Integer> indicesDesQuestionsAPoser = new ArrayList<Integer>();
    private ArrayList<Integer> indicesDesReponsesAAfficher = new ArrayList<Integer>();
    SQLiteDatabase maBaseang;
    ArrayList<Question> tableauDeTouteslesQuestions;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anglais);
        // appelation des composants
        reponse1 = findViewById(R.id.reponse1);
        reponse2 = findViewById(R.id.reponse2);
        reponse3 = findViewById(R.id.reponse3);
        reponse4 = findViewById(R.id.reponse4);
        question = findViewById(R.id.question);
        record = findViewById(R.id.record);
        score = findViewById(R.id.score);
        timer = findViewById(R.id.timer);
        commencer = findViewById(R.id.commencer);
        titreAng = findViewById(R.id.titreAng);
        questionsuivante = findViewById(R.id.questionsuivante);
        // definition de la visibilité des boutons non utiles au debut
        reponse1.setVisibility(View.INVISIBLE);
        reponse2.setVisibility(View.INVISIBLE);
        reponse3.setVisibility(View.INVISIBLE);
        reponse4.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        questionsuivante.setVisibility(View.INVISIBLE);
        setRecord();


        // on remplit le tableau des indices des questions à poser
        remplirTableauDeValeurs(indicesDesQuestionsAPoser, taillebdd);


        try {
            maBaseang = openOrCreateDatabase("maBaseDeDonneesQuestion", MODE_PRIVATE, null);
            // on cree la table question si elle n'existait pas
            maBaseang.execSQL("CREATE TABLE IF NOT EXISTS questionA(" +
                    " id INTEGER PRIMARY KEY," +
                    " question text NOT NULL," +
                    " reponse1 text NOT NULL," +
                    " reponse2 text," +
                    " reponse3 text ," +
                    " reponse4 text);"
            );
            // on la vide (sinon on recréerait a chaque fois les questions a chaque nouveau lancement)
            maBaseang.execSQL(" delete from questionA where 1;");
            // on la remplit de quelques elements  la table question
            maBaseang.execSQL("insert into questionA (id,question, reponse1, reponse2, reponse3, reponse4) values (1,'What is the opposite of easy ? ', 'Difficult', 'Different', 'Dumb', 'Crazy');");
            maBaseang.execSQL("insert into questionA (id,question, reponse1, reponse2, reponse3, reponse4) values (2,'What is the word for : fleur ?', 'Flower', 'Bathroom','Towel','Tree');");
            maBaseang.execSQL("insert into questionA (id,question, reponse1, reponse2, reponse3, reponse4) values (3,'What is the opposite of easy ? ', 'Difficult', 'Different', 'Dumb', 'Crazy');");
            maBaseang.execSQL("insert into questionA (id,question, reponse1, reponse2, reponse3, reponse4) values (4,'What is the opposite of easy ? ', 'Difficult', 'Different', 'Dumb', 'Crazy');");
            maBaseang.execSQL("insert into questionA (id,question, reponse1, reponse2, reponse3, reponse4) values (5,'What is the opposite of easy ? ', 'Difficult', 'Different', 'Dumb', 'Crazy');");


            Log.i("BDD", "Opération réussi");

        } catch (SQLException e) {
            Log.e("execSQL", "Erreur SQL : " + e.getMessage());
        }
        // on crée un tableau de string appelé results qui va contenir les questions de la base sous forme d'objets de type Question
        tableauDeTouteslesQuestions = new ArrayList<>();
        try {
            String tampon = "bonne réponse";
            // on execute la requete SQL et on récupère les résultats dans un Cursor c
            Cursor c = maBaseang.rawQuery("Select * from questionA order by id asc;", null);
            // BD: il faut selectionenr tous les champs, j'ai donc remplacé par *
            // on ajoute chaque ligne du cursor dans le tableau results
            while (c.moveToNext()) {
                String a = c.getString(c.getColumnIndex("question"));
                String b = c.getString(c.getColumnIndex("reponse1"));
                String d = c.getString(c.getColumnIndex("reponse2"));
                String e = c.getString(c.getColumnIndex("reponse3"));
                String f = c.getString(c.getColumnIndex("reponse4"));
                Question q = new Question(a, b, d, e, f);
                Log.i("message", "" + q);
                tableauDeTouteslesQuestions.add(q);

                Log.i("Cursor", "Opération réussi");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        commencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                // On récupère une question alétoire, puis on l'affiche
                Question questionTireeAuSort = obtenirQuestionAleatoire();
                afficherQuestion(questionTireeAuSort);
            }
        });
        questionsuivante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indicesDesQuestionsAPoser.size() > 0) { // il reste des questions
                    Log.i ("questions", "il reste "+ indicesDesQuestionsAPoser.size()+ " dans la base");
                    Question questionTireeAuSort = obtenirQuestionAleatoire();
                    afficherQuestion(questionTireeAuSort);

                    questionsuivante.setVisibility(View.INVISIBLE);
                } else { // mettre pour quand la partie est finie

                }
            }
        });
    }


    /*
    * cette méthode retouurne une question aléatoire
    * elle est à appeler a chaque fois que l'on veut afficher une nouvelle questions
    * */
    Question obtenirQuestionAleatoire ( ) {
        Question questionRetournée =  tableauDeTouteslesQuestions.get(valeurAuPifDuTableau(indicesDesQuestionsAPoser));
        Log.i("question selelectionnee", questionRetournée.getQuestion());
        return questionRetournée;
    }


    void reponse() {
        countdown.cancel();
        setRecord();
    }

    void setScore() { // détermine le score en temps réel
        nbscore += (int) (tempsrestant / 10); // cacul du score en centième de secondes
        score.setText("Score actuel : " + nbscore);
    }

    // partie compte à rebourd
    void startTimer() { // démarre le compte a rebourd
        countdown = new CountDownTimer(tempsrestant, 10) { // on compte en millièmes de secondes
            @Override
            public void onTick(long millisUntilFinished) {
                tempsrestant = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                if (tempsrestant < 15) {// milisecondes : temps fini
                    // partie pour si aucune réponse selectionnée
                    CountDownTimer time = new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            tempsrestant = 0;
                            desactivebouton();
                        }

                        @Override
                        public void onFinish() {
                            reactiverBouton();
                            questionsuivante.setVisibility(View.VISIBLE);
                        }

                    }.start();
                }
            }
        }.start();

    }

    void updateTimer() {       // mise à jour du timer
        int secondes = (int) tempsrestant / 1000;
        int milisec = (int) tempsrestant % 1000;
        String tempsRestant;
        tempsRestant = "" + secondes;
        tempsRestant += ":" + milisec;
        timer.setText(tempsRestant);
    }

    void afficherQuestion(final Question result) {
        // affiche les questions tour à tour
        commencer.setVisibility(View.INVISIBLE);
        titreAng.setVisibility(View.INVISIBLE);
        question.setVisibility(View.VISIBLE);
        reponse1.setVisibility(View.VISIBLE);
        reponse2.setVisibility(View.VISIBLE);
        reponse3.setVisibility(View.VISIBLE);
        reponse4.setVisibility(View.VISIBLE);

        question.setText(result.getQuestion()); // affiche la question choisie aléatoirement
        final String tampon = result.getReponse1(); // variable tampon prenant la bonne réponse de la question
        btnReponse(result);
        Log.i("reponse", "la bonne réponse est "+ tampon);
        reponse1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reponse();
                if (reponse1.getText().toString().equals(tampon)) {// mettre l'id de la reponse juste

                    reponse1.setBackgroundResource(R.color.vert);
                    setScore();
                } else {
                    reponse1.setBackgroundResource(R.color.rouge);
                    bonnereponse(tampon);
                }
                tempsrestant = 10000;
                CountDownTimer time = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        desactivebouton();
                    }

                    @Override
                    public void onFinish() {
                        reactiverBouton();
                        questionsuivante.setVisibility(View.VISIBLE);
                    }

                }.start();
            }
        });

        reponse2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reponse();
                if (reponse2.getText().toString().equals(tampon)) { // mettre l'id de la reponse juste
                    reponse2.setBackgroundResource(R.color.vert);
                    setScore();
                } else {
                    reponse2.setBackgroundResource(R.color.rouge);
                    bonnereponse(tampon);
                }
                CountDownTimer time = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        desactivebouton();
                    }

                    @Override
                    public void onFinish() {
                        reactiverBouton();
                        questionsuivante.setVisibility(View.VISIBLE);
                    }
                }.start();

                tempsrestant = 10000;
            }
        });
        reponse3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reponse();
                if (reponse3.getText().toString().equals(tampon)) { // mettre l'id de la reponse juste
                    reponse3.setBackgroundResource(R.color.vert);
                    setScore();

                } else {
                    reponse3.setBackgroundResource(R.color.rouge);
                }
                    bonnereponse(tampon);
                    CountDownTimer time = new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            desactivebouton();
                        }

                        @Override
                        public void onFinish() {
                            reactiverBouton();
                            questionsuivante.setVisibility(View.VISIBLE);
                        }
                    }.start();

                tempsrestant = 10000;
            }
        });
        reponse4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reponse();
                if (reponse4.getText().toString().equals(tampon)) { // mettre l'id de la reponse juste
                    reponse4.setBackgroundResource(R.color.vert);
                    setScore();

                } else {
                    reponse4.setBackgroundResource(R.color.rouge);
                }
                    bonnereponse(tampon);
                    CountDownTimer time = new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            desactivebouton();
                        }

                        @Override
                        public void onFinish() {
                            reactiverBouton();
                            questionsuivante.setVisibility(View.VISIBLE);
                        }
                    }.start();

                tempsrestant = 10000;
            }
        });

    }

    public void setRecord() {
        // vérifie si le record est battu et le change si oui
        SharedPreferences sp = getSharedPreferences("your_prefs", anglais.MODE_PRIVATE);
        nbrecord = sp.getInt("nbrecord", -1);
        if (nbscore > nbrecord) {
            nbrecord = nbscore;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("nbrecord", nbrecord);
            editor.commit();
        }
        record.setText("Votre record actuel est : " + nbrecord);
    }


    // partie tirage nb aléatoire sans repetition
    /*
    Cette méthode permet de remplir les inddices des questions à poser dans le tableau
    il y a autant d'indices que de questions
     */
    private void remplirTableauDesIndicesDeQuestionsAPoser() {   // On rempli le tableau "nombres" de 1 à nb
        for (int i = 0; i < taillebdd; i++) {
            indicesDesQuestionsAPoser.add(i);
        }
    }

    // tirage aléatoire pour les questions
/*
  cette méthode retourne une valeur au pif contenue dans le tableau passé en paramètres, et l'enleve
*/
    public Integer valeurAuPifDuTableau(ArrayList<Integer> tableauParam) {
        int i = pif(0, tableauParam.size() );
        int retour = tableauParam.get(i);
        tableauParam.remove(i);
        return retour;
    }


    /*
      cette méthode rempllit le tableau passé en paramtères de valeurs entre 1 et ValeurMax
    */
    private void remplirTableauDeValeurs(ArrayList<Integer> tableauParam, int valeurMax) {   // On rempli le tableau "nombres" de 0 à 3
        for (int i = 0; i < valeurMax; i++) {
            tableauParam.add(i);
        }
    }


    // Tirage au sort
// SORTIE : un nombre entier compris entre min et max
    public  int pif(int min, int max) {

        int valeurRetour = rand.nextInt((max - min) ) + min;
        Log.i ("random ", "nombre reouurné : "+ valeurRetour + " entre " + min + " et " + max)  ;

        return valeurRetour;
    }

    void btnReponse(Question questionAAfficher) {

        // je remplis mon tableau indicesDesReponsesAAfficher avec 4 valeurs de 0 à 3
        remplirTableauDeValeurs(indicesDesReponsesAAfficher, 4);
        // je récupere ces 4 valeurs
        int i = valeurAuPifDuTableau(indicesDesReponsesAAfficher);
        int a = valeurAuPifDuTableau(indicesDesReponsesAAfficher);
        int b = valeurAuPifDuTableau(indicesDesReponsesAAfficher);
        int c = valeurAuPifDuTableau(indicesDesReponsesAAfficher);


        // btn question 1 :
        if (i == 0) {
            reponse1.setText(questionAAfficher.getReponse1());
        } else if (i == 1) {
            reponse1.setText(questionAAfficher.getReponse2());
        } else if (i == 2) {
            reponse1.setText(questionAAfficher.getReponse3());
        } else if (i == 3) {
            reponse1.setText(questionAAfficher.getReponse4());
        }
        // btn question 2 :
        if (a == 0) {
            reponse2.setText(questionAAfficher.getReponse1());
        } else if (a == 1) {
            reponse2.setText(questionAAfficher.getReponse2());
        } else if (a == 2) {
            reponse2.setText(questionAAfficher.getReponse3());
        } else if (a == 3) {
            reponse2.setText(questionAAfficher.getReponse4());
        }
        // btn question 3 :
        if (b == 0) {
            reponse3.setText(questionAAfficher.getReponse1());
        } else if (b == 1) {
            reponse3.setText(questionAAfficher.getReponse2());
        } else if (b == 2) {
            reponse3.setText(questionAAfficher.getReponse3());
        } else if (b == 3) {
            reponse3.setText(questionAAfficher.getReponse4());
        }
        // btn question 4 :
        if (c == 0) {
            reponse4.setText(questionAAfficher.getReponse1());
        } else if (c == 1) {
            reponse4.setText(questionAAfficher.getReponse2());
        } else if (c == 2) {
            reponse4.setText(questionAAfficher.getReponse3());
        } else if (c == 3) {
            reponse4.setText(questionAAfficher.getReponse4());
        }
    }

    void bonnereponse(String tampon) {
        if (reponse1.toString().equals(tampon)) {
            reponse1.setBackgroundResource(R.color.vert);
        } else if (reponse2.toString().equals(tampon)) {
            reponse2.setBackgroundResource(R.color.vert);
        } else if (reponse3.toString().equals(tampon)) {
            reponse3.setBackgroundResource(R.color.vert);
        } else if (reponse4.toString().equals(tampon)) {
            reponse4.setBackgroundResource(R.color.vert);
        }


    }

    void desactivebouton() {
        reponse1.setEnabled(false);
        reponse2.setEnabled(false);
        reponse3.setEnabled(false);
        reponse4.setEnabled(false);
    }

    void reactiverBouton() {
        reponse1.setEnabled(true);
        reponse2.setEnabled(true);
        reponse3.setEnabled(true);
        reponse4.setEnabled(true);
        reponse1.setBackgroundResource(R.color.bleu);
        reponse2.setBackgroundResource(R.color.bleu);
        reponse3.setBackgroundResource(R.color.bleu);
        reponse4.setBackgroundResource(R.color.bleu);
    }
}