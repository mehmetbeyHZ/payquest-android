package com.payquestion.payquest.API;

import com.payquestion.payquest.Models.AnswerQuestionResponse;
import com.payquestion.payquest.Models.AppUpdateResponse;
import com.payquestion.payquest.Models.BanksResponse;
import com.payquestion.payquest.Models.ChangeAvatarResponse;
import com.payquestion.payquest.Models.CompetitionListResponse;
import com.payquestion.payquest.Models.CompetitionRegisteredResponse;
import com.payquestion.payquest.Models.CurrentQuestionsResponse;
import com.payquestion.payquest.Models.GenericResponse;
import com.payquestion.payquest.Models.GetQuestion;
import com.payquestion.payquest.Models.InformationResponse;
import com.payquestion.payquest.Models.LoginResponse;
import com.payquestion.payquest.Models.PaymentRequestResponse;
import com.payquestion.payquest.Models.ProfileInfoDetail;
import com.payquestion.payquest.Models.ReferencesResponse;
import com.payquestion.payquest.Models.ReportResponse;
import com.payquestion.payquest.Models.TakeNewResponse;
import com.payquestion.payquest.Models.ThreadMessageResponse;
import com.payquestion.payquest.Models.ThreadsListResponse;
import com.payquestion.payquest.Models.UserInfoResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @GET("app-info")
    Call<AppUpdateResponse> getUpdate();


    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<LoginResponse> register(@Field("name") String name,@Field("email") String email, @Field("password") String password,@Field("repeat_password") String repeat_password,@Field("phone_number") String phone_number,@Field("gender") int gender,@Field("reference_code") String reference_code);

    @FormUrlEncoded
    @POST("register/fcm-token")
    Call<GenericResponse> registerFcmToken(@Field("token") String fcmToken);

    @FormUrlEncoded
    @POST("user/change-password")
    Call<GenericResponse> changePassword(@Field("old_password") String old_password, @Field("new_password") String new_password, @Field("confirm_password") String confirm_password,@Field("logout_other") String logout_other);

    @Multipart
    @POST("user/change-avatar")
    Call<ChangeAvatarResponse> uploadImage(@Part MultipartBody.Part image);

    @GET("user/information")
    Call<List<InformationResponse>> getInformation();

    @GET("support/information")
    Call<List<InformationResponse>> getTicketInformation();


    @FormUrlEncoded
    @POST("forgot")
    Call<GenericResponse> forgotPassword(@Field("email") String email);

    @GET("user/info")
    Call<UserInfoResponse> getUserInfo();

    @GET("user/profile")
    Call<ProfileInfoDetail> getProfile();

    @FormUrlEncoded
    @POST("user/add-reference")
    Call<GenericResponse> addReference(@Field("code") String code);

    @GET("user/references")
    Call<ReferencesResponse> getReferences();

    @GET("mission/current")
    Call<CurrentQuestionsResponse> getCurrentQuestions();

    @GET("mission/all")
    Call<CurrentQuestionsResponse> getAllMissions();

    @GET("mission/new")
    Call<TakeNewResponse> takeNewMissions();

    @FormUrlEncoded
    @POST("mission/expired")
    Call<GenericResponse> expiredMission(@Field("mission_id") int expiredid);

    @FormUrlEncoded
    @POST("mission/get")
    Call<GetQuestion> getQuestion(@Field("mission_id") int mission_id);

    @FormUrlEncoded
    @POST("mission/answer")
    Call<AnswerQuestionResponse> answerQuestion(@Field("mission_id") int mission_id, @Field("answer_index") int answer_index);

    @GET("banks")
    Call<BanksResponse> getBanks();

    @FormUrlEncoded
    @POST("payment-request")
    Call<GenericResponse> newPaymentRequest(@Field("bank_id") int bank_id,@Field("quantity") int quantity,@Field("iban") String iban);

    @GET("payment-request-all")
    Call<PaymentRequestResponse> getAllPaymentRequests();

    @FormUrlEncoded
    @POST("mission/payment-status")
    Call<GenericResponse> checkPaymentToken(@Field("token") String token);

    @GET("support/threads")
    Call<ThreadsListResponse> getThreads();

    @FormUrlEncoded
    @POST("support/thread-detail")
    Call<ThreadMessageResponse> getMessages(@Field("thread_id") int thread_id);

    @FormUrlEncoded
    @POST("support/create-thread")
    Call<GenericResponse> createThread(@Field("thread_title") String thread_title, @Field("thread_message") String message);

    @FormUrlEncoded
    @POST("support/create-message")
    Call<GenericResponse> createMessage(@Field("thread_id") int thread_id, @Field("message") String message);

    @GET("ads/diamond/info")
    Call<GenericResponse> getDiamondInfo();

    @GET("ads/diamond/create")
    Call<GenericResponse> createDiamondToken();

    @FormUrlEncoded
    @POST("ads/diamond/check")
    Call<GenericResponse> checkDiamondToken(@Field("token") String token);

    @GET("diamond/skip-seconds")
    Call<GenericResponse> skipSeconds();

    @GET("competitions/all")
    Call<CompetitionListResponse> getCompetitions();

    @GET("competitions/registered")
    Call<CompetitionListResponse> getCompetitionsRegistered();

    @FormUrlEncoded
    @POST("report/response")
    Call<ReportResponse> reportResponse(@Field("mission_handle_id") Integer mission_handle_id, @Field("response") String response, @Field("url") String url, @Field("count_type") Integer count_type);

}
