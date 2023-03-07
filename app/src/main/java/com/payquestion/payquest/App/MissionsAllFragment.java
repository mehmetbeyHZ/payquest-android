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
import com.payquestion.payquest.Adapters.QuestionsAdapter;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Models.CurrentQuestionsResponse;
import com.payquestion.payquest.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionsAllFragment extends Fragment implements QuestionsAdapter.QuestionListener{
    ViewGroup view;
    LoadingDialog loadingDialog;
    SwipeRefreshLayout refreshMissions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(1).setChecked(true);
        view = (ViewGroup) inflater.inflate(R.layout.app_missions_all,container,false);
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        refreshMissions = view.findViewById(R.id.refreshAllMissions);
        refreshMissions.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                allMissions();
            }
        });

        allMissions();
        return view;
    }

    public void allMissions()
    {
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<CurrentQuestionsResponse> call = api.getAllMissions();
        call.enqueue(new Callback<CurrentQuestionsResponse>() {
            @Override
            public void onResponse(Call<CurrentQuestionsResponse> call, Response<CurrentQuestionsResponse> response) {
                refreshMissions.setRefreshing(false);
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {

                    QuestionsAdapter questionsAdapter = new QuestionsAdapter(getActivity(),response.body().getQuestions(),MissionsAllFragment.this);
                    questionsAdapter.setShowPlay(false);
                    EmptyRecyclerView emptyRecyclerView = view.findViewById(R.id.missionsAllRecycler);
                    emptyRecyclerView.setAdapter(questionsAdapter);
                    emptyRecyclerView.setEmptyView(view.findViewById(R.id.noAllMission));
                    emptyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();

            }

            @Override
            public void onFailure(Call<CurrentQuestionsResponse> call, Throwable t) {
                loadingDialog.stop();
                refreshMissions.setRefreshing(false);
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void questionClick(int position) {
        Toast.makeText(getActivity(),getString(R.string.play_error),Toast.LENGTH_SHORT).show();
    }

}
