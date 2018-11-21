package com.example.allan.appalpharead;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class AnswerQuestionTwo extends Activity {

    private Button avancar, btnCancel;

    private Bundle bundle;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question_two);

        this.context = getApplicationContext();

        bundle = getIntent().getExtras();

        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(AnswerQuestionTwo.this);
                alert.setTitle("Tem certeza de que deseja sair?");
                alert
                        .setMessage("Todo o progresso nesta construção de prova será perdido.")
                        .setIcon(R.drawable.notification)
                        .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(AnswerQuestionTwo.this, PaginaPrincipal.class));
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });

        avancar = findViewById(R.id.btnAvancar);
        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, AnswerQuestionThree.class);

                it.putExtra("picture", bundle.getString("picture"));
                it.putExtra("name", bundle.getString("name"));
                it.putExtra("frase", bundle.getString("frase"));

                it.putExtra("Point", bundle.getString("Point"));

                it.putExtra("uid", bundle.getString("uid"));
                it.putExtra("userScore", bundle.getString("userScore"));
                it.putExtra("score", bundle.getString("score"));
                it.putExtra("userUid", bundle.getString("userUid"));

                startActivity(it);
                finish();
            }
        });
    }
}
