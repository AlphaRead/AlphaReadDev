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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allan.appalpharead.Algoritms.Q1;
import com.example.allan.appalpharead.models.DicionarioOnline;
import com.example.allan.appalpharead.models.DicionarioService;
import com.example.allan.appalpharead.models.Entry;
import com.example.allan.appalpharead.models.Sense;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnswerQuestionOne extends Activity {

    private TextView words, sig1, sig2, sig3, result1, result2, result3;
    private EditText ans1, ans2, ans3;
    private ImageView check1, check2, check3, cat;
    private Button avancar, btnCancel;

    private Bundle bundle;

    private Context context;

    private ArrayList<Q1> q;
    private ArrayList<Integer> relation = new ArrayList<>();

    private boolean[] v = new boolean[]{false, false, false};
    private Boolean flag;
    private int point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question_one);

        this.context = getApplicationContext();

        flag = true;
        point = 0;

        result1 = findViewById(R.id.result1);
        result2 = findViewById(R.id.result2);
        result3 = findViewById(R.id.result3);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        cat = findViewById(R.id.cat);

        bundle = getIntent().getExtras();

        sig1 = findViewById(R.id.sig1);
        sig2 = findViewById(R.id.sig2);
        sig3 = findViewById(R.id.sig3);

        ArrayList<Q1> txt = new ArrayList<>();
        txt.add(new Q1(sig1, 0));
        txt.add(new Q1(sig2, 1));
        txt.add(new Q1(sig3, 2));
        Collections.shuffle(txt);

        q = new ArrayList<>();
        q.add(new Q1(bundle.getString("word1"), txt.get(0).getSig(), txt.get(0).getPos()));
        q.add(new Q1(bundle.getString("word2"), txt.get(1).getSig(), txt.get(1).getPos()));
        q.add(new Q1(bundle.getString("word3"), txt.get(2).getSig(), txt.get(2).getPos()));

        words = findViewById(R.id.words);
        String text = "Palavras: ";
        text = text.concat(q.get(0).getWord().concat(", ")).concat(q.get(1).getWord().concat(", ")).concat(q.get(2).getWord());
        words.setText(text);

        Collections.shuffle(q);

        searchInDict(q.get(0).getWord(), q.get(0).getSig());
        searchInDict(q.get(1).getWord(), q.get(1).getSig());
        searchInDict(q.get(2).getWord(), q.get(2).getSig());


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
                if(flag){
                    flag = false;

                    ans1 = findViewById(R.id.ans1);
                    ans2 = findViewById(R.id.ans2);
                    ans3 = findViewById(R.id.ans3);

                    point = avaliate(ans1.getText().toString(), ans2.getText().toString(), ans3.getText().toString());
                }else {
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
            }

            private int avaliate(String ans1, String ans2, String ans3) {
                int point = 0;
                cat.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.cat));

                ArrayList<String> respostas = new ArrayList<>();
                respostas.add("."); respostas.add("."); respostas.add(".");

                for (Q1 s: q) respostas.set(s.getPos(), s.getWord());

                result1.setText(respostas.get(0));
                result2.setText(respostas.get(1));
                result3.setText(respostas.get(2));

                if(ans1.toLowerCase().equals(respostas.get(0).toLowerCase())) {
                    check1.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.balloon_sucess));
                    point++;
                }else check1.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.balloon_error));

                if(ans2.toLowerCase().equals(respostas.get(1).toLowerCase())){
                    check2.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.balloon_sucess_2));
                    point++;
                }else check2.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.balloon_error_2));

                if(ans3.toLowerCase().equals(respostas.get(2).toLowerCase())){
                    check3.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.balloon_sucess));
                    point++;
                }else check3.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.balloon_error));

                return point;
            }
        });
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
                            sig.setText(m.replaceAll(";").split(";")[0]);
                            break;
                        }
                    }catch (Exception e){
                        sig.setText("Palavra não encontrada no dicionário.");
                    }
                }else{
                    sig.setText("Palavra não encontrada no dicionário.");
                }
            }

            @Override
            public void onFailure(Call<DicionarioOnline> call, Throwable t) {
                Toast.makeText(context, "Palavra '"+word+"' não encontrada.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
