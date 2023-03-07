package com.payquestion.payquest.App;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.MainActivity;
import com.payquestion.payquest.R;
import com.payquestion.payquest.Storage.UserStorage;

import java.io.IOException;

public class SettingsFragment extends Fragment {
    ViewGroup view;
    UserStorage storage;
    Button changePassword;
    TextView adidTextview;
    LoadingDialog loadingDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(3).setChecked(true);
        view =  (ViewGroup) inflater.inflate(R.layout.app_settings,container,false);
        storage = new UserStorage(getActivity());
        loadingDialog = new LoadingDialog(getActivity());
        adidTextview = view.findViewById(R.id.adidTextview);
        adidTextview.setText(storage.getAdId());
        LinearLayout logout = view.findViewById(R.id.logout);
        LinearLayout advertising = view.findViewById(R.id.advertising);
        advertising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAdvertisingID();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storage.saveUserToken(null);
                storage.resetStorage();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        Switch notifications = view.findViewById(R.id.notifications);
        notifications.setChecked(storage.getNotificationsEnabled());
        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                storage.setNotificationsEnabled(isChecked);
            }
        });

        return view;
    }

    public void initAdvertisingID()
    {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
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
                if (advertId != null)
                {
                    adidTextview.setText(advertId);
                }
                if (advertId != null && !storage.getAdId().equals(advertId))
                {
                    Toast.makeText(getContext(), "Reklam kimliğiniz değişti!", Toast.LENGTH_SHORT).show();
                    storage.setAdId(advertId);
                    long unixTime = System.currentTimeMillis() / 1000L;
                    storage.setAdIdTime(unixTime);
                }else{
                    Toast.makeText(getContext(), advertId, Toast.LENGTH_SHORT).show();
                }
            }
        };

        task.execute();
    }


}
