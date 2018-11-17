package com.example.allan.appalpharead.provas;

public class UserProfile {
    private String nome;
    private String sobrenome;
    private int score;

    UserProfile(){}

    //Construtor para usuários novos score é inicializado com 0
    public UserProfile(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.score = 0;
    }

    //Construtor caso seja necessário criar um usuário com score diferente de 0
    public UserProfile(String nome, String sobrenome, int score) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.score = score;
    }

    //getters
    public String getNome() {
        return this.nome;
    }
    public String getSobrenome() {
        return this.sobrenome;
    }
    public int getScore() {
        return this.score;
    }

    //setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public void setScore(int score) {
        this.score = score;
    }

    //outros métodos
    public int incrementaScore(int val){
        this.score += val;
        return this.score;
    }
}
