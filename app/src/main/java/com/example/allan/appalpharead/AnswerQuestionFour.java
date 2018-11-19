package com.example.allan.appalpharead;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.UUID;

public class AnswerQuestionFour extends Activity {

    private Button avancar, gravar, play;
    private Bundle bundle;
    private Context context;
    private TextView frase;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private String pathSaved = "";
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
                            + UUID.randomUUID().toString()+"_audio_record.3gp";

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

}
