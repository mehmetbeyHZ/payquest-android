package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.payquestion.payquest.Adapters.EmptyRecyclerView;
import com.payquestion.payquest.Adapters.MyReferencesAdapter;
import com.payquestion.payquest.Models.ReferenceDetail;
import com.payquestion.payquest.R;

import java.util.List;

public class ReferenceListDialog {
    Activity activity;
    Dialog dialog;
    List<ReferenceDetail> mData;
    public ReferenceListDialog(Activity activity, List<ReferenceDetail> mData)
    {
        this.activity = activity;
        this.mData = mData;
    }

    public void show()
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_my_references);
        dialog.show();

        MyReferencesAdapter adapter = new MyReferencesAdapter(mData,activity);
        EmptyRecyclerView emptyRecyclerView = dialog.findViewById(R.id.myReferencesRecycler);
        emptyRecyclerView.setAdapter(adapter);
        emptyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyRecyclerView.setEmptyView(dialog.findViewById(R.id.emptyRefData));
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        Button closeDialog = dialog.findViewById(R.id.closeDialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }



    public void hide()
    {
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }

}
