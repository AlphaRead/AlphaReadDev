package com.example.allan.appalpharead;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.allan.appalpharead.api.ApiUtils;
import com.example.allan.appalpharead.api.Data;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswerQuestionFour extends Activity {

    private Button avancar, gravar, play, btnCancel;
    private Bundle bundle;
    private Context context;
    private TextView frase;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private String pathSaved = "", transcript = "";
    private Boolean flagGravar, flagPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question_four);

        this.context = getApplicationContext();

        bundle = getIntent().getExtras();

        frase = findViewById(R.id.frase);
        frase.setText(bundle.getString("frase"));

        flagGravar = false;
        flagPlay = false;

        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(AnswerQuestionFour.this);
                alert.setTitle("Tem certeza de que deseja sair?");
                alert
                        .setMessage("Todo o progresso nesta construção de prova será perdido.")
                        .setIcon(R.drawable.notification)
                        .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(AnswerQuestionFour.this, PaginaPrincipal.class));
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

        gravar = findViewById(R.id.gravar);
        gravar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagGravar){
                    flagGravar = true;
                    gravar.setBackground(context.getResources().getDrawable(R.drawable.stop));
                    play.setEnabled(false);

                    pathSaved = Environment.getExternalStorageDirectory()
                            .getAbsolutePath()+"/"
                            + UUID.randomUUID().toString()+"_audio_record.wav";

                    setupMediaRecord();
                    try{
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    }catch (Exception e){}
                }else{
                    flagGravar = false;
                    gravar.setBackground(context.getResources().getDrawable(R.drawable.record));
                    mediaRecorder.stop();
                    play.setEnabled(true);
                }
            }
        }));

        play = findViewById(R.id.play);
        play.setEnabled(false);
        play.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagPlay){
                    flagPlay = true;
                    play.setBackground(context.getResources().getDrawable(R.drawable.pause));

                    mediaPlayer = new MediaPlayer();
                    try{
                        mediaPlayer.setDataSource(pathSaved);
                        mediaPlayer.prepare();
                    }catch (Exception e){}

                    mediaPlayer.start();
                    gravar.setEnabled(false);

                }else{
                    flagPlay = false;
                    play.setBackground(context.getResources().getDrawable(R.drawable.play));

                    if(mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        setupMediaRecord();
                    }

                    gravar.setEnabled(true);
                }
            }
        }));

        avancar = findViewById(R.id.btnAvancar);
        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] audioBytes;
                try {
                    File audioFile = new File(pathSaved);
                    long fileSize = audioFile.length();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    FileInputStream fis = new FileInputStream(new File(pathSaved));
                    byte[] buf = new byte[1024];
                    int n;
                    while (-1 != (n = fis.read(buf))) {
                        baos.write(buf, 0, n);
                    }
                    audioBytes = baos.toByteArray();

                    String _audioBase64 = Base64.encodeToString(audioBytes, Base64.DEFAULT);
                    onHit(_audioBase64);
                }catch (Exception e){ }

                Intent it = new Intent(context, FinalPoint.class);

                it.putExtra("Point", String.valueOf(10));

                it.putExtra("uid", bundle.getString("uid"));
                it.putExtra("userScore", bundle.getString("userScore"));
                it.putExtra("score", bundle.getString("score"));
                it.putExtra("userUid", bundle.getString("userUid"));

                startActivity(it);
                finish();
            }
        });

    }

    private void setupMediaRecord() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSaved);
    }

    protected void onHit(String base64){
        Data data = new Data(); data.data = base64;

        Call<Data> service = ApiUtils.getCreateAudio().postAudio("application/json", data);

        service.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    try {
                        Data api_return = response.body();
                        transcript =  api_return.data;
                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

}
