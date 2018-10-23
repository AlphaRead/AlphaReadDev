package com.example.allan.appalpharead;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allan.appalpharead.provas.Prova;
import com.example.allan.appalpharead.provas.QuestionOne;
import com.example.allan.appalpharead.provas.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FinalizarBuild extends Activity {

    EditText txtProva;
    Button btnSalvar;



    String q1, q2, q3, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_build);

        final Bundle b = getIntent().getExtras();
        btnSalvar = findViewById(R.id.btnSalvar);
        txtProva = findViewById(R.id.txtProva);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtProva = findViewById(R.id.txtProva);
                if(b != null || !TextUtils.isEmpty(txtProva.getText().toString())){

                    q1 = b.getString("word1");
                    q2 = b.getString("word2");
                    q3 = b.getString("word3");
                    title = txtProva.getText().toString();

                    QuestionOne question = new QuestionOne(q1, q2, q3, title);

                    //salva a prova no firebase
                    saveTest(question);



                    Toast.makeText(getApplicationContext(), "Prova salva com sucesso", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), PaginaPrincipal.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Campo obrigatório", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void saveTest(QuestionOne q) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        DatabaseReference refProva = database.getReference("/Users/"+uid+"/Provas/QuestionOne/");
        String provaID = refProva.push().getKey();

        refProva.child(provaID).setValue(q);
    }

}
