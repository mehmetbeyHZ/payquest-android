package com.payquestion.payquest.App;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.EmptyRecyclerView;
import com.payquestion.payquest.Adapters.TicketsAdapter;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.MainActivity;
import com.payquestion.payquest.Models.ThreadsList;
import com.payquestion.payquest.Models.ThreadsListResponse;
import com.payquestion.payquest.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportFragment extends Fragment implements TicketsAdapter.OpenMessageListener {
    ViewGroup view;
    EmptyRecyclerView threadsRecycler;
    List<ThreadsList> mData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity) getActivity()).navigationView.getMenu().getItem(0).setChecked(true);
        view = (ViewGroup) inflater.inflate(R.layout.app_support_tickets, container, false);
        threadsRecycler = view.findViewById(R.id.threadsRecycler);
        Button createNewThread = view.findViewById(R.id.createNewThread);
        createNewThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity)getActivity()).goSupportNewThreadFragment();
            }
        });

        getThreads();
        return view;
    }

    public void getThreads() {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        ApiInterface api = (new ApiClient(getActivity()).getClient()).create(ApiInterface.class);
        Call<ThreadsListResponse> call = api.getThreads();
        call.enqueue(new Callback<ThreadsListResponse>() {
            @Override
            public void onResponse(Call<ThreadsListResponse> call, Response<ThreadsListResponse> response) {
                if (response.isSuccessful()) {
                    mData = response.body().getThreadsList();
                    TicketsAdapter ticketsAdapter = new TicketsAdapter(mData,SupportFragment.this);
                    threadsRecycler.setAdapter(ticketsAdapter);
                    threadsRecycler.setEmptyView(view.findViewById(R.id.emptyTicketData));
                    threadsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    ErrorCollection collection = new ErrorCollection(response.errorBody(), getActivity());
                    Toast.makeText(getContext(), collection.getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<ThreadsListResponse> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });
    }

    @Override
    public void clickMessage(int position) {
        ((AppActivity)getActivity()).goSupportChatFragment(mData.get(position).getThreadId(),mData.get(position).getThreadTitle());

    }
}
