package com.payquestion.payquest.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
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
import com.payquestion.payquest.Classes.TimeConverter;
import com.payquestion.payquest.Models.Competition;
import com.payquestion.payquest.Models.CompetitionListResponse;
import com.payquestion.payquest.R;

import java.util.ArrayList;
import java.util.List;

public class CompetitionsAdapter extends RecyclerView.Adapter<CompetitionsAdapter.ViewHolder> {
    List<Competition> competitions;
    Context context;

    public CompetitionsAdapter(List<Competition> competitions, Context context)
    {
        this.competitions = competitions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_competition_item,parent,false);
        return (new ViewHolder(view));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Competition competition = competitions.get(position);
        TimeConverter timeConverter = new TimeConverter(competition.getStartDate());

        holder.competitionTitle.setText(competition.getCompetitionTitle());
        holder.competitionDescription.setText(competition.getCompetitionDescription());
        holder.reward.setText(competition.getAward());
        holder.registrationFee.setText(competition.getRegistrationFee());
        holder.totalRegistered.setText(competition.getTotalRegisterCount() + "/" + competition.getMaxUsers());

        holder.competitionDate.setText(timeConverter.getDay() +" "+ timeConverter.getMonth() +" "+ timeConverter.getDayName() + " " +timeConverter.getHour());

//
//
//        holder.registerCompetition.setVisibility(competition.getIsRegistered() == null ? View.VISIBLE : View.GONE);
//        holder.registerCompetition.setVisibility(competition.getTotalRegisterCount() >= competition.getMaxUsers() ? View.GONE : View.VISIBLE);
//        holder.registerCompetition.setVisibility(competition.getCanRegister() == 2  ? View.GONE : View.VISIBLE);

        if (competition.getIsRegistered() != null || (competition.getTotalRegisterCount() >= competition.getMaxUsers()) || competition.getCanRegister() == 2)
        {
            holder.registerCompetition.setVisibility(View.GONE);
        }else{
            holder.registerCompetition.setVisibility(View.VISIBLE);
        }

        if (competition.getCompetitionImage() != null)
        {
            Glide.with(context).load(competition.getCompetitionImage()).into(holder.competitionImage);
        }else{
            holder.competitionImageArea.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {
        return this.competitions != null ? this.competitions.size() : 0;
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
