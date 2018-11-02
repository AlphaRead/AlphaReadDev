package com.example.allan.appalpharead.provas;

public class QuestionFour {
    private static int score = 1;

    private String frase;

    public QuestionFour(String frase){
        this.frase = frase;
    }

    public String getFrase() {
        return frase;
    }
    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        QuestionFour.score = score;
    }
    public void setFrase(String frase) {
        this.frase = frase;
    }
}
