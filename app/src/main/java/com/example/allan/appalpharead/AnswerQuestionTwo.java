package com.example.allan.appalpharead;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private TextView word, result;
    private Spinner classlist;
    private ImageView check, cat;

    private String palavra, selecao, classe;
    private int Point;
    private Boolean flag1, flag2;

    private Bundle bundle;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question_two);

        this.context = getApplicationContext();

        check = findViewById(R.id.check_4);
        cat = findViewById(R.id.cat);
        result = findViewById(R.id.result);

        bundle = getIntent().getExtras();

        palavra = bundle.getString("word");
        Point = 0;
        flag1 = false;
        flag2 = false;

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
                if (!flag1 && !flag2) {
                    flag1 = true;
                    selecao = String.valueOf(classlist.getSelectedItem());
                    searchInDict(palavra, selecao);
                }else if (flag1 && !flag2){
                    Toast.makeText(getApplicationContext(), "Aguarde. Estamos validando sua resposta.", Toast.LENGTH_LONG).show();
                }else if (flag1 && flag2){
                    Intent it = new Intent(context, AnswerQuestionThree.class);

                    it.putExtra("picture", bundle.getString("picture"));
                    it.putExtra("name", bundle.getString("name"));
                    it.putExtra("frase", bundle.getString("frase"));

                    it.putExtra("uid", bundle.getString("uid"));
                    it.putExtra("userScore", bundle.getString("userScore"));
                    it.putExtra("score", bundle.getString("score"));
                    it.putExtra("userUid", bundle.getString("userUid"));

                    it.putExtra("Point", String.valueOf(Point));

                    startActivity(it);
                    finish();
                }
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

                Intent it = new Intent(context, AnswerQuestionThree.class);

                it.putExtra("picture", bundle.getString("picture"));
                it.putExtra("name", bundle.getString("name"));
                it.putExtra("frase", bundle.getString("frase"));

                it.putExtra("uid", bundle.getString("uid"));
                it.putExtra("userScore", bundle.getString("userScore"));
                it.putExtra("score", bundle.getString("score"));
                it.putExtra("userUid", bundle.getString("userUid"));

                if (response.isSuccessful()) {
                    try {
                        //Requisição retornou com sucesso
                        DicionarioOnline dicionario = response.body();
                        Entry e = dicionario.entry;
                        Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

                        for (Sense s : e.sense) {
                            Matcher m = REMOVE_TAGS.matcher(s.gramGrp);
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
                        cat.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.cat_2));
                        result.setText(classe);
                        flag2 = true;
                        if (classe.equals(selecao)) {
                            check.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.balloon_sucess_2));
                            Point = Integer.valueOf(bundle.getString("Point")) + 1;
                        }else check.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.balloon_error_2));



                    }catch (Exception e){
                        Toast.makeText(context, "Classe gramatical não encontrada.", Toast.LENGTH_LONG).show();

                        it.putExtra("Point", String.valueOf(Point));

                        startActivity(it);
                        finish();
                    }
                }else{
                    Toast.makeText(context, "Classe gramatical não encontrada.", Toast.LENGTH_LONG).show();

                    it.putExtra("Point", String.valueOf(Point));

                    startActivity(it);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DicionarioOnline> call, Throwable t) {
                Toast.makeText(context, "Palavra '"+word+"' não encontrada.", Toast.LENGTH_LONG).show();
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
