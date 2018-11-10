package com.example.allan.appalpharead;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.allan.appalpharead.Adapters.RecyclerViewAdapter;
import com.example.allan.appalpharead.provas.Prova;
import com.example.allan.appalpharead.provas.QuestionFour;
import com.example.allan.appalpharead.provas.QuestionOne;
import com.example.allan.appalpharead.provas.QuestionTwo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.SimpleTimeZone;

public class SearchExam extends Activity {

    private ArrayList<String> titles, points, uid;
    private ArrayList<Prova> prova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_exam);

        titles = new ArrayList<>();
        points = new ArrayList<>();
        uid = new ArrayList<>();
        prova = new ArrayList<>();

        initList();
    }

    private void initList(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProva = database.getReference("Users/");

        refProva.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot pSnapShot : ds.child("Provas").getChildren()){
                        titles.add(pSnapShot.child("questionTitle").getValue().toString());
                        points.add(pSnapShot.child("score").getValue().toString());
                        uid.add(pSnapShot.getKey());

                        QuestionOne q1 = new QuestionOne(pSnapShot.child("_q1").child("w1").getValue().toString(),
                                pSnapShot.child("_q1").child("w2").getValue().toString(),
                                pSnapShot.child("_q1").child("w3").getValue().toString());

                        Log.i("myTag", q1.getW1());
                        Log.i("myTag", q1.getW2());
                        Log.i("myTag", q1.getW3());

                        QuestionTwo q2 = new QuestionTwo(pSnapShot.child("_q2").child("word").getValue().toString());

                        QuestionFour q4 = new QuestionFour(pSnapShot.child("_q4").child("frase").getValue().toString());

                        Prova p = new Prova(q1, q2, q4, pSnapShot.child("questionTitle").getValue().toString());
                        prova.add(p);

                        initRecyclerView();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

   private void initRecyclerView(){
        RecyclerView rv = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(prova, uid, titles, points, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

}
