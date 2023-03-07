package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.payquestion.payquest.R;
import com.payquestion.payquest.Storage.UserStorage;

public class ReferenceMyCodeDialog {
    Activity activity;
    Dialog dialog;
    EditText code;
    UserStorage userStorage;
    public ReferenceMyCodeDialog(Activity activity){
        this.activity = activity;
        userStorage = new UserStorage(activity);
    }

    public void show()
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_my_ref);
        dialog.show();
        TextView myRefCode = dialog.findViewById(R.id.myRefCode);
        myRefCode.setText(userStorage.getRefCode());
        Button closeDialog = dialog.findViewById(R.id.closeDialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        Button copyCode = dialog.findViewById(R.id.copyCode);
        copyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyCode();
            }
        });
    }

    public void copyCode()
    {

        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("REF CODE", userStorage.getRefCode());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(activity,activity.getString(R.string.code_copied),Toast.LENGTH_SHORT).show();
    }

    public void hide()
    {
        dialog.dismiss();
    }
}
