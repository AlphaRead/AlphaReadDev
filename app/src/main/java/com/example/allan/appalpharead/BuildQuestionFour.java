package com.example.allan.appalpharead;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuildQuestionFour extends Activity {

    private Button btnAvancar, btnCancel;
    private EditText txtFrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_question_four);

        final Bundle b = getIntent().getExtras();

        btnAvancar = findViewById(R.id.btnAvancar);
        txtFrase = findViewById(R.id.txtFrase);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(BuildQuestionFour.this);
                alert.setTitle("Tem certeza de que deseja sair?");
                alert
                        .setMessage("Todo o progresso nesta construção de prova será perdido.")
                        .setIcon(R.drawable.notification)
                        .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(BuildQuestionFour.this, PaginaPrincipal.class));
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

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(txtFrase.getText().toString())) {
                    Intent it = new Intent(getApplicationContext(), FinalizarBuild.class);

                    it.putExtra("word1", b.getString("word1"));
                    it.putExtra("word2", b.getString("word2"));
                    it.putExtra("word3", b.getString("word3"));
                    it.putExtra("word", b.getString("word"));
                    it.putExtra("picture", b.getString("picture"));
                    it.putExtra("name", b.getString("name"));
                    it.putExtra("frase", txtFrase.getText().toString());

                    startActivity(it);
                }else{
                    Toast.makeText(getApplicationContext(), "Favor preencher todos os campos!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
