package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.App.MissionsFragment;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkipSecondsDialog {

    Activity activity;
    Dialog dialog;
    public SkipCompletedListener skipCompletedListener;
    public SkipSecondsDialog(Activity activity, MissionsFragment missionsFragment)
    {
        this.activity = activity;
        skipCompletedListener = (SkipCompletedListener) missionsFragment;
    }

    public void show(String message)
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_skip_seconds);
        dialog.show();

        TextView dialogMessage = dialog.findViewById(R.id.skipDialogMessage);
        dialogMessage.setText(message);
        Button closeDialog = dialog.findViewById(R.id.closeDialog);
        Button confirmDialog = dialog.findViewById(R.id.confirmSkip);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipSeconds();
            }
        });
    }

    public void skipSeconds()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.start();
        ApiInterface apiInterface = new ApiClient(activity).getClient().create(ApiInterface.class);
        Call<GenericResponse> call = apiInterface.skipSeconds();
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    skipCompletedListener.skipCompleted();
                    hide();
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),activity);
                    Toast.makeText(activity,collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(activity,activity.getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
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

    public interface SkipCompletedListener{
        void skipCompleted();
    }

}
