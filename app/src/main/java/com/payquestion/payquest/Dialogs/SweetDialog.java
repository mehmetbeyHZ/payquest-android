package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.payquestion.payquest.App.MissionsFragment;
import com.payquestion.payquest.R;

public class SweetDialog {
    Activity activity;
    Dialog dialog;
    private DialogOkListener dialogOkListener;
    MissionsFragment context;
    public SweetDialog(Activity mActivity, MissionsFragment context)
    {
        this.activity = mActivity;
        this.context = context;
        dialogOkListener = (DialogOkListener) context;

    }

    private void start(int resources)
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(resources);
        dialog.show();
        Button button = dialog.findViewById(R.id.okDialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                dialogOkListener.callbackApp();
            }
        });


    }

    public void dialogInit(int resource,String description)
    {
        this.start(R.layout.dialog_status_confirmed);
        TextView textView = dialog.findViewById(R.id.dialogTitle);
        textView.setText(description);
        ImageView imageView = dialog.findViewById(R.id.dialogIcon);
        imageView.setImageResource(resource);
    }

    public void success()
    {
        this.start(R.layout.dialog_status_success);
    }

    public void error()
    {
        this.start(R.layout.dialog_status_error);
    }

    public void looking()
    {
        this.start(R.layout.dialog_status_looking);
    }

    public void endTime()
    {
        this.start(R.layout.dialog_status_timeout);
    }

    public void stop()
    {
        dialog.dismiss();
    }

    public interface DialogOkListener
    {
        void callbackApp();
    }
}
