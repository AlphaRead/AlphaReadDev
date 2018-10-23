package com.example.allan.appalpharead;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.allan.appalpharead.models.DicionarioOnline;
import com.example.allan.appalpharead.models.Entry;
import com.example.allan.appalpharead.models.Sense;

import java.util.ArrayList;
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

    private Bundle bundle;
    private String answer = "!!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question_one);

        bundle = getIntent().getExtras();
        ArrayList<String> q = new ArrayList<>();
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

        searchInDict(q.get(0));
        sig1.setText(answer);

        searchInDict(q.get(1));
        sig2.setText(answer);

        searchInDict(q.get(2));
        sig3.setText(answer);
    }

    private void searchInDict(String word){
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
                Log.i("Words", Integer.toString(response.code()));
                if (response.isSuccessful()) {
                    //Log.i("Words", Integer.toString(response.code()));
                    //Requisição retornou com sucesso
                    DicionarioOnline dicionario = response.body();
                    String ans = "";
                    Entry e = dicionario.entry;
                    Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

                    for (Sense s : e.sense) {
                        Matcher m = REMOVE_TAGS.matcher(s.def);
                        answer = m.replaceAll(";").split(";")[0];
                        //sig.setText(m.replaceAll(";").split(";")[0]);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<DicionarioOnline> call, Throwable t) {
                Log.i("Words", "erro");
                answer = "..";
                /*android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getApplicationContext());
                alert.setTitle("Aviso");
                alert
                        .setMessage("Algo errado aconteceu ao tentar validar as palavras.")
                        .setIcon(R.drawable.notification);
                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();*/
            }
        });
    }

}
