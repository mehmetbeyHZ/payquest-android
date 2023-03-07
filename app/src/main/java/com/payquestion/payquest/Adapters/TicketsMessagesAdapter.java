package com.payquestion.payquest.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.payquestion.payquest.Models.ThreadMessage;
import com.payquestion.payquest.R;

import java.util.List;

public class TicketsMessagesAdapter extends RecyclerView.Adapter<TicketsMessagesAdapter.ViewHolder> {


    List<ThreadMessage> mData;
    Context context;
    public TicketsMessagesAdapter(List<ThreadMessage> threadMessages, Context context)
    {
        this.mData = threadMessages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_message_item,parent,false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int isAdmin = mData.get(position).getSenderIsAdmin();
        holder.messageText.setText(mData.get(position).getMessage());
        holder.ticketCreatedAt.setText(mData.get(position).getCreatedAt());
        if (isAdmin == 1)
        {

            LinearLayout.LayoutParams lp  = (new LinearLayout.LayoutParams(600,ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.START));
            lp.setMargins(0,(position == 0) ? 15 : 0,0,15);
            holder.messageLayout.setLayoutParams(lp);
            holder.messageLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.custom_box));
            holder.messageText.setTextColor(Color.parseColor("#000000"));
            holder.ticketCreatedAt.setTextColor(Color.parseColor("#000000"));

        }else{
            @SuppressLint("RtlHardcoded") LinearLayout.LayoutParams lp  = (new LinearLayout.LayoutParams(600,ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.RIGHT));
            lp.gravity = Gravity.RIGHT;
            lp.setMargins(0,(position == 0) ? 15 : 0,0,15);
            holder.messageLayout.setLayoutParams(lp);
            holder.messageLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.custom_box_blue));
            holder.messageText.setTextColor(Color.parseColor("#ffffff"));
            holder.ticketCreatedAt.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout messageLayout;
        TextView messageText,ticketCreatedAt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageLayout = itemView.findViewById(R.id.messageLayout);
            messageText = itemView.findViewById(R.id.messageText);
            ticketCreatedAt = itemView.findViewById(R.id.ticketCreatedAt);
        }

    }


}
