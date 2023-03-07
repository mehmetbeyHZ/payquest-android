package com.payquestion.payquest.Authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class Register extends Fragment {
    ViewGroup view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = (ViewGroup) inflater.inflate(R.layout.auth_register,container,false);

        TextView loginLabel = view.findViewById(R.id.login);
        loginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).goToLogin();
            }
        });

        Button button = view.findViewById(R.id.doRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        return view;
    }

    public void doRegister()
    {
        EditText register_name = view.findViewById(R.id.register_name);
        EditText register_email = view.findViewById(R.id.register_email);
        EditText register_phone_number = view.findViewById(R.id.register_phone_number);
        EditText register_password = view.findViewById(R.id.register_password);
        EditText register_re_password = view.findViewById(R.id.register_re_password);
        EditText reference_code = view.findViewById(R.id.reference_code);

        String name = register_name.getText().toString().trim();
        String email = register_email.getText().toString().trim();
        String phone_number = register_phone_number.getText().toString().trim();
        String password = register_password.getText().toString().trim();
        String re_password = register_re_password.getText().toString().trim();
        String ref_code = reference_code.getText().toString().trim();

        if (name.matches("") || email.matches("") || phone_number.matches("") || password.matches("") || re_password.matches(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_area),Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.matches(re_password))
        {
            Toast.makeText(getActivity(),getString(R.string.password_not_matches),Toast.LENGTH_SHORT).show();
            return;
        }

        RadioGroup radioButtonGroup = view.findViewById(R.id.register_gender);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int gender = radioButtonGroup.indexOfChild(radioButton);

        if (gender == -1)
        {
            Toast.makeText(getActivity(),getString(R.string.please_select_gender),Toast.LENGTH_SHORT).show();
            return;
        }

        final LoadingDialog dialog = new LoadingDialog(getActivity());
        dialog.start();
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<LoginResponse> login = api.register(name,email,password,re_password,phone_number,gender + 1,ref_code);
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
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                dialog.stop();
            }
        });

    }
}
