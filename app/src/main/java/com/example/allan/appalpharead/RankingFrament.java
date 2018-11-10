package com.example.allan.appalpharead;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.allan.appalpharead.Adapters.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RankingFrament extends Fragment {

    private ArrayList<String> titles, points;

    private Context context;
    RecyclerView rv;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        context = view.getContext();

        rv = view.findViewById(R.id.recycler_view_rank);

        titles = new ArrayList<>();
        points = new ArrayList<>();

        initList();

        return view;
    }

    private void initList(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProva = database.getReference("Users/");

        refProva.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot pSnapShot : ds.child("UserProfile").getChildren()){
                        titles.add(pSnapShot.child("nome").getValue().toString() + " " + pSnapShot.child("sobrenome").getValue().toString());
                        points.add(pSnapShot.child("score").getValue().toString());
                        initRecyclerView();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void initRecyclerView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titles, points, context);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(context));
    }

}
