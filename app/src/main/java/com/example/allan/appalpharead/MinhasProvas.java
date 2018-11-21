package com.example.allan.appalpharead;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.allan.appalpharead.Adapters.RecyclerViewAdapter;
import com.example.allan.appalpharead.Algoritms.QuickSortRankUsers;
import com.example.allan.appalpharead.Algoritms.RankUsers;
import com.example.allan.appalpharead.provas.Prova;
import com.example.allan.appalpharead.provas.QuestionOne;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MinhasProvas extends Activity {
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;

    private ArrayList<String> titles, points;
    private ArrayList<RankUsers> rank = new ArrayList<>();
    private ProgressBar prog;

    private ImageView img;
    private TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_provas);

        msg = findViewById(R.id.msg);
        img = findViewById(R.id.back);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        final String uid = mAuth.getUid();

        titles = new ArrayList<>();
        points = new ArrayList<>();
        prog = findViewById(R.id.progressMinhasProvas);
        prog.setVisibility(View.VISIBLE);

        DatabaseReference provasRef = mDatabase.getReference("/Users/"+uid+"/Provas/");

        provasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    RankUsers user = new RankUsers(ds.child("questionTitle").getValue().toString(), Integer.valueOf(ds.child("score").getValue().toString()));
                    rank.add(user);
                }
                if (rank.isEmpty()){
                    img.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.books));
                    msg.setText("Você não tem nenhuma atividade construida até agora.\n\nCrie uma atividade para que outras pessoas possam aprender com você!");
                }else {
                    QuickSortRankUsers ob = new QuickSortRankUsers();
                    rank = ob.sort(rank, 0, rank.size() - 1);
                    for (int i = rank.size() - 1; i >= 0; i--) {
                        titles.add(rank.get(i).getName());
                        points.add(String.valueOf(rank.get(i).getPoint()));
                    }
                    initRecyclerView();
                }
                prog.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void initRecyclerView(){
        RecyclerView rv = findViewById(R.id.recycler_view_minhas_provas);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titles, points, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

}
