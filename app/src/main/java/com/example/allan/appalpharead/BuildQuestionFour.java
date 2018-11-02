package com.example.allan.appalpharead;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuildQuestionFour extends Activity {

    private Button btnAvancar;
    private EditText txtFrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_question_four);

        final Bundle b = getIntent().getExtras();

        btnAvancar = findViewById(R.id.btnAvancar);
        txtFrase = findViewById(R.id.txtFrase);

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(txtFrase.getText().toString())) {
                    Intent it = new Intent(getApplicationContext(), FinalizarBuild.class);

                    it.putExtra("word1", b.getString("word1"));
                    it.putExtra("word2", b.getString("word3"));
                    it.putExtra("word3", b.getString("word2"));
                    it.putExtra("word", b.getString("word"));
                    //it.putExtra("picture", b.getString("picture"));
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
