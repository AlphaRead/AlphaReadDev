package com.example.allan.appalpharead;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allan.appalpharead.models.DicionarioOnline;
import com.example.allan.appalpharead.models.DicionarioService;
import com.example.allan.appalpharead.models.Entry;
import com.example.allan.appalpharead.models.Sense;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnswerQuestionOne extends Activity {

    private TextView words, sig1, sig2, sig3;
    private EditText ans1, ans2, ans3;

    private Button avancar, btnCancel;

    private Bundle bundle;

    private Context context;

    private ArrayList<String> q;
    private ArrayList<Integer> relation = new ArrayList<>();

    private boolean[] v = new boolean[]{false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question_one);

        this.context = getApplicationContext();

        bundle = getIntent().getExtras();

        q = new ArrayList<>();
        q.add(bundle.getString("word1"));
        q.add(bundle.getString("word2"));
        q.add(bundle.getString("word3"));

        words = findViewById(R.id.words);
        String text = "Palavras: ";
        text = text.concat(q.get(0).concat(", ")).concat(q.get(1).concat(", ")).concat(q.get(2));
        words.setText(text);

        sig1 = findViewById(R.id.sig1);
        sig2 = findViewById(R.id.sig2);
        sig3 = findViewById(R.id.sig3);

        relation.add(-1);
        relation.add(-1);
        relation.add(-1);

        putSig(sig1, 0);
        putSig(sig2, 1);
        putSig(sig3, 2);

        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(AnswerQuestionOne.this);
                alert.setTitle("Tem certeza de que deseja sair?");
                alert
                        .setMessage("Todo o progresso nesta construção de prova será perdido.")
                        .setIcon(R.drawable.notification)
                        .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(AnswerQuestionOne.this, PaginaPrincipal.class));
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
                ans1 = findViewById(R.id.ans1);
                ans2 = findViewById(R.id.ans2);
                ans3 = findViewById(R.id.ans3);

                int point = avaliate(ans1.getText().toString(), ans2.getText().toString(), ans3.getText().toString());

                Intent it = new Intent(context, AnswerQuestionTwo.class);

                it.putExtra("word", bundle.getString("word"));
                it.putExtra("picture", bundle.getString("picture"));
                it.putExtra("name", bundle.getString("name"));
                it.putExtra("frase", bundle.getString("frase"));

                it.putExtra("Point", Integer.toString(point));

                it.putExtra("uid", bundle.getString("uid"));
                it.putExtra("userScore", bundle.getString("userScore"));
                it.putExtra("score", bundle.getString("score"));
                it.putExtra("userUid", bundle.getString("userUid"));

                startActivity(it);
                finish();
            }

            private int avaliate(String ans1, String ans2, String ans3) {
                int point = 0;
                Log.i("myTag", ans1 + " " + ans2 + " " + ans3);
                Log.i("myTag", q.get(relation.get(0)) + " " + q.get(relation.get(1)) + " " + q.get(relation.get(2)));
                if(ans1.toLowerCase().equals(q.get(relation.get(0)).toLowerCase())) point++;
                if(ans2.toLowerCase().equals(q.get(relation.get(1)).toLowerCase())) point++;
                if(ans3.toLowerCase().equals(q.get(relation.get(2)).toLowerCase())) point++;
                return point;
            }
        });
    }

    private void putSig(final TextView sig, int i){
        Random aleatorio = new Random();
        while (true) {
            int n = aleatorio.nextInt(3);
            if (n == 0 && !v[0]) {
                v[0] = true;
                relation.set(0, i);
                searchInDict(q.get(0), sig);
                break;
            } else if (n == 1 && !v[1]) {
                v[1] = true;
                relation.set(1, i);
                searchInDict(q.get(1), sig);
                break;
            } else if (n == 2 && !v[2]) {
                v[2] = true;
                relation.set(2, i);
                searchInDict(q.get(2), sig);
                break;
            }
        }
    }

    private void searchInDict(final String word, final TextView sig){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dicionario-aberto.net/") //define a url base
                .addConverterFactory(GsonConverterFactory.create()) //define o objeto de converção (Gson)
                .build();

        //retrofit retorna uma classe que implementa DicionarioService(pois este é uma interface e não pode ser instânciada)
        DicionarioService service = retrofit.create(DicionarioService.class);

        //objeto para fazer a chamada
        Call<DicionarioOnline> requestDicionario = service.searchWord("search-json/" + word);

        requestDicionario.enqueue(new Callback<DicionarioOnline>() {
            @Override
            public void onResponse(Call<DicionarioOnline> call, Response<DicionarioOnline> response) {
                if (response.isSuccessful()) {
                    try {
                        //Requisição retornou com sucesso
                        DicionarioOnline dicionario = response.body();
                        Entry e = dicionario.entry;
                        Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

                        for (Sense s : e.sense) {
                            Matcher m = REMOVE_TAGS.matcher(s.def);
                            Log.i("Words", m.replaceAll(";").split(";")[0]);
                            sig.setText(m.replaceAll(";").split(";")[0]);
                            break;
                        }
                    }catch (Exception e){
                        sig.setText("Palavra não encontrada no dicionário.");
                    }
                }
            }

            @Override
            public void onFailure(Call<DicionarioOnline> call, Throwable t) {
                Toast.makeText(context, "Palavra '"+word+"' não encontrada.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
