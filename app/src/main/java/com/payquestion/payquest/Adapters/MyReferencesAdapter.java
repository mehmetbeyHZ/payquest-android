package com.payquestion.payquest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.payquestion.payquest.Models.ReferenceDetail;
import com.payquestion.payquest.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyReferencesAdapter extends RecyclerView.Adapter<MyReferencesAdapter.ViewHolder> {
    List<ReferenceDetail> mData;
    Context context;
    public MyReferencesAdapter(List<ReferenceDetail> mData,Context context)
    {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_reference_user_item,parent,false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.refName.setText(mData.get(position).getUserInfo().getName());
        holder.refCreated.setText(mData.get(position).getCreatedAt());

        holder.verified_profile.setVisibility(mData.get(position).getUserInfo().getIsVerified() == 1 ? View.VISIBLE : View.GONE);
        holder.verified_phone.setVisibility(mData.get(position).getUserInfo().getPhoneVerifiedAt() != null ? View.VISIBLE : View.GONE);


        Glide.with(context).load(mData.get(position).getUserInfo().getAvatar()).into(holder.refAvatar);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView refAvatar;
        ImageView verified_profile, verified_phone;
        TextView refName,refCreated;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verified_phone = itemView.findViewById(R.id.verified_phone);
            verified_profile = itemView.findViewById(R.id.verified_profile);
            refAvatar = itemView.findViewById(R.id.refAvatar);
            refName = itemView.findViewById(R.id.refName);
            refCreated = itemView.findViewById(R.id.refCreated);
        }
    }
}
