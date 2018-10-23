package com.example.allan.appalpharead;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.allan.appalpharead.provas.Prova;
import com.example.allan.appalpharead.provas.QuestionOne;
import com.example.allan.appalpharead.provas.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchExam extends Activity {

    private ListView listExam;
    private ArrayList<String> nomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_exam);

        listExam = findViewById(R.id.listExam);

        nomes = new ArrayList<>();
        final ArrayList<Prova> provas = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProva = database.getReference("Provas");

        refProva.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //QuestionOne q = ds.getValue(QuestionOne.class);
                    //provas.add(q);
                        Log.d("myTag", ds.getValue(Prova.class).toString());
                }            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            //provas.add(adicionaProva("casa", "cama", "gato", "Allan", "Objetos"));
            //provas.add(adicionaProva("correr", "andar", "nadar", "Izabela", "Verbos"));
            //provas.add(adicionaProva("gato", "cachorro", "papagaio", "Bruno Henrique", "Animais"));
            //provas.add(adicionaProva("médico", "ginecologista", "desenvolvedor", "Érika", "Profissões"));
            //provas.add(adicionaProva("vermelho", "azul", "amarelo", "Edson", "Cores"));
            //provas.add(adicionaProva("pirulito", "brigadeiro", "bolo", "Rodrigo", "Doces"));
            //provas.add(adicionaProva("matemática", "ciências", "biologia", "Bruno Amorim", "Disciplinas"));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, nomes);
        listExam.setAdapter(arrayAdapter);

        listExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(), AnswerQuestionOne.class);
                //it.putExtra("word1", provas.get(position).get_q1().get_text_question().get(0));
                //it.putExtra("word2", provas.get(position).get_q1().get_text_question().get(1));
                //it.putExtra("word3", provas.get(position).get_q1().get_text_question().get(2));
                startActivity(it);
            }
        });

    }

    private void adicionaProva(String word1, String word2, String word3, String nome, String prova){
        ArrayList<String> words = new ArrayList<>();
        words.add(word1);
        words.add(word2);
        words.add(word3);

        nomes.add(nome);

        //return new Prova(new QuestionOne(words), new User(nome, prova));
    }
/*
    private Prova adicionaProva(QuestionOne q1, String nome, String prova){
        nomes.add(nome);

        return new Prova(q1, new User(nome, prova));
    }*/



}
