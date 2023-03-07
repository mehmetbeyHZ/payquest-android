package com.payquestion.payquest.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;

import com.payquestion.payquest.Storage.UserStorage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    Context context;
    private static final String api_url = "https://payquestion.com/api/";
    private static Retrofit retrofit = null;

    public ApiClient(Context context)
    {
        this.context = context;
    }

    public Retrofit getClient()
    {
        if(retrofit == null)
        {
            final UserStorage storage = new UserStorage(context);
            final SharedPreferences sharedPreferences = this.context.getSharedPreferences("com.payquestion.payquest",Context.MODE_PRIVATE);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60,TimeUnit.SECONDS);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + sharedPreferences.getString("bearer_token",null))
                            .addHeader("Accept","application/json")
                            .addHeader("device",Build.DEVICE)
                            .addHeader("model",Build.MODEL)
                            .addHeader("product",Build.PRODUCT)
                            .addHeader("brand",Build.BRAND)
                            .addHeader("sdk-int", String.valueOf(Build.VERSION.SDK_INT))
                            .addHeader("fcm-token", storage.getFCMToken())
                            .addHeader("app_version", String.valueOf(APIConst.BUILD_NUMBER))
                            .addHeader("adid",storage.getAdId())
                            .build();
                    return chain.proceed(request);
                }
            });

            retrofit = new Retrofit.Builder().baseUrl(api_url)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
