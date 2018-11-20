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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.allan.appalpharead.Algoritms.BuscaBinaria;
import com.example.allan.appalpharead.Algoritms.QuickSortName;
import com.example.allan.appalpharead.Algoritms.QuickSortRankUsers;
import com.example.allan.appalpharead.Algoritms.RankUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilFrament extends Fragment {

    private FirebaseAuth mAuth;
    private String uid;

    private Button btnMinhasProvas;
    private TextView name, score, ranking;
    private ArrayList<RankUsers> rank = new ArrayList<>();
    private ArrayList<RankUsers> rank2 = new ArrayList<>();

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
        ranking = view.findViewById(R.id.ranking);

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

        DatabaseReference refUsers = database.getReference("Users/");

        refUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot pSnapShot : ds.child("UserProfile").getChildren()){
                        rank.add(new RankUsers(pSnapShot.child("nome").getValue().toString() + " " + pSnapShot.child("sobrenome").getValue().toString(),
                                Integer.valueOf(pSnapShot.child("score").getValue().toString())));
                    }
                }
                QuickSortRankUsers ob = new QuickSortRankUsers();
                rank = ob.sort(rank, 0, rank.size()-1);
                for(int i = rank.size()-1, j=1; i>=0; i--, j++) rank2.add(new RankUsers(rank.get(i).getName(), j));
                QuickSortName names = new QuickSortName();
                rank2 = names.sort(rank2, 0,rank2.size()-1);
                BuscaBinaria bb = new BuscaBinaria();
                int x = bb.Binaria(name.getText().toString(), rank2);
                ranking.setText(String.valueOf(x));
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
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
            }, MY_CAMERA_REQUEST_CODE);
        }
    }
}
