package com.payquestion.payquest.App;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.EmptyRecyclerView;
import com.payquestion.payquest.Adapters.PaymentRequestsAdapter;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Models.PaymentRequestResponse;
import com.payquestion.payquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRequestAll extends Fragment {
    ViewGroup view;
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.app_payment_request_all,container,false);
        swipeRefreshLayout = view.findViewById(R.id.refreshAllPayments);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPayments(false);
            }
        });
        getPayments(true);
        return view;
    }

    public void getPayments(final boolean withDialog)
    {
        final LoadingDialog dialog = new LoadingDialog(getActivity());
        if (withDialog)
        {
            dialog.start();
        }
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<PaymentRequestResponse> call = api.getAllPaymentRequests();
        call.enqueue(new Callback<PaymentRequestResponse>() {
            @Override
            public void onResponse(Call<PaymentRequestResponse> call, Response<PaymentRequestResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    EmptyRecyclerView recyclerView = view.findViewById(R.id.paymentsRecycler);
                    PaymentRequestsAdapter paymentRequestsAdapter = new PaymentRequestsAdapter(getActivity(),response.body().getPaymentRequests());
                    recyclerView.setAdapter(paymentRequestsAdapter);
                    recyclerView.setEmptyView(view.findViewById(R.id.noPaymentRequest));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                if (withDialog)
                {
                    dialog.stop();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PaymentRequestResponse> call, Throwable t) {
                if (withDialog)
                {
                    dialog.stop();
                }
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
