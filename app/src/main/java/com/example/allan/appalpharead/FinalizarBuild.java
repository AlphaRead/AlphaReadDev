package com.example.allan.appalpharead;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Base64;
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
    private Button btnSalvar, btnCancel;

    private String uid;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_build);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();

        final Bundle b = getIntent().getExtras();
        btnSalvar = findViewById(R.id.btnSalvar);
        txtProva = findViewById(R.id.txtProva);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(FinalizarBuild.this);
                alert.setTitle("Tem certeza de que deseja sair?");
                alert
                        .setMessage("Todo o progresso nesta construção de prova será perdido.")
                        .setIcon(R.drawable.notification)
                        .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(FinalizarBuild.this, PaginaPrincipal.class));
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        });
                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtProva = findViewById(R.id.txtProva);
                if(b != null || !TextUtils.isEmpty(txtProva.getText().toString())){

                    //byte[] decodedString = Base64.decode(b.getString("picture"), Base64.DEFAULT);
                    //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    QuestionOne question1 = new QuestionOne(b.getString("word1"), b.getString("word2"), b.getString("word3"));
                    QuestionTwo question2 = new QuestionTwo(b.getString("word"));
                    QuestionThree question3 = new QuestionThree(b.getString("name"), b.getString("picture"));
                    QuestionFour question4 = new QuestionFour(b.getString("frase"));

                    Prova prova = new Prova(question1, question2, question3, question4, txtProva.getText().toString(), uid);

                    //salva a prova no firebase
                    saveTest(prova);

                    Toast.makeText(getApplicationContext(), "Prova salva com sucesso", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), PaginaPrincipal.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Campo obrigatório", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void saveTest(Prova p) {
        DatabaseReference refProva = database.getReference("/Users/"+uid+"/Provas/");
        String provaID = refProva.push().getKey();
        refProva.child(provaID).setValue(p);
    }

}
