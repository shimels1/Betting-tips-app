package com.shime.betpowersports;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{
    private List<Result> results;


    public ResultAdapter(List<Result> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.time.setText(String.valueOf(result.getTime()));
        holder.league.setText(String.valueOf(result.getLeague()));
        holder.club1.setText(String.valueOf(result.getClub1()));
        holder.club2.setText(String.valueOf(result.getClub2()));
        holder.club1_score.setText(result.getClub1_score() + " : " + result.getClub2_score());
        holder.prediction.setText(String.valueOf(result.getPrediction()));
        holder.odd.setText(String.valueOf(result.getOdd()));
//        holder.resultImage.setBackgroundResource(R.drawable.baseline_check_circle_24);

        if (result.getResult().toString().toLowerCase().equals("true")){
            holder.resultImage.setCompoundDrawablesWithIntrinsicBounds(0,  0,R.drawable.baseline_check_circle_24,0 );
        }else if(result.getResult().toString().toLowerCase().equals("false")){
            holder.resultImage.setCompoundDrawablesWithIntrinsicBounds(0,  0,R.drawable.baseline_cancel_24,0 );
        }else{
            holder.resultImage.setCompoundDrawablesWithIntrinsicBounds(0,  0,R.drawable.baseline_access_time_24,0 );
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView league;
        public TextView club1;
        public TextView club2;
        public TextView club1_score;
        public TextView prediction;
        public TextView odd;
        public TextView resultImage;

        public ViewHolder(@NonNull View resultView) {
            super(resultView);
            time = resultView.findViewById(R.id.time);
            league = resultView.findViewById(R.id.league);
            club1 = resultView.findViewById(R.id.club1);
            club2 = resultView.findViewById(R.id.club2);
            club1_score = resultView.findViewById(R.id.club1_score);
            odd = resultView.findViewById(R.id.odd);
            prediction = resultView.findViewById(R.id.prediction);
            resultImage = resultView.findViewById(R.id.result);
        }
    }
}
