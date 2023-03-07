package com.payquestion.payquest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.payquestion.payquest.Models.PaymentRequest;
import com.payquestion.payquest.R;

import java.util.List;

public class PaymentRequestsAdapter extends RecyclerView.Adapter<PaymentRequestsAdapter.ViewHolder> {

    Context context;
    List<PaymentRequest> mData;
    public PaymentRequestsAdapter(Context context, List<PaymentRequest> mData)
    {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_payment_request_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mData.get(position).getBank() != null){
            holder.paymentTitle.setText("Ödeme talebi - " + mData.get(position).getBank().getBankName());
        }else{
            holder.paymentTitle.setText("Ödeme talebi");
        }
        holder.paymentDate.setText("İstek Tarihi - " + mData.get(position).getCreatedAt());
        holder.ibanArea.setText("IBAN - " + mData.get(position).getIban());
        int status = mData.get(position).getIsConfirmed();

        if (status == 0)
        {
            holder.paymentDescription.setText("Ödeme talebiniz beklemede.");
        }else if(status == 1){
            holder.paymentDescription.setText("Ödeme talebiniz onaylandı.");
        }else if(status == 2) {
            holder.paymentDescription.setText("Ödeme talebiniz reddedildi.");
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView paymentTitle,paymentDescription,paymentDate,ibanArea;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ibanArea = itemView.findViewById(R.id.ibanArea);
            paymentTitle = itemView.findViewById(R.id.paymentTitle);
            paymentDescription = itemView.findViewById(R.id.paymentDescription);
            paymentDate = itemView.findViewById(R.id.paymentDate);
        }
    }
}
