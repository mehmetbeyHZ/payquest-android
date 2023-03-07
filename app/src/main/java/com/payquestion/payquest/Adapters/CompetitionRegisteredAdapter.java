package com.payquestion.payquest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.payquestion.payquest.Models.Competition;
import com.payquestion.payquest.Models.RegisteredCompetition;
import com.payquestion.payquest.R;

import java.util.List;

public class CompetitionRegisteredAdapter extends RecyclerView.Adapter<CompetitionRegisteredAdapter.ViewHolder> {

    public Context context;
    List<RegisteredCompetition> mData = null;

    public CompetitionRegisteredAdapter(List<RegisteredCompetition> mData, Context context)
    {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_competition_item,parent,false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Competition competition = mData.get(position).getCompetition();

        holder.competitionTitle.setText(competition.getCompetitionTitle());
        holder.competitionDescription.setText(competition.getCompetitionDescription());
        holder.reward.setText(competition.getAward());
        holder.registrationFee.setText(competition.getRegistrationFee());
        holder.totalRegistered.setText(competition.getTotalRegisterCount() + "/" + competition.getMaxUsers());
        holder.competitionDate.setText(competition.getStartDate());
        if (competition.getTotalRegisterCount() >= competition.getMaxUsers())
        {
            holder.registerCompetition.setVisibility(competition.getTotalRegisterCount() >= competition.getMaxUsers() ? View.GONE : View.VISIBLE);
        }

        if (competition.getCompetitionImage() != null)
        {
            Glide.with(context).load(competition.getCompetitionImage()).into(holder.competitionImage);
        }else{
            holder.competitionImageArea.setVisibility(View.GONE);
        }

        if (competition.getIsRegistered() == null)
        {
            holder.registerCompetition.setVisibility(View.VISIBLE);
        }else{
            holder.registerCompetition.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        Button registerCompetition;
        TextView competitionTitle, competitionDescription,totalRegistered,registrationFee,reward,competitionDate;
        ImageView competitionImage;
        LinearLayout competitionImageArea;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            registerCompetition = itemView.findViewById(R.id.registerCompetition);
            competitionTitle = itemView.findViewById(R.id.competitionTitle);
            competitionDescription = itemView.findViewById(R.id.competitionDescription);
            totalRegistered = itemView.findViewById(R.id.totalRegistered);
            registrationFee = itemView.findViewById(R.id.registrationFee);
            reward = itemView.findViewById(R.id.reward);
            competitionImage = itemView.findViewById(R.id.competitionImage);
            competitionImageArea = itemView.findViewById(R.id.competitionImageArea);
            competitionDate = itemView.findViewById(R.id.competitionDate);
        }
    }

}
