package com.payquestion.payquest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.payquestion.payquest.Models.InformationResponse;
import com.payquestion.payquest.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    Context context;
    List<InformationResponse> mData;

    public InformationAdapter(List<InformationResponse> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_user_information_item, parent, false);
        return (new InformationAdapter.ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.infoTitle.setText(mData.get(position).getTitle());
        holder.infoDescription.setText(mData.get(position).getText());
        Glide.with(context).load(mData.get(position).getImage()).into(holder.infoImage);
        if (mData.get(position).getRoute() != null)
        {
            holder.infoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(position).getRoute()));
                        context.startActivity(myIntent);
                    }catch (Exception e){}
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView infoImage;
        TextView infoTitle, infoDescription;
        LinearLayout infoLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            infoImage = itemView.findViewById(R.id.infoImage);
            infoTitle = itemView.findViewById(R.id.infoTitle);
            infoDescription = itemView.findViewById(R.id.infoDescription);
            infoLayout = itemView.findViewById(R.id.infoLayout);
        }
    }
}
