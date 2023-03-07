package com.payquestion.payquest.App;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.EmptyRecyclerView;
import com.payquestion.payquest.Adapters.QuestionsAdapter;
import com.payquestion.payquest.Adapters.WrapContentLinearLayoutManager;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Dialogs.PassTheWaitingDialog;
import com.payquestion.payquest.Dialogs.QuestionCustomDialog;
import com.payquestion.payquest.Dialogs.QuestionDialog;
import com.payquestion.payquest.Dialogs.SkipSecondsDialog;
import com.payquestion.payquest.Dialogs.SweetDialog;
import com.payquestion.payquest.Models.CurrentQuestionsResponse;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.Models.GetQuestion;
import com.payquestion.payquest.Models.Question;
import com.payquestion.payquest.Models.TakeNewResponse;
import com.payquestion.payquest.R;
import com.payquestion.payquest.Services.AlarmService;
import com.payquestion.payquest.Storage.UserStorage;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionsFragment extends Fragment implements QuestionsAdapter.QuestionListener, SweetDialog.DialogOkListener, SkipSecondsDialog.SkipCompletedListener {
    ViewGroup view;
    LinearLayout timerArea;
    QuestionsAdapter adapter;
    Button newMissions,reduceWaiting, skipSeconds;
    SwipeRefreshLayout refreshGame;
    private long mTimeLeftInMillis = 0;
    CountDownTimer countDownTimer;
    QuestionDialog questionDialog;
    QuestionCustomDialog questionCustomDialog;
    UserStorage storage;
    public int questionClickStorage = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(1).setChecked(true);
        view =  (ViewGroup) inflater.inflate(R.layout.app_missions,container,false);
        storage = new UserStorage(getContext());
        reduceWaiting = view.findViewById(R.id.reduceWaiting);
        List<Question> cachedQuestion = storage.getQuestions();

        if (cachedQuestion != null)
        {
            initAdapter(cachedQuestion);
        }else{
            initGame(true);
        }
        skipSeconds = view.findViewById(R.id.skipSeconds);
        newMissions = view.findViewById(R.id.takeNewMissions);
        refreshGame = view.findViewById(R.id.refreshGame);
        timerArea   = view.findViewById(R.id.timerLayout);
        refreshGame.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initGame(false);
            }
        });
        newMissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeNew();
            }
        });
        reduceWaiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new PassTheWaitingDialog(getActivity())).show();
            }
        });
        skipSeconds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipSecondsHandler();
            }
        });
        return view;
    }

    public void skipSecondsHandler()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        ApiInterface apiInterface = new ApiClient(getActivity()).getClient().create(ApiInterface.class);
        Call<GenericResponse> call = apiInterface.getDiamondInfo();
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    SkipSecondsDialog skipSecondsDialog = new SkipSecondsDialog(getActivity(),MissionsFragment.this);
                    skipSecondsDialog.show(response.body().getMessage());
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }

    public void initGame(final boolean withLoading)
    {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        if (withLoading)
        {
            loadingDialog.start();
        }
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<CurrentQuestionsResponse> call = api.getCurrentQuestions();
        call.enqueue(new Callback<CurrentQuestionsResponse>() {
            @Override
            public void onResponse(Call<CurrentQuestionsResponse> call, Response<CurrentQuestionsResponse> response) {
                if (response.isSuccessful())
                {
                    initAdapter(response.body().getQuestions());
                    storage.setQuestions(response.body().getQuestions());
                    refreshGame.setRefreshing(false);
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                if (withLoading) { loadingDialog.stop(); }
            }

            @Override
            public void onFailure(Call<CurrentQuestionsResponse> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                refreshGame.setRefreshing(false);
                if (withLoading) { loadingDialog.stop(); }
            }
        });
    }

    public void initAdapter(List<Question> questions)
    {
        if (adapter == null)
        {
            EmptyRecyclerView recyclerView = view.findViewById(R.id.missionsRecycler);
            adapter = new QuestionsAdapter(getActivity(),questions,MissionsFragment.this);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            recyclerView.setEmptyView(view.findViewById(R.id.noMission));
            recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            adapter.notifyDataSetChanged();
        }else{
            adapter.setMyData(questions);
            if (questions.size() == 0)
            {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void takeNew()
    {
        final LoadingDialog dialog = new LoadingDialog(getActivity());
        dialog.start();
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<TakeNewResponse> call = api.takeNewMissions();
        call.enqueue(new Callback<TakeNewResponse>() {
            @Override
            public void onResponse(Call<TakeNewResponse> call, Response<TakeNewResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    adapter = null;
                    initAdapter(response.body().getQuestions());
                    storage.setQuestions(response.body().getQuestions());
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                    if (collection.getRemains_second() > 0)
                    {

                        setAlarm(collection.getRemains_second());
                        if (countDownTimer == null)
                        {
                            runTimerArea(collection.getRemains_second());
                        }else{
                            countDownTimer.cancel();
                            runTimerArea(collection.getRemains_second());
                        }
                    }
                }
                dialog.stop();
            }

            @Override
            public void onFailure(Call<TakeNewResponse> call, Throwable t) {
                dialog.stop();
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void questionClick(int position) {

        if (storage.getAdIdResetTimeSystem() && ((System.currentTimeMillis() / 1000L) - storage.getAdIdTime()) >= storage.getAdIdResetTime()){
            Toast.makeText(getContext(),"Ayarlardan reklam kimliğinizi sıfırlayın.",Toast.LENGTH_LONG).show();
            return;
        }

        questionClickStorage = position;
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        questionDialog = new QuestionDialog(getActivity(),this);
        questionCustomDialog = new QuestionCustomDialog(getActivity(),this);

        Question question = adapter.mData.get(position);
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<GetQuestion> call = api.getQuestion(question.getMissionHandleId());
        call.enqueue(new Callback<GetQuestion>() {
            @Override
            public void onResponse(Call<GetQuestion> call, Response<GetQuestion> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    if (response.body().getType() == 0)
                    {
                        questionDialog.start(response.body());
                    }else{
                        questionCustomDialog.start(response.body());
                    }
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                    if (collection.getMessage().equals("Bu soruyu zamanında cevaplayamadınız."))
                    {
                        List<Question> mQ = storage.getQuestions();
                        mQ.remove(questionClickStorage);
                        storage.setQuestions(mQ);
                        initAdapter(storage.getQuestions());
                    }
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<GetQuestion> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });
    }


    private void runTimerArea(final int remains)
    {
        timerArea.setVisibility(View.VISIBLE);
        final TextView time = view.findViewById(R.id.remainsSecond);
        time.setText(getDurationString(remains));
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(remains * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                time.setText(getDurationString((int) millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerArea.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Yeni görev alabilirsiniz!",Toast.LENGTH_SHORT).show();
                time.setText(getDurationString(0));
                countDownTimer = null;
            }
        }.start();
    }


    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    private int[] getDurationINTS(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return new int[] {hours,minutes,seconds};
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public void setAlarm(int extraSeconds)
    {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,extraSeconds);

        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.get(Calendar.SECOND));
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void callbackApp() {
        if (questionClickStorage > -1)
        {
            List<Question> mQ = storage.getQuestions();
            mQ.remove(questionClickStorage);
            storage.setQuestions(mQ);
            initAdapter(mQ);
        }else{
            initGame(false);
        }
        if (questionDialog != null)
        {
            questionDialog.stop();
        }
        if (questionCustomDialog != null)
        {
            questionCustomDialog.stop();
        }
    }

    @Override
    public void skipCompleted() {
        takeNew();
    }
}
