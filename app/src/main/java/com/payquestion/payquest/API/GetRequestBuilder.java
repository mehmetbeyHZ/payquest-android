package com.payquestion.payquest.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.payquestion.payquest.Storage.UserStorage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRequestBuilder {
    Context context;
    private static Retrofit retrofit = null;
    private List<String> headers;

    public GetRequestBuilder(List<String> headers, Context context)
    {
        this.context = context;
        this.headers = headers;
    }

    public Retrofit getClient()
    {
        if(retrofit == null)
        {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60,TimeUnit.SECONDS);
            if (headers.size() > 0)
            {
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder request = chain.request().newBuilder();

                        for (int i = 0; i < headers.size(); i++)
                        {
                            String headerItem = headers.get(i);
                            String[] headerItems = headerItem.split(":");
                            if (headerItems.length > 2)
                            {
                                request.addHeader(headerItems[0],headerItems[1]);
                            }
                        }

                        return chain.proceed(request.build());
                    }
                });
            }

            retrofit = new Retrofit.Builder().baseUrl("https://payqyestion.com")
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
