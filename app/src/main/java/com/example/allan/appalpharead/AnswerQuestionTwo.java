package com.example.allan.appalpharead;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allan.appalpharead.models.DicionarioOnline;
import com.example.allan.appalpharead.models.DicionarioService;
import com.example.allan.appalpharead.models.Entry;
import com.example.allan.appalpharead.models.Sense;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnswerQuestionTwo extends Activity {

    private Button avancar, btnCancel;
    private TextView word;
    private Spinner classlist;
    private String palavra, selecao, classe;
    private int Point;

    private Bundle bundle;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question_two);

        this.context = getApplicationContext();

        bundle = getIntent().getExtras();

        palavra = bundle.getString("word");

        word = findViewById(R.id.word);
        word.setText(palavra);


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
        classlist = findViewById(R.id.classlist);
        avancar = findViewById(R.id.btnAvancar);
        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecao = String.valueOf(classlist.getSelectedItem());
                searchInDict(palavra, selecao);
            }
        });
    }


    private void searchInDict(final String word, final String selecao){


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
                            Matcher m = REMOVE_TAGS.matcher(s.gramGrp);
                            Log.i("Words", m.replaceAll(";").split(";")[0]);
                            classe = m.replaceAll(";").split(";")[0];
                            break;
                        }
                        switch(classe){
                            case "f.":
                                classe = "Substantivo";
                                break;
                            case "m.":
                                classe = "Substantivo";
                                break;
                            case "v. t.":
                                classe = "Verbo";
                                break;
                            case "v. i.":
                                classe = "Verbo";
                                break;
                            case "pron.":
                                classe = "Pronome";
                                break;
                            case "adj.":
                                classe = "Adjetivo";
                                break;
                            case "Art.":
                                classe = "Artigo";
                                break;
                            case "Interj.":
                                classe = "Interjeição";
                                break;
                            case "adv.":
                                classe = "Advérbio";
                                break;
                            case "conj.":
                                classe = "Conjunção";
                                break;
                            case "prep.":
                                classe = "Preposição";
                                break;
                            case "num.":
                                classe = "Numeral";
                                break;
                            default:
                                Toast.makeText(context, "Classe gramatical não encontrada.", Toast.LENGTH_LONG).show();
                                classe = "";
                                break;
                        }
                        Log.i("questao2", classe);
                        Log.i("questao2", selecao);
                        if (classe.equals(selecao)) {
                            Point = Integer.valueOf(bundle.getString("Point")) + 1;
                            Log.i("questao2", String.valueOf(Point));
                        }
                        Intent it = new Intent(context, AnswerQuestionThree.class);

                        it.putExtra("picture", bundle.getString("picture"));
                        it.putExtra("name", bundle.getString("name"));
                        it.putExtra("frase", bundle.getString("frase"));

                        Log.i("questao2", String.valueOf(Point));
                        it.putExtra("Point", String.valueOf(Point));

                        it.putExtra("uid", bundle.getString("uid"));
                        it.putExtra("userScore", bundle.getString("userScore"));
                        it.putExtra("score", bundle.getString("score"));
                        it.putExtra("userUid", bundle.getString("userUid"));

                        startActivity(it);
                        finish();

                    }catch (Exception e){
                        Toast.makeText(context, "Classe gramatical não encontrada.", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(context, AnswerQuestionThree.class);

                        it.putExtra("picture", bundle.getString("picture"));
                        it.putExtra("name", bundle.getString("name"));
                        it.putExtra("frase", bundle.getString("frase"));

                        Log.i("questao2", bundle.getString("Point"));
                        it.putExtra("Point", bundle.getString("Point"));

                        it.putExtra("uid", bundle.getString("uid"));
                        it.putExtra("userScore", bundle.getString("userScore"));
                        it.putExtra("score", bundle.getString("score"));
                        it.putExtra("userUid", bundle.getString("userUid"));

                        startActivity(it);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<DicionarioOnline> call, Throwable t) {
                Toast.makeText(context, "Palavra '"+word+"' não encontrada.", Toast.LENGTH_LONG).show();
                Intent it = new Intent(context, AnswerQuestionThree.class);

                it.putExtra("picture", bundle.getString("picture"));
                it.putExtra("name", bundle.getString("name"));
                it.putExtra("frase", bundle.getString("frase"));

                Log.i("questao2", bundle.getString("Point"));
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
