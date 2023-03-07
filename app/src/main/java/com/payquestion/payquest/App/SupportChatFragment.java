package com.payquestion.payquest.App;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.EmptyRecyclerView;
import com.payquestion.payquest.Adapters.TicketsMessagesAdapter;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.Models.ThreadMessage;
import com.payquestion.payquest.Models.ThreadMessageResponse;
import com.payquestion.payquest.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportChatFragment extends Fragment {
    private int threadID;
    String title;
    public SupportChatFragment(int questionId, String title)
    {
        this.threadID = questionId;
        this.title = title;
    }

    ViewGroup view;
    EmptyRecyclerView messagesRecycler;
    List<ThreadMessage> mData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(0).setChecked(true);
        ((AppActivity)getActivity()).navigationView.setVisibility(View.GONE);
        ((AppActivity)getActivity()).actionBarWrapper.setVisibility(View.GONE);


        RelativeLayout relativeLayout = ((AppActivity)getActivity()).appRelativeContent;
        RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)relativeLayout.getLayoutParams();
        relativeParams.setMargins(0, 0, 0, 0);  // left, top, right, bottom
        relativeLayout.setLayoutParams(relativeParams);

        view = (ViewGroup) inflater.inflate(R.layout.app_support_chat,container,false);
        messagesRecycler = view.findViewById(R.id.messagesRecycler);

        ImageView imageView = view.findViewById(R.id.sendMessage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendMessage();
            }
        });

        initChat();
        ImageView goBack = view.findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity)getActivity()).onBackPressed();
            }
        });

        TextView titleText = view.findViewById(R.id.titleText);
        titleText.setText(title);
        return view;
    }

    public void initChat()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        ApiInterface api = (new ApiClient(getContext())).getClient().create(ApiInterface.class);
        Call<ThreadMessageResponse> call = api.getMessages(threadID);
        call.enqueue(new Callback<ThreadMessageResponse>(){
            @Override
            public void onResponse(Call<ThreadMessageResponse> call, Response<ThreadMessageResponse> response) {
                if (response.isSuccessful())
                {
                    mData = response.body().getThreadMessages();
                    TicketsMessagesAdapter ticketsMessagesAdapter = new TicketsMessagesAdapter(response.body().getThreadMessages(),getContext());
                    messagesRecycler.setAdapter(ticketsMessagesAdapter);
                    messagesRecycler.setEmptyView(view.findViewById(R.id.noMessagesData));
                    messagesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    messagesRecycler.scrollToPosition(response.body().getThreadMessages().size() - 1);

                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getContext(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<ThreadMessageResponse> call, Throwable t) {
                Toast.makeText(getContext(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });
    }


    public void appendMessage()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        final EditText chatText = view.findViewById(R.id.chatText);
        String message = chatText.getText().toString().trim();

        if (message.matches(""))
        {
            Toast.makeText(getContext(),getString(R.string.empty_area),Toast.LENGTH_SHORT).show();
            return;
        }

        ApiInterface api = (new ApiClient(getContext()).getClient()).create(ApiInterface.class);
        Call<GenericResponse> call = api.createMessage(threadID,message);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful())
                {
                    initChat();
                    chatText.setText("");
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppActivity)getActivity()).navigationView.setVisibility(View.VISIBLE);
        RelativeLayout relativeLayout = ((AppActivity)getActivity()).appRelativeContent;
        RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)relativeLayout.getLayoutParams();
        relativeParams.setMargins(0, 0, 0, 120);  // left, top, right, bottom
        relativeLayout.setLayoutParams(relativeParams);
        ((AppActivity)getActivity()).actionBarWrapper.setVisibility(View.VISIBLE);

    }


}
