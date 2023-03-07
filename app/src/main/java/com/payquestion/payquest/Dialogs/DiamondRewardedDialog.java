package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.payquestion.payquest.R;

public class DiamondRewardedDialog {
    Activity activity;
    Dialog dialog;

    public DiamondRewardedDialog(Activity activity)
    {
        this.activity = activity;
    }

    public void start(String message)
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_diamond_confirmed);
        dialog.show();
        TextView dialogMessage = dialog.findViewById(R.id.dialogMessage);
        dialogMessage.setText(message);
        Button button = dialog.findViewById(R.id.okDialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
    }

    public void stop()
    {
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }

}
