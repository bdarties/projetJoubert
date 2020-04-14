package com.example.test2;

public class Question {
    String question,reponse1,reponse2,reponse3,reponse4;
    public Question(String qst, String r1,String r2, String r3,String r4) {
        this.question=qst;
        this.reponse1=r1;
        this.reponse2=r2;
        this.reponse3=r3;
        this.reponse4=r4;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponse1() {
        return reponse1;
    }

    public String getReponse2() {
        return reponse2;
    }

    public String getReponse3() {
        return reponse3;
    }

    public String getReponse4() {
        return reponse4;
    }
}
