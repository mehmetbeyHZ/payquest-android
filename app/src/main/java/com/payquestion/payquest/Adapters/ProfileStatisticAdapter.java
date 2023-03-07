package com.payquestion.payquest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.payquestion.payquest.Models.Statistic;
import com.payquestion.payquest.R;

import java.util.List;

public class ProfileStatisticAdapter extends RecyclerView.Adapter<ProfileStatisticAdapter.ViewHolder> {

    List<Statistic> mData;
    Context context;

    public ProfileStatisticAdapter(List<Statistic> mData, Context context)
    {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_statistic_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Statistic statistic = mData.get(position);

        holder.statisticProgress.setProgress(statistic.getProgress());
        holder.statisticLabel.setText(statistic.getLabel());
        holder.statisticText.setText(statistic.getText());
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView statisticText,statisticLabel;
        ProgressBar statisticProgress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statisticLabel = itemView.findViewById(R.id.statisticLabel);
            statisticText = itemView.findViewById(R.id.statisticText);
            statisticProgress = itemView.findViewById(R.id.statisticProgress);
        }
    }

}
