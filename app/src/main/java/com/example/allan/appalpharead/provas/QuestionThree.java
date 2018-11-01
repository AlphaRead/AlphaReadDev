package com.example.allan.appalpharead.provas;

import android.graphics.Bitmap;

public class QuestionThree {

    private static int score = 1;

    public Bitmap image;
    public String name;

    public QuestionThree(String name, Bitmap image){
        this.image = image;
        this.name = name;
    }

    public static int getScore() {
        return score;
    }
    public Bitmap getImage() {
        return image;
    }
    public String getName() {
        return name;
    }

    public static void setScore(int score) {
        QuestionThree.score = score;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public void setName(String name) {
        this.name = name;
    }
}
