package com.payquestion.payquest.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.payquestion.payquest.Models.ThreadsList;
import com.payquestion.payquest.R;

import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> {

    List<ThreadsList> mData;
    OpenMessageListener openMessageListener;

    public TicketsAdapter(List<ThreadsList> threadsLists, OpenMessageListener openMessageListener)
    {
        this.mData = threadsLists;
        this.openMessageListener = openMessageListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_ticket_item,parent,false);
        return (new TicketsAdapter.ViewHolder(view,openMessageListener));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = mData.get(position).getThreadTitle();
        String created = mData.get(position).getCreatedAt();
        int is_closed = mData.get(position).getIsClosed();

        holder.ticketTitle.setText(title);
        holder.ticketCreated.setText(created);

        if (is_closed == 1) {
            holder.ticketStatus.setText("Cevaplandı ve Kapatıldı");
        } else {
            holder.ticketStatus.setText("İnceleniyor");
        }
        if(mData.get(position).getUnseenCount() > 0){
            holder.unseenArea.setVisibility(View.VISIBLE);
            holder.unseenArea.setText(mData.get(position).getUnseenCount() + " Okunmamış mesaj");
        }else{
            holder.unseenArea.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ticketTitle,ticketCreated,ticketStatus,unseenArea;
        LinearLayout ticketLayout;
        public OpenMessageListener openMessageListener;
        public ViewHolder(@NonNull View itemView,OpenMessageListener openMessageListener) {
            super(itemView);
            this.openMessageListener = openMessageListener;
            ticketLayout = itemView.findViewById(R.id.ticketLayout);
            ticketTitle = itemView.findViewById(R.id.ticketTitle);
            ticketCreated = itemView.findViewById(R.id.ticketCreated);
            ticketStatus = itemView.findViewById(R.id.ticketStatus);
            unseenArea = itemView.findViewById(R.id.unseenArea);
            ticketLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            openMessageListener.clickMessage(getAdapterPosition());
        }
    }

    public interface OpenMessageListener
    {
        void clickMessage(int position);

    }

}
