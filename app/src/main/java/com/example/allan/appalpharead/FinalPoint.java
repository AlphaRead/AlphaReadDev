package com.example.allan.appalpharead;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class FinalPoint extends Activity {

    private TextView point;

    private Button btnTelaPrincipal;

    private Bundle bundle;
    private Context context;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    private static String totalUser, totalExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_point);

        context = getApplicationContext();

        bundle = getIntent().getExtras();

        point = findViewById(R.id.Point);
        point.setText(bundle.getString("Point"));

        btnTelaPrincipal = findViewById(R.id.btnEnd);
        btnTelaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), PaginaPrincipal.class);
                startActivity(it);
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getUid();

        //Change score to Exam
        DatabaseReference provasRef = mDatabase.getReference("/Users/"+bundle.getString("userUid")+"/Provas/"+bundle.getString("uid"));
        totalExam = String.valueOf(Integer.valueOf(bundle.getString("score")) + Integer.valueOf(bundle.getString("Point")));
        provasRef.child("score").setValue(totalExam);

        //Change score to User
        DatabaseReference usersRef = mDatabase.getReference("/Users/"+uid+"/UserProfile/"+uid);
        totalUser = String.valueOf(Integer.valueOf(bundle.getString("userScore")) + Integer.valueOf(bundle.getString("Point")));
        usersRef.child("score").setValue(totalUser);
    }
}
