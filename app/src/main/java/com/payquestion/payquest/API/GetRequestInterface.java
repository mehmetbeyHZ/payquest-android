package com.payquestion.payquest.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetRequestInterface {
    @GET
    Call<ResponseBody> get(@Url String url);
}
