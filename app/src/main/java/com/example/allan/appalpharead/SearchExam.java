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
import com.google.firebase.auth.FirebaseAuth;
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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProva = database.getReference("Users/");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, nomes);

        refProva.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot qSnapShot : ds.child("Provas").getChildren()){
                        provas.add(qSnapShot.getValue(Prova.class));
                        //nomes.add(qSnapShot.getValue(QuestionOne.class).getTitle());
                        //Log.i("Words", qSnapShot.getValue(QuestionOne.class).getTitle());
                    }
                    //QuestionOne q = ds.getValue(QuestionOne.class);
                    //provas.add(q);
                    Log.d("myTag", ds.getValue(Prova.class).toString());
                }
                listExam.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, nomes);
        listExam.setAdapter(arrayAdapter);

        listExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(), AnswerQuestionOne.class);
                //it.putExtra("word1", questoes.get(position).getQ1());
                //it.putExtra("word2", questoes.get(position).getQ2());
                //it.putExtra("word3", questoes.get(position).getQ3());
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

        //return new Prova(new QuestionOne(words));
    }
/*
    private Prova adicionaProva(QuestionOne q1, String nome, String prova){
        nomes.add(nome);

        return new Prova(q1);
    }*/



}
