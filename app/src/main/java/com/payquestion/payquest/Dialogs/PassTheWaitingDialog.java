package com.payquestion.payquest.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassTheWaitingDialog implements RewardedVideoAdListener {
    Activity activity;
    private RewardedVideoAd mRewardedVideoAd;
    Dialog dialog;
    public String token = null;

    public PassTheWaitingDialog(Activity activity)
    {
        this.activity = activity;
        MobileAds.initialize(activity, "ca-app-pub-6931530475516594~8492120657");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd("ca-app-pub-6931530475516594/2654945391", new AdRequest.Builder().build());
    }

    public void show()
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_pass_waiting);
        dialog.show();
        final Button showVideo = dialog.findViewById(R.id.showVideo);
        showVideo.setClickable(false);
        showVideo.setEnabled(false);
        CountDownTimer countDownTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showVideo.setText(String.valueOf( (millisUntilFinished / 1000)) + " saniye");
            }

            @Override
            public void onFinish() {
                showVideo.setText("İzle");
                showVideo.setEnabled(true);
                showVideo.setClickable(true);
            }
        };
        countDownTimer.start();

        Button closeDialog = dialog.findViewById(R.id.closeDialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        showVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewableAd();
            }
        });

    }

    public void rewardedChecker()
    {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }else{
            Toast.makeText(activity.getApplicationContext(),"Henüz Reklam Yok!",Toast.LENGTH_SHORT).show();
        }
    }

    public void viewableAd()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.start();
        ApiInterface apiInterface = new ApiClient(activity).getClient().create(ApiInterface.class);
        Call<GenericResponse> call = apiInterface.createDiamondToken();
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    token = response.body().getMessage();
                    rewardedChecker();
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),activity);
                    Toast.makeText(activity,collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(activity,activity.getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });
    }

    public void hide()
    {
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        hide();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        final LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.start();
        ApiInterface api = new ApiClient(activity).getClient().create(ApiInterface.class);
        Call<GenericResponse> call = api.checkDiamondToken(token);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("ok"))
                {
                    DiamondRewardedDialog dr = new DiamondRewardedDialog(activity);
                    dr.start(response.body().getMessage());
                    hide();
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),activity);
                    Toast.makeText(activity,collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(activity,activity.getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}
