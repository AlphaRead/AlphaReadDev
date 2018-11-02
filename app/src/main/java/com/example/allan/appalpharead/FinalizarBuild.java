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
import com.example.allan.appalpharead.provas.QuestionFour;
import com.example.allan.appalpharead.provas.QuestionOne;
import com.example.allan.appalpharead.provas.QuestionThree;
import com.example.allan.appalpharead.provas.QuestionTwo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FinalizarBuild extends Activity {

    private EditText txtProva;
    private Button btnSalvar;

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
                txtProva = findViewById(R.id.txtProva);
                if(b != null || !TextUtils.isEmpty(txtProva.getText().toString())){

                    QuestionOne question1 = new QuestionOne(b.getString("word1"), b.getString("word1"), b.getString("word1"));
                    QuestionTwo question2 = new QuestionTwo(b.getString("word"));
                    //QuestionThree question3 = new QuestionThree(b.getString("name"), b.getString("picture"));
                    QuestionFour question4 = new QuestionFour(b.getString("frase"));

                    //User user = new User(<nome da pessoa>, txtProva.getText().toString());

                    Prova prova = new Prova(question1, question2, question4, txtProva.getText().toString());

                    //salva a prova no firebase
                    saveTest(prova);

                    Toast.makeText(getApplicationContext(), "Prova salva com sucesso", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), PaginaPrincipal.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Campo obrigatório", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void saveTest(Prova p) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        DatabaseReference refProva = database.getReference("/Users/"+uid+"/Provas/");
        String provaID = refProva.push().getKey();

        refProva.child(provaID).setValue(p);
    }

}
