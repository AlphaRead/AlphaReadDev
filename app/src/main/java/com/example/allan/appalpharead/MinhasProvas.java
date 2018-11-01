package com.example.allan.appalpharead;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.example.allan.appalpharead.provas.QuestionOne;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MinhasProvas extends Activity {
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;

    TextView test;
    List<QuestionOne> q1List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_provas);

        test = findViewById(R.id.txtTesteProvas);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        final String uid = mAuth.getUid();

        q1List = new ArrayList<>();

        DatabaseReference provasRef = mDatabase.getReference("/Users/"+uid+"/Provas/QuestionOne/");

        provasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    QuestionOne q = ds.getValue(QuestionOne.class);
                    //test.setText(q.getTitle());
                    q1List.add(q);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
