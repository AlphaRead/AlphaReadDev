package com.example.allan.appalpharead.provas;

public class Prova {

    private QuestionOne _q1;
    private User user;
    Prova(){}

    public Prova(QuestionOne q1, User user){
        this._q1 = q1;
        this.user = user;
    }

    public QuestionOne get_q1() {
        return _q1;
    }

    public void set_q1(QuestionOne _q1) {
        this._q1 = _q1;
    }
}
