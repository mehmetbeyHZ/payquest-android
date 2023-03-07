package com.payquestion.payquest.App;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.payquestion.payquest.API.ApiClient;
import com.payquestion.payquest.API.ApiInterface;
import com.payquestion.payquest.Adapters.InformationAdapter;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.Collections.ErrorCollection;
import com.payquestion.payquest.MainActivity;
import com.payquestion.payquest.Models.InformationResponse;
import com.payquestion.payquest.Models.UserInfoResponse;
import com.payquestion.payquest.R;
import com.payquestion.payquest.Storage.UserStorage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ViewGroup view;
    UserStorage userStorage;
    CircleImageView avatar;
    TextView name, email, authBalance, levelText;
    LinearLayout activityGoProfile;
    public SwipeRefreshLayout swipeRefreshLayout;
    AdLoader adLoader;
    ImageView is_verified_profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity) getActivity()).navigationView.getMenu().getItem(0).setChecked(true);
        view = (ViewGroup) inflater.inflate(R.layout.app_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refreshHome);
        is_verified_profile = view.findViewById(R.id.is_verified_profile);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyInfo();
                information();
            }
        });
        userStorage = (new UserStorage(getActivity()));
        avatar = view.findViewById(R.id.userAvatar);
        name = view.findViewById(R.id.authName);
        email = view.findViewById(R.id.authEmail);
        authBalance = view.findViewById(R.id.authBalance);
        activityGoProfile = view.findViewById(R.id.activityGoProfile);
        levelText = view.findViewById(R.id.levelText);

        createBanner();

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity)getActivity()).goProfileFragment();
            }
        });
        levelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity)getActivity()).goProfileFragment();

            }
        });

        activityGoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity) getActivity()).goProfileFragment();
            }
        });



        initInfoRecycler(userStorage.getInformations());
        getMyInfo();
        information();
        initUserInfoArea();

        LinearLayout missionsPage = view.findViewById(R.id.missionsPage);
        missionsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity) getActivity()).goMissionsFragment();
            }
        });

        LinearLayout allMissionsList = view.findViewById(R.id.allMissionsList);
        allMissionsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity) getActivity()).goMissionsAllFragment();
            }
        });

        LinearLayout goPaymentRequest = view.findViewById(R.id.goPaymentRequest);
        goPaymentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity) getActivity()).goPaymentFragment();
            }
        });

        LinearLayout paymentRequests = view.findViewById(R.id.paymentRequests);
        paymentRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity) getActivity()).goPaymentRequestAllFragment();
            }
        });


        LinearLayout support = view.findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppActivity) getActivity()).goSupportFragment();
            }
        });


        return view;
    }


    public void initUserInfoArea() {
        try {
            Glide.with(getActivity()).load(userStorage.getAvatar()).into(avatar);
            name.setText(userStorage.getName());
            email.setText(userStorage.getEmail());
            levelText.setText(userStorage.getLevel());
            authBalance.setText(userStorage.getBalance() + " ₺");
            is_verified_profile.setVisibility(userStorage.getIsVerified() == 1 ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
        }
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
        Call<List<InformationResponse>> call = api.getInformation();
        call.enqueue(new Callback<List<InformationResponse>>() {
            @Override
            public void onResponse(Call<List<InformationResponse>> call, Response<List<InformationResponse>> response) {
                if (response.isSuccessful())
                {
                    userStorage.setInformation(response.body());
                    initInfoRecycler(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<InformationResponse>> call, Throwable t) {

            }
        });

    }

    public void getMyInfo() {
        ApiInterface api = (new ApiClient(getActivity())).getClient().create(ApiInterface.class);
        Call<UserInfoResponse> call = api.getUserInfo();
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if (response.isSuccessful()) {
                    UserInfoResponse info = response.body();
                    userStorage.setIsVerified(info.getIsVerified());
                    userStorage.setDiamond(info.getDiamond());
                    userStorage.saveDirect(String.valueOf(info.getBalance()),
                            info.getAvatar(),
                            info.getName(),
                            info.getEmail(),
                            info.getPhoneNumber(),
                            info.getXp(),
                            info.getRefCode(), info.getLevel());
                    initUserInfoArea();
                } else {
                    ErrorCollection collection = new ErrorCollection(response.errorBody(), getActivity());
                    Toast.makeText(getActivity(), collection.getMessage(), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    public void createBanner() {

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        adLoader = new AdLoader.Builder(getActivity(), "ca-app-pub-6931530475516594/7081957754").forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                loadNativeAdd();
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder()
                .build())
                .build();

    }

    private void loadNativeAdd()
    {
        adLoader.loadAd(new AdRequest.Builder().build());

    }
}
