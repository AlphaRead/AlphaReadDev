package com.example.allan.appalpharead;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.allan.appalpharead.models.DicionarioOnline;
import com.example.allan.appalpharead.models.Entry;
import com.example.allan.appalpharead.models.Sense;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingFrament extends Fragment {
    private EditText word;
    private TextView boxResponse;
    private Button btn;

    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        word = view.findViewById(R.id.TextT);
        boxResponse = view.findViewById(R.id.TextRT);
        btn = view.findViewById(R.id.btnTest);

        btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://dicionario-aberto.net/") //define a url base
                            .addConverterFactory(GsonConverterFactory.create()) //define o objeto de converção (Gson)
                            .build();

                    //retrofit retorna uma classe que implementa DicionarioService(pois este é uma interface e não pode ser instânciada)
                    DicionarioService service = retrofit.create(DicionarioService.class);

                    //objeto para fazer a chamada
                    Call<DicionarioOnline> requestDicionario = service.searchWord("search-json/"+word.getText().toString());

                    requestDicionario.enqueue(new Callback<DicionarioOnline>() {
                        @Override
                        public void onResponse(Call<DicionarioOnline> call, Response<DicionarioOnline> response) {
                            if(!response.isSuccessful()) {
                                boxResponse.setText("Desculpe, essa palavra não foi encontrada, tente outra.");
                            }else{
                                //Requisição retornou co sucesso
                                DicionarioOnline dicionario = response.body();
                                String ans = "";
                                Entry e = dicionario.entry;
                                for(Sense s: e.sense) {
                                    Matcher m = REMOVE_TAGS.matcher(s.def);
                                    ans = m.replaceAll(";").split(";")[0];
                                    break;
                                }
                                boxResponse.setText(ans);
                            }
                }

                @Override
                public void onFailure(Call<DicionarioOnline> call, Throwable t) {
                    boxResponse.setText("Desculpe, essa palavra não foi encontrada, tente outra.");
                }
            });
        }
        });
        return view;
    }
}
