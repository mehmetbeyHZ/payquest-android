package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.youtube.player.YouTubeIntents;
import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.API.GetRequestBuilder;
import com.payquestion.payquest.API.GetRequestInterface;
import com.payquestion.payquest.App.MissionsFragment;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Models.AnswerQuestionResponse;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.Models.GetQuestion;
import com.payquestion.payquest.Models.ReportResponse;
import com.payquestion.payquest.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionCustomDialog extends DialogFragment {
    Activity activity;
    MissionsFragment context;
    Dialog dialog;
    GetQuestion question;
    LoadingDialog loadingDialog;
    ProgressBar progressBar;
    TextView loadingText;
    CountDownTimer countDownTimer;
    Button questionCompleted;

    public QuestionCustomDialog(Activity mActivity, MissionsFragment context) {
        this.activity = mActivity;
        this.context = context;
        this.loadingDialog = new LoadingDialog(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer = null;
        }
    }

    public void start(final GetQuestion question) {
        this.question = question;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_question_custom);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                countDownTimer.cancel();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                countDownTimer.cancel();
            }
        });
        progressBar = dialog.findViewById(R.id.questionLoadingProgress);
        loadingText = dialog.findViewById(R.id.loadingText);
        startCountTime(question.getRemainsAnswerSec());


        TextView questionRealText = dialog.findViewById(R.id.questionRealText);
        questionRealText.setText(question.getMissionQuestion());

        questionCompleted = dialog.findViewById(R.id.questionCompleted);
        Button openIntentFromApp = dialog.findViewById(R.id.openIntentFromApp);
        openIntentFromApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initType();
                questionCompleted.setVisibility(View.VISIBLE);
            }
        });

        questionCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionCompleted.setEnabled(false);
                questionCompleted.setText("Kontrol Ediliyor...");
                if (question.getScrapeLink() == null)
                {
                    answerCustomMission();
                }else{
                    answerWithCount();
                }
            }
        });
        if (question.getScrapeLink() != null)
        {
            scrapeItem(question.getScrapeLink(),false);
        }
    }

    public void startCountTime(final int remainsAnswerSec) {
        countDownTimer = new CountDownTimer(remainsAnswerSec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                double completedSecond = remainsAnswerSec - (int) millisUntilFinished / 1000;
                double refKitKat = Double.valueOf(remainsAnswerSec);
                double calc = ((double) (completedSecond / refKitKat)) * 100;
                progressBar.setProgress((int) Math.round(calc));
                loadingText.setText(String.valueOf((int)remainsAnswerSec - (int)completedSecond));
            }

            @Override
            public void onFinish() {
                SweetDialog sweetDialog = new SweetDialog(activity,context);
                sweetDialog.endTime();
                setTimeExpired();
                stop();
            }
        }.start();
    }

    public void setTimeExpired() {
        ApiInterface api = (new ApiClient((activity))).getClient().create(ApiInterface.class);
        Call<GenericResponse> call = api.expiredMission(question.getMissionId());
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });
    }
    public void scrapeItem(final String scrapeLink, final boolean afterAnswer)
    {
        List<String> item = new ArrayList<>();
        GetRequestInterface api = (new GetRequestBuilder(item, context.getActivity())).getClient().create(GetRequestInterface.class);
        Call<ResponseBody> call = api.get(scrapeLink);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        reportResponse(response.body().string(),scrapeLink,afterAnswer);
                    } catch (IOException e) {
                        if (afterAnswer)
                        {
                            answerCustomMission();
                        }
                    }
                }else{
                    if (afterAnswer)
                    {
                        answerCustomMission();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (afterAnswer)
                {
                    answerCustomMission();
                }
            }
        });
    }

    public void reportResponse(String response, String url, final boolean afterAnswer)
    {
        ApiInterface api = (new ApiClient(activity)).getClient().create(ApiInterface.class);
        Call<ReportResponse> call = api.reportResponse(question.getMissionId(),response,url, afterAnswer ? 2 : 1);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful())
                {
                    System.out.println(response.body().getCount());
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),activity);
                    Toast.makeText(activity,collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                if (afterAnswer)
                {
                    answerCustomMission();
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                if (afterAnswer)
                {
                    answerCustomMission();
                }
            }
        });
    }


    public void answerWithCount()
    {
        scrapeItem(question.getScrapeLink(),true);
    }

    public void answerCustomMission()
    {
        loadingDialog.start();
        final SweetDialog sweetDialog = new SweetDialog(activity,context);

        ApiInterface api = (new ApiClient(activity)).getClient().create(ApiInterface.class);
        Call<AnswerQuestionResponse> call = api.answerQuestion(question.getMissionId(),0);
        call.enqueue(new Callback<AnswerQuestionResponse>() {
            @Override
            public void onResponse(Call<AnswerQuestionResponse> call, Response<AnswerQuestionResponse> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();

                    switch (response.body().getMessage()) {
                        case "Özel görevin onaylandı!":
                            sweetDialog.dialogInit(R.drawable.gift, response.body().getMessage());
                            break;
                        case "Özel görev başarısız!":
                            sweetDialog.dialogInit(R.drawable.close, response.body().getMessage());
                            break;
                        default:
                            sweetDialog.looking();
                            break;
                    }

                }else{
                    sweetDialog.error();
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),activity);
                    Toast.makeText(activity,collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<AnswerQuestionResponse> call, Throwable t) {
                Toast.makeText(activity,activity.getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
                questionCompleted.setText("BU GÖREVİ TAMAMLANDIM");
            }
        });
    }

    public void stop()
    {
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }

    public void initType()
    {
        if (question.getType() == 1)
        {
            Intent intent = YouTubeIntents.createChannelIntent(activity,question.getIntentLink());
            activity.startActivity(intent);
        }else if(question.getType() == 2 || question.getType() == 3 ||  question.getType() == 4){
            Intent intent = YouTubeIntents.createPlayVideoIntent(activity,question.getIntentLink());
            activity.startActivity(intent);
        }else if (question.getType() == 5){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id="+question.getIntentLink()));
            activity.startActivity(intent);
        }else{

            try{
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(question.getIntentLink()));
                context.startActivity(myIntent);
            }catch (Exception e){
                Toast.makeText(activity,activity.getString(R.string.update_app),Toast.LENGTH_SHORT).show();

            }
        }
    }

}
