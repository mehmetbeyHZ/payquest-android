package com.payquestion.payquest.Authenticate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.MainActivity;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends Fragment {
    ViewGroup view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.auth_forgotten,container,false);

        TextView goLoginLabel = view.findViewById(R.id.go_login);
        TextView goToRegisterLabel = view.findViewById(R.id.go_register);
        goLoginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).goToLogin();
            }
        });
        goToRegisterLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).goToRegister();
            }
        });

        Button button = view.findViewById(R.id.forgot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doForgot();
            }
        });

        return view;
    }

    public void doForgot()
    {
        EditText forgot_email = view.findViewById(R.id.forgot_email);
        String mail = forgot_email.getText().toString().trim();
        if (mail.matches(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_area),Toast.LENGTH_SHORT).show();
            return;
        }
        final LoadingDialog dialog = new LoadingDialog(getActivity());
        dialog.start();
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<GenericResponse> forgot = api.forgotPassword(mail);
        forgot.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok")){
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }else{
                    ErrorCollection errorCollection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),errorCollection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                dialog.stop();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                dialog.stop();
            }
        });
    }
}
