package com.example.allan.appalpharead;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilFrament extends Fragment {

    private FirebaseAuth mAuth;
    private String uid;

    private Button btnMinhasProvas;
    private TextView name, score;

    private Context context;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        context = view.getContext();

        btnMinhasProvas =  view.findViewById(R.id.btn_minhas_provas_perfil);
        name = view.findViewById(R.id.name_perfil);
        score = view.findViewById(R.id.score);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();

        final FirebaseUser user = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProva = database.getReference("Users/"+uid+"/UserProfile/");

        refProva.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    name.setText(ds.child("nome").getValue().toString() + " " + ds.child("sobrenome").getValue().toString());
                    score.setText(ds.child("score").getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        verificationPermissions();

        btnMinhasProvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), MinhasProvas.class);
                startActivity(it);
            }
        });

        return view;
    }

    private void verificationPermissions(){
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }
}
