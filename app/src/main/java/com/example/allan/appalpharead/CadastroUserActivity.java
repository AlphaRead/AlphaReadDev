package com.example.allan.appalpharead;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.allan.appalpharead.models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUserActivity extends AppCompatActivity {

    Button submit;
    EditText username, lastname;

    FirebaseDatabase database;
    FirebaseAuth mAuth;

    String uid, name, lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_user);

        submit = findViewById(R.id.btnUserprofile);
        username = findViewById(R.id.edtUsername);
        lastname = findViewById(R.id.edtLastname);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUserProfile();
            }
        });


    }

    public void submitUserProfile(){
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();

        name = username.getText().toString().trim();
        lname = lastname.getText().toString().trim();

        DatabaseReference users = database.getReference("/Users/"+uid+"/UserProfile");

        UserProfile user_insert = new UserProfile(name, lname);
        users.child(uid).setValue(user_insert);

        startActivity(new Intent(CadastroUserActivity.this, PaginaPrincipal.class));
    }
}
