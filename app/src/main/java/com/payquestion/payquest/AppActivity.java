package com.payquestion.payquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.payquestion.payquest.API.APIConst;
import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.API.GetRequestBuilder;
import com.payquestion.payquest.API.GetRequestInterface;
import com.payquestion.payquest.App.CompetitionListFragment;
import com.payquestion.payquest.App.EditProfileFragment;
import com.payquestion.payquest.App.HomeFragment;
import com.payquestion.payquest.App.MissionsAllFragment;
import com.payquestion.payquest.App.MissionsFragment;
import com.payquestion.payquest.App.PaymentRequestAll;
import com.payquestion.payquest.App.PaymentRequestFragment;
import com.payquestion.payquest.App.ProfileFragment;
import com.payquestion.payquest.App.SettingsFragment;
import com.payquestion.payquest.App.SupportChatFragment;
import com.payquestion.payquest.App.SupportFragment;
import com.payquestion.payquest.App.SupportNewThreadFragment;
import com.payquestion.payquest.Models.AppUpdateResponse;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.Storage.UserStorage;
import com.plattysoft.leonids.ParticleSystem;
import com.unity3d.services.core.device.AdvertisingId;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppActivity extends AppCompatActivity {
    public BottomNavigationView navigationView;
    public UserStorage userStorage;
    public RelativeLayout appRelativeContent;
    public LinearLayout actionBarWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        actionBarWrapper = findViewById(R.id.actionBarWrapper);
        initNavListener();
        userStorage = (new UserStorage(this));
        String token = userStorage.getToken();
        appRelativeContent = findViewById(R.id.appRelativeContent);
        generateMyFirebaseToken();
        hasAppUpdate();
        if (token == null) {
            this.finish();
        }
        goHomeFragment();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        initAdvertisingID();
    }

    public void showCong() {
        new ParticleSystem(this, 80, R.drawable.first_cong, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(findViewById(R.id.emiter_top_left), 8, 5000);

        new ParticleSystem(this, 80, R.drawable.second_cong, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(findViewById(R.id.emiter_top_left), 8, 5000);
    }

    public void initNavListener() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        goHomeFragment();
                        return true;
                    case R.id.menu_missions:
                        goMissionsFragment();
                        return true;
                    case R.id.menu_profile:
                        goProfileFragment();
                        return true;
                    case R.id.menu_settings:
                        goSettingsFragment();
                        return true;
                }
                return false;
            }
        });
    }

    public void goHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new HomeFragment()).commit();
    }

    public void goMissionsFragment() {
        navigationView.getMenu().getItem(1).setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new MissionsFragment()).commit();
    }

    public void goCompetitionListFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new CompetitionListFragment()).commit();
    }

    public void goMissionsAllFragment() {
        navigationView.getMenu().getItem(0).setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MissionsAllFragment missionsAllFragment = new MissionsAllFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, missionsAllFragment).commit();
    }

    public void goProfileFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new ProfileFragment()).commit();
    }

    public void goProfileEditFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new EditProfileFragment()).commit();
    }

    public void goSettingsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new SettingsFragment()).commit();

    }

    public void goPaymentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new PaymentRequestFragment()).commit();
    }

    public void goPaymentRequestAllFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new PaymentRequestAll()).commit();
    }

    public void goSupportFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new SupportFragment()).commit();
    }


    public void goSupportNewThreadFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new SupportNewThreadFragment()).commit();
    }

    public void goSupportChatFragment(int questionId,String title)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.appFrame, new SupportChatFragment(questionId,title)).commit();
    }

    private boolean isFirstBackPressed = false;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 1) {
            super.onBackPressed();
        } else {
            if (isFirstBackPressed && getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
            } else {
                isFirstBackPressed = true;
                Toast.makeText(this, getString(R.string.double_logout), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isFirstBackPressed = false;
                    }
                }, 1500);
            }
        }
    }


    public void generateMyFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(AppActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String mToken = instanceIdResult.getToken();
                saveMyFCMToken(mToken);
            }
        });
    }

    public void saveMyFCMToken(final String token) {
        if (userStorage.getFCMToken() == null || !token.equals(userStorage.getFCMToken())) {
            ApiInterface api = (new ApiClient(this)).getClient().create(ApiInterface.class);
            Call<GenericResponse> call = api.registerFcmToken(token);
            call.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.isSuccessful()) {
                        userStorage.setFCMToken(token);
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
    
                }
            });
        }
    }

    public void initAdvertisingID()
    {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try{
                    advertId = idInfo.getId();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                return advertId;
            }

            @Override
            protected void onPostExecute(String advertId) {
                if (advertId != null && !userStorage.getAdId().equals(advertId))
                {
                    userStorage.setAdId(advertId);
                    long unixTime = System.currentTimeMillis() / 1000L;
                    userStorage.setAdIdTime(unixTime);
                }
            }
        };

        task.execute();

    }

    private void hasAppUpdate()
    {
        ApiInterface api = (new ApiClient(this)).getClient().create(ApiInterface.class);
        Call<AppUpdateResponse> call = api.getUpdate();
        call.enqueue(new Callback<AppUpdateResponse>() {
            @Override
            public void onResponse(Call<AppUpdateResponse> call, final Response<AppUpdateResponse> response) {
                if (response.isSuccessful())
                {

                    try{
                        userStorage.setAdIdResetTime(response.body().getAdvertisingSeconds());
                        userStorage.setAdIdResetTimeSystem(response.body().getAdvertisingResets());

                        System.out.println("ADRESET: SCNDS: " + response.body().getAdvertisingSeconds());
                        System.out.println("ADRESET: RESETS: " + response.body().getAdvertisingResets());

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (response.body().getBuildNumber() > APIConst.BUILD_NUMBER)
                    {

                        new AlertDialog.Builder(AppActivity.this)
                                .setTitle("Uygulamanızı güncelleyin")
                                .setMessage("Uygulamanın yeni bir sürümü mevcut. Google Play üzerinden güncelleyin.")
                                .setCancelable(response.body().getLockApp() == 1)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("market://details?id="+response.body().getIntentLink()));
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppUpdateResponse> call, Throwable t) {

            }
        });
    }

}