package com.payquestion.payquest.App;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.InformationAdapter;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.Models.InformationResponse;
import com.payquestion.payquest.R;
import com.payquestion.payquest.Storage.UserStorage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportNewThreadFragment extends Fragment {
    ViewGroup view;
    EditText threadTitle, threadMessage;
    Button createNewThread;
    UserStorage userStorage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(0).setChecked(true);
        view = (ViewGroup) inflater.inflate(R.layout.app_support_new_ticket,container,false);
        userStorage = new UserStorage(getActivity());
        threadTitle = view.findViewById(R.id.threadTitle);
        threadMessage = view.findViewById(R.id.threadMessage);
        createNewThread = view.findViewById(R.id.createNewThread);
        createNewThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newThread();
            }
        });

        if (userStorage.getInformations() != null)
        {
            initInfoRecycler(userStorage.getTicketInformation());
        }
        information();

        return view;
    }

    public void newThread()
    {
        String title = threadTitle.getText().toString().trim();
        String message = threadMessage.getText().toString().trim();

        if (title.matches("") || message.matches(""))
        {
            Toast.makeText(getContext(),getString(R.string.empty_area),Toast.LENGTH_SHORT).show();
            return;
        }
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        ApiInterface apiClient = (new ApiClient(getContext()).getClient()).create(ApiInterface.class);
        Call<GenericResponse> call = apiClient.createThread(title,message);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    ((AppActivity)getActivity()).onBackPressed();
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getContext(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(getContext(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });
    }


    public void initInfoRecycler(List<InformationResponse> mData)
    {
        RecyclerView recyclerView = view.findViewById(R.id.informationRecycler);
        InformationAdapter adapter = new InformationAdapter(mData,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void information()
    {
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<List<InformationResponse>> call = api.getTicketInformation();
        call.enqueue(new Callback<List<InformationResponse>>() {
            @Override
            public void onResponse(Call<List<InformationResponse>> call, Response<List<InformationResponse>> response) {
                if (response.isSuccessful())
                {
                    userStorage.setTicketInformation(response.body());
                    initInfoRecycler(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<InformationResponse>> call, Throwable t) {

            }
        });

    }

}
