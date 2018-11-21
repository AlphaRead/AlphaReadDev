package com.example.allan.appalpharead;

import android.content.Context;
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
import android.widget.ImageView;

public class AnswerQuestionThree extends Activity {

    private Button avancar, btnCancel;
    private Bundle bundle;
    private Context context;
    private ImageView image;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question_three);

        this.context = getApplicationContext();

        bundle = getIntent().getExtras();

        image = findViewById(R.id.picture);
        name = findViewById(R.id.name);

        byte[] decodedString = Base64.decode(bundle.getString("picture"), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);

        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(AnswerQuestionThree.this);
                alert.setTitle("Tem certeza de que deseja sair?");
                alert
                        .setMessage("Todo o progresso nesta construção de prova será perdido.")
                        .setIcon(R.drawable.notification)
                        .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(AnswerQuestionThree.this, PaginaPrincipal.class));
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
                Intent it = new Intent(context, AnswerQuestionFour.class);

                int point = Integer.valueOf(bundle.getString("Point")) + pontuacao();
                Log.i("questao3", bundle.getString("Point"));
                Log.i("questao3", String.valueOf(point));

                it.putExtra("frase", bundle.getString("frase"));
                it.putExtra("Point", String.valueOf(point));

                it.putExtra("uid", bundle.getString("uid"));
                it.putExtra("userScore", bundle.getString("userScore"));
                it.putExtra("score", bundle.getString("score"));
                it.putExtra("userUid", bundle.getString("userUid"));

                startActivity(it);
                finish();
            }
        });
    }

    private int pontuacao(){
        if (name.getText().toString().toLowerCase().equals(bundle.getString("name").toLowerCase())) return 1;
        return 0;
    }

}
