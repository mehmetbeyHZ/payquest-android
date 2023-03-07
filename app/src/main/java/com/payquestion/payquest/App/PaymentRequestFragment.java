package com.payquestion.payquest.App;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.BanksAdapter;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Models.Bank;
import com.payquestion.payquest.Models.BanksResponse;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.R;
import com.payquestion.payquest.Storage.UserStorage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRequestFragment extends Fragment{
    ViewGroup view;
    Spinner spinner;
    ArrayList<Bank> bankArrayList;
    Button newPaymentRequest;
    EditText iban,quantity,register_name;
    LinearLayout bankSpinnerArea;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(0).setChecked(true);
        view = (ViewGroup) inflater.inflate(R.layout.app_payment_request,container,false);
        spinner = view.findViewById(R.id.bankSpinner);
        newPaymentRequest = view.findViewById(R.id.newPaymentRequest);
        iban = view.findViewById(R.id.iban);
        quantity = view.findViewById(R.id.quantity);
        bankSpinnerArea = view.findViewById(R.id.bankSpinnerArea);
        newPaymentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPaymentRequest();
            }
        });
        UserStorage storage = new UserStorage(getActivity());
        register_name = view.findViewById(R.id.register_name);
        register_name.setText(storage.getName());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initBanks();
        return view;
    }

    public void initBanks() {
        final LoadingDialog dialog = new LoadingDialog(getActivity());
        dialog.start();
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<BanksResponse> banks = api.getBanks();
        banks.enqueue(new Callback<BanksResponse>() {
            @Override
            public void onResponse(Call<BanksResponse> call, Response<BanksResponse> response) {
                if (response.isSuccessful())
                {
                    bankSpinnerArea.setVisibility(View.VISIBLE);
                    bankArrayList = (ArrayList<Bank>) response.body().getBanks();
                    BanksAdapter banksAdapter = new BanksAdapter(getActivity(), (ArrayList<Bank>) response.body().getBanks());
                    spinner.setAdapter(banksAdapter);
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                dialog.stop();
            }

            @Override
            public void onFailure(Call<BanksResponse> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                dialog.stop();

            }
        });
    }


    public void newPaymentRequest()
    {

        String mQuantity = quantity.getText().toString().trim();
        String mIban     = iban.getText().toString().trim();

        if (!(mQuantity.matches("") || mIban.matches("")))
        {

            final LoadingDialog dialog = new LoadingDialog(getActivity());
            dialog.start();
            ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
            Call<GenericResponse> call = api.newPaymentRequest(bankArrayList.get(spinner.getSelectedItemPosition()).getBankId(),Integer.parseInt(mQuantity),mIban);
            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.isSuccessful())
                    {
                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }else{
                        ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                        Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    dialog.stop();
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                    dialog.stop();
                }
            });
        }else{
            Toast.makeText(getActivity(),getString(R.string.empty_area),Toast.LENGTH_SHORT).show();
        }
    }

}
