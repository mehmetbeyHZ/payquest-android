package com.payquestion.payquest.Authenticate;

import android.content.Intent;
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
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.MainActivity;
import com.payquestion.payquest.Models.LoginResponse;
import com.payquestion.payquest.R;
import com.payquestion.payquest.Storage.UserStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Fragment {
    ViewGroup view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.auth_login,container,false);

        TextView register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).goToRegister();
            }
        });

        TextView getHelp = view.findViewById(R.id.get_help);
        getHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).goToForgotPassword();
            }
        });

        Button button = view.findViewById(R.id.do_authenticate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        return view;
    }

    private void doLogin()
    {
        EditText login_email = view.findViewById(R.id.login_email);
        String email = login_email.getText().toString().trim();
        EditText login_password = view.findViewById(R.id.login_password);
        String password = login_password.getText().toString().trim();

        if (email.matches("") || password.matches(""))
        {
            Toast.makeText(getActivity(),"Boş alan bıraktınız",Toast.LENGTH_SHORT).show();
            return;
        }

        final LoadingDialog dialog = new LoadingDialog(getActivity());
        dialog.start();

        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<LoginResponse> login = api.login(email,password);
        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    (new UserStorage(getActivity())).saveUserToken(response.body().getUser().getToken());

                    Intent intent = new Intent(getActivity(), AppActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }else{
                    ErrorCollection errorCollection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),errorCollection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                dialog.stop();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                dialog.stop();
            }
        });


    }
}
