package com.example.allan.appalpharead;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allan.appalpharead.models.DicionarioOnline;
import com.example.allan.appalpharead.models.Entry;
import com.example.allan.appalpharead.models.Sense;
import com.example.allan.appalpharead.provas.Prova;
import com.example.allan.appalpharead.provas.QuestionOne;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildQuestionOne extends Activity {

    private Button btnAvancar, btnVoltar, btnValidate;
    private EditText wordOne, wordTwo, wordThree;
    private String ans;

    String w1, w2, w3;

    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

    private QuestionOne q1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_question_one);

        wordOne = findViewById(R.id.word1);
        wordTwo = findViewById(R.id.word2);
        wordThree = findViewById(R.id.word3);

        btnAvancar = findViewById(R.id.btnAvancar);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnValidate = findViewById(R.id.btnValidate);

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getApplicationContext(), BuildQuestionTwo.class);

                w1 = wordOne.getText().toString();
                w2 = wordTwo.getText().toString();
                w3 = wordThree.getText().toString();

                if (w1.matches("") || w2.matches("" ) || w3.matches("")){
                    Toast.makeText(getApplicationContext(), "Favor preencher todos os campos!", Toast.LENGTH_LONG).show();
                }else{
                    it.putExtra("word1", w1);
                    it.putExtra("word2", w2);
                    it.putExtra("word3", w3);

                    startActivity(it);
                }
            }
        });
    }
}
