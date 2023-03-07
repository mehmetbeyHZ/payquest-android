package com.payquestion.payquest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.payquestion.payquest.Models.Bank;
import com.payquestion.payquest.R;

import java.util.ArrayList;

public class BanksAdapter extends ArrayAdapter<Bank>
{

    public BanksAdapter(Context context, ArrayList<Bank> bankList){
        super(context, 0, bankList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rc_spinner_bank_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.bankName);
        Bank currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getBankName());
        }
        return convertView;
    }

}
