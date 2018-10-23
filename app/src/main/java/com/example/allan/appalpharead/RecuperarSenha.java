package com.example.allan.appalpharead;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuth;

public class RecuperarSenha extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button retrieveBtn;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.Email_recuperar_senha);

        final Button RecuperarSenha = findViewById(R.id.RecuperarSenha_recuperar_senha);
        RecuperarSenha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                retrievePassword();
                AlertDialog.Builder alert = new AlertDialog.Builder(RecuperarSenha.this);
                alert.setTitle("Aviso");
                alert
                        .setMessage("Sua senha foi enviada para o e-mail cadastrado!")
                        .setIcon(R.drawable.notification);
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        Button voltar = findViewById(R.id.Voltar_registrar);
        voltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
            }
        });
    }

    public void retrievePassword(){
        String emailStr = email.getText().toString();
        mAuth.sendPasswordResetEmail(emailStr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RecuperarSenha.this, "Email com instruções para criação de nova senha enviado!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RecuperarSenha.this, MainActivity.class));
                }
            }
        });
    }

}
