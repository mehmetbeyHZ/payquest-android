package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.payquestion.payquest.R;

public class LoadingDialog {

    Activity activity;
    Dialog dialog;
    public LoadingDialog(Activity mActivity)
    {
        this.activity = mActivity;
    }

    public void start()
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.show();
    }

    public void stop()
    {
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }
}
