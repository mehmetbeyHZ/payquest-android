package com.payquestion.payquest.App;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.ProfileStatisticAdapter;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.Dialogs.LoadingDialog;
import com.payquestion.payquest.Dialogs.ReferenceCodeDialog;
import com.payquestion.payquest.Dialogs.ReferenceListDialog;
import com.payquestion.payquest.Dialogs.ReferenceMyCodeDialog;
import com.payquestion.payquest.Models.ProfileInfoDetail;
import com.payquestion.payquest.Models.ReferencesResponse;
import com.payquestion.payquest.Models.Statistic;
import com.payquestion.payquest.Models.UserInfoResponse;
import com.payquestion.payquest.R;
import com.payquestion.payquest.Storage.UserStorage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

public class ProfileFragment extends Fragment {
    ViewGroup view;
    UserStorage userStorage;
    TextView authLevel,authDiamond;
    SwipeRefreshLayout swipeRefreshProfile;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(2).setChecked(true);
        view =  (ViewGroup) inflater.inflate(R.layout.app_profile,container,false);
        userStorage = new UserStorage(getActivity());
        authLevel = view.findViewById(R.id.authLevel);
        authDiamond = view.findViewById(R.id.authDiamond);
        swipeRefreshProfile = view.findViewById(R.id.swipeRefreshProfile);
        swipeRefreshProfile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProfileInfo();
            }
        });
        Button button = view.findViewById(R.id.checkStatistic);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfileInfo();
            }
        });

        initStatisticAdapter(userStorage.getStatistic());

        Button editProfile = view.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity)getActivity()).goProfileEditFragment();
            }
        });

        LinearLayout enterRefCode = view.findViewById(R.id.enterRefCode);
        enterRefCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReferenceCodeDialog referenceCodeDialog = new ReferenceCodeDialog(getActivity());
                referenceCodeDialog.show();
            }
        });

        LinearLayout openMyRefCode = view.findViewById(R.id.openMyRefCode);
        openMyRefCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReferenceMyCodeDialog referenceMyCodeDialog = new ReferenceMyCodeDialog(getActivity());
                referenceMyCodeDialog.show();
            }
        });

        LinearLayout myReferences = view.findViewById(R.id.myReferences);
        myReferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRefList();
            }
        });

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (userStorage.getStatistic() == null)
        {
            getProfileInfo();
        }

        initStorageInfo();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               return view;
    }


    public void initStorageInfo()
    {
        try{
            CircleImageView avatar = view.findViewById(R.id.userProfileAvatar);
            Glide.with(getActivity()).load(userStorage.getAvatar()).into(avatar);
            TextView currentBalance = view.findViewById(R.id.currentBalance);
            currentBalance.setText(userStorage.getBalance() + " ₺");
            authLevel.setText(userStorage.getLevel() + " Level");
            authDiamond.setText(String.valueOf(userStorage.getDiamond()));
        }catch (Exception e) { }
    }

    public void initStatisticAdapter(List<Statistic> data)
    {
        userStorage.setStatistic(data);
        ProfileStatisticAdapter adapter = new ProfileStatisticAdapter(data,getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.statisticRecycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getProfileInfo()
    {
        final ProgressBar loadingStatistic = view.findViewById(R.id.loadingStatistic);
        loadingStatistic.setVisibility(View.VISIBLE);

        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<ProfileInfoDetail> call = api.getProfile();
        call.enqueue(new Callback<ProfileInfoDetail>() {
            @Override
            public void onResponse(Call<ProfileInfoDetail> call, Response<ProfileInfoDetail> response) {
                if (response.isSuccessful())
                {
                    UserInfoResponse info = response.body().getUser();
                    userStorage.setIsVerified(info.getIsVerified());
                    userStorage.setDiamond(info.getDiamond());
                    userStorage.saveDirect(String.valueOf(info.getBalance()),
                            info.getAvatar(),
                            info.getName(),
                            info.getEmail(),
                            info.getPhoneNumber(),
                            info.getXp(),
                            info.getRefCode(),
                            info.getLevel());
                    initStorageInfo();
                    initStatisticAdapter(response.body().getStatistic());

                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingStatistic.setVisibility(View.GONE);
                swipeRefreshProfile.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ProfileInfoDetail> call, Throwable t) {
                swipeRefreshProfile.setRefreshing(false);
                loadingStatistic.setVisibility(View.GONE);
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initRefList()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.start();
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<ReferencesResponse> call = api.getReferences();
        call.enqueue(new Callback<ReferencesResponse>() {
            @Override
            public void onResponse(Call<ReferencesResponse> call, Response<ReferencesResponse> response) {
                if (response.isSuccessful())
                {
                    ReferenceListDialog referenceListDialog = new ReferenceListDialog(getActivity(),response.body().getReferences());
                    referenceListDialog.show();
                }else{
                    ErrorCollection collection = new ErrorCollection(response.errorBody(),getActivity());
                    Toast.makeText(getActivity(),collection.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loadingDialog.stop();
            }

            @Override
            public void onFailure(Call<ReferencesResponse> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.request_error),Toast.LENGTH_SHORT).show();
                loadingDialog.stop();
            }
        });
    }



}
