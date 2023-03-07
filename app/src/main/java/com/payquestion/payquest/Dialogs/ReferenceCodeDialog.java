package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferenceCodeDialog {
    Activity activity;
    Dialog dialog;
    EditText code;
    public ReferenceCodeDialog(Activity activity){
        this.activity = activity;
    }

    public void show()
    {

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_enter_reference);
        dialog.show();
        code = dialog.findViewById(R.id.refCode);
        final Button confirmCode = dialog.findViewById(R.id.confirmCode);
        confirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCode();
            }
        });

    }



    public void confirmCode()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(activity);
        String refCode = code.getText().toString().trim();
        if (refCode.matches(""))
        {
            Toast.makeText(activity,activity.getString(R.string.empty_area),Toast.LENGTH_SHORT).show();
            return;
        }

        loadingDialog.start();
        ApiInterface api = (new ApiClient(activity)).getClient().create(ApiInterface.class);
        Call<GenericResponse> call = api.addReference(refCode);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),activity);
                    Toast.makeText(activity,collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                loadingDialog.stop();
                Toast.makeText(activity,activity.getString(R.string.request_error),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
