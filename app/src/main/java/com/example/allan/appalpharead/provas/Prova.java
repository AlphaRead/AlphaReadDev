package com.example.allan.appalpharead.provas;

public class Prova {

    private QuestionOne _q1;
    private QuestionTwo _q2;
    private QuestionThree _q3;
    private User user;

    private String questionTitle;

    Prova(){}

    public Prova(QuestionOne q1, QuestionTwo q2, QuestionThree q3, User user, String questionTitle){
        this._q1 = q1;
        this._q2 = q2;
        this._q3 = q3;
        this.user = user;
        this.questionTitle = questionTitle;
    }

    public QuestionOne get_q1() {
        return _q1;
    }
    public QuestionTwo get_q2() {
        return _q2;
    }
    public QuestionThree get_q3() {
        return _q3;
    }
    public User getUser() {
        return user;
    }
    public String getQuestionTitle() {
        return questionTitle;
    }

    public void set_q1(QuestionOne _q1) {
        this._q1 = _q1;
    }
    public void set_q2(QuestionTwo _q2) {
        this._q2 = _q2;
    }
    public void set_q3(QuestionThree _q3) {
        this._q3 = _q3;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

}
