package com.example.allan.appalpharead.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allan.appalpharead.AnswerQuestionOne;
import com.example.allan.appalpharead.PaginaPrincipal;
import com.example.allan.appalpharead.R;
import com.example.allan.appalpharead.provas.Prova;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> lTitles = new ArrayList<>();
    private ArrayList<String> lPoints = new ArrayList<>();
    private ArrayList<String> lUid = new ArrayList<>();
    private ArrayList<Prova> lProva = new ArrayList<>();
    private String myScore;
    private Context context;
    private Boolean flag;

    public RecyclerViewAdapter(ArrayList<String> lTitles, ArrayList<String> lPoints, Context context) {
        this.lTitles = lTitles;
        this.lPoints = lPoints;
        this.context = context;
        this.flag = false;
    }

    public RecyclerViewAdapter(String myScore, ArrayList<Prova> lProva, ArrayList<String> lUid, ArrayList<String> lTitles, ArrayList<String> lPoints, Context context) {
        this.lTitles = lTitles;
        this.lPoints = lPoints;
        this.lProva = lProva;
        this.lUid = lUid;
        this.myScore = myScore;
        this.context = context;
        this.flag = true;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");

        viewHolder.title.setText(lTitles.get(i));
        viewHolder.points.setText(lPoints.get(i));
        viewHolder.rank.setText(String.valueOf(i + 1));

        if (i == 0) viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.medal_gold));
        if (i == 1) viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.medal_prata));
        if (i == 2) viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.medal_bronze));

        if (flag){
            viewHolder.make.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.do_it));
            viewHolder.make.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(context, AnswerQuestionOne.class);
                    it.putExtra("word1", lProva.get(i).get_q1().getW1());
                    it.putExtra("word2", lProva.get(i).get_q1().getW2());
                    it.putExtra("word3", lProva.get(i).get_q1().getW3());

                    it.putExtra("word", lProva.get(i).get_q2().getWord());

                    it.putExtra("picture", lProva.get(i).get_q3().getImage());
                    it.putExtra("name", lProva.get(i).get_q3().getName());

                    it.putExtra("frase", lProva.get(i).get_q4().getFrase());

                    it.putExtra("uid", lUid.get(i));
                    it.putExtra("userScore", myScore);

                    it.putExtra("score", String.valueOf(lProva.get(i).getScore()));
                    it.putExtra("title", lProva.get(i).getQuestionTitle());
                    it.putExtra("userUid", lProva.get(i).getUserUid());

                    context.startActivity(it);
                }
            });
        }else {
            viewHolder.make.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.nothig));
        }


    }

    @Override
    public int getItemCount() {
        return lTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, points, rank;
        ImageView image;
        Button make;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            points = itemView.findViewById(R.id.points_rank);
            rank = itemView.findViewById(R.id.rank);
            image = itemView.findViewById(R.id.image_rank);
            make = itemView.findViewById(R.id.btn_DO_IT);
        }
    }

}
