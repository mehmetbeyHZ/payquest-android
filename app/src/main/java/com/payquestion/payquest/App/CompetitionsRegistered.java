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

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.CompetitionRegisteredAdapter;
import com.payquestion.payquest.Adapters.CompetitionsAdapter;
import com.payquestion.payquest.Adapters.EmptyRecyclerView;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Models.CompetitionListResponse;
import com.payquestion.payquest.Models.CompetitionRegisteredResponse;
import com.payquestion.payquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompetitionsRegistered extends Fragment {
    ViewGroup view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.app_competition_listing,container,false);

        getRegistered();

        return view;
    }

    public void getRegistered()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        ApiInterface api = (new ApiClient(getContext())).getClient().create(ApiInterface.class);

        Call<CompetitionListResponse> call = api.getCompetitionsRegistered();
        call.enqueue(new Callback<CompetitionListResponse>() {
            @Override
            public void onResponse(Call<CompetitionListResponse> call, Response<CompetitionListResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    CompetitionsAdapter competitionsAdapter = new CompetitionsAdapter(response.body().getCompetitions(),getContext());
                    EmptyRecyclerView recyclerView = view.findViewById(R.id.competition_recycler);
                    recyclerView.setAdapter(competitionsAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<CompetitionListResponse> call, Throwable t) {
                Toast.makeText(getContext(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });

    }

}
