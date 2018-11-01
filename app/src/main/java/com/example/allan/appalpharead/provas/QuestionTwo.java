package com.example.allan.appalpharead.provas;

public class QuestionTwo {

    private static int score = 1;

    private String word;

    public QuestionTwo(String word){
        this.word = word;
    }

    public static int getScore() {
        return score;
    }
    public String getWord() {
        return word;
    }

    public static void setScore(int score) {
        QuestionTwo.score = score;
    }
    public void setWord(String word) {
        this.word = word;
    }
}
