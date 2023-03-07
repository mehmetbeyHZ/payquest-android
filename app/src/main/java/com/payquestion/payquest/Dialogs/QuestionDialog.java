package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.QuestionAnswerAdapter;
import com.payquestion.payquest.App.MissionsFragment;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Models.AnswerQuestionResponse;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.Models.GetQuestion;
import com.payquestion.payquest.R;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDialog extends DialogFragment implements QuestionAnswerAdapter.AnswerListener, IUnityAdsListener {
    Activity activity;
    MissionsFragment context;
    Dialog dialog;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    GetQuestion question;
    String token = null;
    InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    SweetDialog sweetDialog;


    private String unityGameID = "3849833";
    private Boolean testMode = false;
    private String placementId = "QuestionAnswered";


    public QuestionDialog(Activity mActivity, MissionsFragment context) {
        this.activity = mActivity;
        this.context = context;
        sweetDialog = new SweetDialog(activity, context);
    }

    public void start(GetQuestion question) {

        MobileAds.initialize(activity, "ca-app-pub-6931530475516594~8492120657");
        // Use an activity context to get the rewarded video instance.
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId("ca-app-pub-6931530475516594/5815131631");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //Add the listener to the SDK:
        UnityAds.addListener(QuestionDialog.this);
        // Initialize the SDK:
        UnityAds.initialize(activity, unityGameID, false);

        this.question = question;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_question);
        dialog.show();
        progressBar = dialog.findViewById(R.id.questionLoadingProgress);
        startCountTime(question.getRemainsAnswerSec());
        RecyclerView recyclerView = dialog.findViewById(R.id.questionAnswersRecycler);
        QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(question.getMissionAnswers(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));

        TextView qText = dialog.findViewById(R.id.questionRealText);
        qText.setText(question.getMissionQuestion());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer = null;
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
            }

            @Override
            public void onFinish() {
                setTimeExpired();
                sweetDialog.endTime();
                stop();
            }
        }.start();
    }

    public void stop() {
        if (dialog != null) {
            dialog.dismiss();
        }

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


    @Override
    public void answerClick(int position) {
        countDownTimer.cancel();
        final LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.start();

        ApiInterface api = (new ApiClient(activity)).getClient().create(ApiInterface.class);
        Call<AnswerQuestionResponse> call = api.answerQuestion(question.getMissionId(), position);
        call.enqueue(new Callback<AnswerQuestionResponse>() {
            @Override
            public void onResponse(Call<AnswerQuestionResponse> call, Response<AnswerQuestionResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok")) {
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    token = response.body().getToken();

                    final LoadingDialog myd = new LoadingDialog(activity);
                    myd.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (UnityAds.isReady(placementId)) {
                                UnityAds.show(activity, placementId);
                            } else {
                                giveMoney();
                                ((AppActivity) activity).showCong();
                            }
                            myd.stop();
                        }
                    }, 3000);

                    sweetDialog.success();

                } else {
                    final LoadingDialog myd = new LoadingDialog(activity);
                    myd.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }
                            myd.stop();
                        }
                    }, 3000);


                    sweetDialog.error();
                    ErrorCollection collection = new ErrorCollection(response.errorBody(), getActivity());
                    Toast.makeText(activity, collection.getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<AnswerQuestionResponse> call, Throwable t) {
                Toast.makeText(activity, context.getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
                stop();
            }
        });
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-6931530475516594/2654945391", new AdRequest.Builder().build());
    }


    public void giveMoney() {
        ApiInterface apiInterface = (new ApiClient(activity)).getClient().create(ApiInterface.class);
        Call<GenericResponse> call = apiInterface.checkPaymentToken(token);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().getMessage());
                } else {
                    ErrorCollection collection = new ErrorCollection(response.errorBody(), getActivity());
                    System.out.println(collection.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onUnityAdsReady(String s) {
    }

    @Override
    public void onUnityAdsStart(String s) {
    }

    @Override
    public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
        ((AppActivity) activity).showCong();
        if (finishState.equals(UnityAds.FinishState.COMPLETED)) {
            giveMoney();
        } else if (finishState.equals(UnityAds.FinishState.SKIPPED)) {

        } else if (finishState.equals(UnityAds.FinishState.ERROR)) {
            // Log an error.
        }
    }

    @Override
    public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
        ((AppActivity) activity).showCong();
    }
}
