package com.example.test2;

import android.os.CountDownTimer;
import android.view.View;

public class AfficherQuestionF extends francais{
    {
    // affiche les questions tour à tour
        commencer.setVisibility(View.INVISIBLE);
        titreFr.setVisibility(View.INVISIBLE);
        question.setVisibility(View.VISIBLE);
        reponse1.setVisibility(View.VISIBLE);
        reponse2.setVisibility(View.VISIBLE);
        reponse3.setVisibility(View.VISIBLE);
        reponse4.setVisibility(View.VISIBLE);
    final Question result = null;

        question.setText(result.getQuestion()); // affiche la question choisie aléatoirement
    final String tampon = result.getReponse1(); // variable tampon prenant la bonne réponse de la question
    btnReponse(result);
        reponse1.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            reponse();
            if (reponse1.toString().equals(tampon) ){// mettre l'id de la reponse juste
                reponse1.setBackgroundResource(R.color.vert);
                setScore();
                CountDownTimer time = new CountDownTimer(3000,1000) {
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
            else {reponse1.setBackgroundResource(R.color.rouge);
                bonnereponse(tampon);
                CountDownTimer time = new CountDownTimer(3000,1000) {
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
            tempsrestant=10000;
        }});
        reponse2.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            reponse();
            if (reponse2.toString().equals(tampon) ){ // mettre l'id de la reponse juste
                reponse2.setBackgroundResource(R.color.vert);
                setScore();

                CountDownTimer time = new CountDownTimer(3000,1000) {
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
            else {reponse2.setBackgroundResource(R.color.rouge);
                bonnereponse(tampon);
                CountDownTimer time = new CountDownTimer(3000,1000) {
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
            tempsrestant=10000;
        }});
        reponse3.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            reponse();
            if (reponse3.toString().equals(tampon) ){ // mettre l'id de la reponse juste
                reponse3.setBackgroundResource(R.color.vert);
                setScore();
                CountDownTimer time = new CountDownTimer(3000,1000) {
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
            else {reponse3.setBackgroundResource(R.color.rouge);
                bonnereponse(tampon);
                CountDownTimer time = new CountDownTimer(3000,1000) {
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
            tempsrestant=10000;
        }});
        reponse4.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            reponse();
            if (reponse4.toString().equals(tampon)){ // mettre l'id de la reponse juste
                reponse4.setBackgroundResource(R.color.vert);
                setScore();
                CountDownTimer time = new CountDownTimer(3000,1000) {
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
            else {reponse4.setBackgroundResource(R.color.rouge);
                bonnereponse(tampon);
                CountDownTimer time = new CountDownTimer(3000,1000) {
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
            tempsrestant=10000;
        }});

}
}


