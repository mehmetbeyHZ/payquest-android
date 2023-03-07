package com.payquestion.payquest.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.payquestion.payquest.Models.InformationResponse;
import com.payquestion.payquest.Models.Question;
import com.payquestion.payquest.Models.Statistic;

import java.lang.reflect.Type;
import java.util.List;

public class UserStorage {
    SharedPreferences sharedPreferences;
    Context context;

    public UserStorage(Context context)
    {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences("com.payquestion.payquest",Context.MODE_PRIVATE);
    }



    public void saveDirect(String balance, String avatar, String name, String email, String phone_number, int xp, String ref_code, String level)
    {
        this.setBalance(balance);
        this.setAvatar(avatar);
        this.setName(name);
        this.setEmail(email);
        this.setPhone_number(phone_number);
        this.setXp(xp);
        this.setRefCode(ref_code);
        this.setLevel(level);
    }

    public void setDiamond(int diamond)
    {
        sharedPreferences.edit().putInt("user_diamond",diamond).apply();
    }

    public int getDiamond()
    {
        return sharedPreferences.getInt("user_diamond",0);
    }

    public void setIsVerified(int is_verified)
    {
        sharedPreferences.edit().putInt("is_verified",is_verified).apply();
    }

    public int getIsVerified()
    {
        return sharedPreferences.getInt("is_verified",0);
    }

    public void saveUserToken(String token)
    {
        sharedPreferences.edit().putString("bearer_token",token).apply();
    }

    public String getToken()
    {
        return sharedPreferences.getString("bearer_token",null);
    }

    public String  getBalance() {
        return sharedPreferences.getString("auth_balance","0");
    }

    public void setBalance(String value) {
        sharedPreferences.edit().putString("auth_balance",value).apply();
    }

    public String  getAdId() {
        return sharedPreferences.getString("advertising_id","0");
    }

    public void setAdId(String value) {
        sharedPreferences.edit().putString("advertising_id",value).apply();
    }



    public long  getAdIdResetTime() {
        return sharedPreferences.getLong("advertising_id_reset_time",86400);
    }
    public void setAdIdResetTime(Long value) {
        sharedPreferences.edit().putLong("advertising_id_reset_time",value).apply();
    }


    public boolean  getAdIdResetTimeSystem() {
        return sharedPreferences.getBoolean("advertising_id_reset_system",true);
    }
    public void setAdIdResetTimeSystem(Boolean value) {
        sharedPreferences.edit().putBoolean("advertising_id_reset_system",value).apply();
    }





    public Long  getAdIdTime() {
        return sharedPreferences.getLong("advertising_id_time",0);
    }

    public void setAdIdTime(Long value) {
        sharedPreferences.edit().putLong("advertising_id_time",value).apply();
    }

    public String getAvatar() {
        return sharedPreferences.getString("auth_avatar",null);
    }

    public void setAvatar(String avatar) {
        sharedPreferences.edit().putString("auth_avatar",avatar).apply();
    }

    public String getName() {
        return sharedPreferences.getString("auth_name",null);
    }

    public void setName(String name) {
        sharedPreferences.edit().putString("auth_name",name).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString("auth_email",null);
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString("auth_email",email).apply();
    }

    public String getPhone_number() {
        return sharedPreferences.getString("auth_phone",null);
    }

    public void setPhone_number(String phone_number) {
        sharedPreferences.edit().putString("auth_phone",phone_number).apply();
    }

    public int getXp() {
        return sharedPreferences.getInt("auth_xp",0);
    }

    public void setXp(int xp) {
        sharedPreferences.edit().putInt("auth_xp",xp).apply();
    }

    public void setRefCode(String refCode)
    {
        sharedPreferences.edit().putString("ref_code",refCode).apply();
    }

    public String getRefCode()
    {
        return sharedPreferences.getString("ref_code",null);
    }

    public void setNotificationsEnabled(Boolean isEnabled)
    {
        sharedPreferences.edit().putBoolean("is_notifications",isEnabled).apply();
    }

    public Boolean getNotificationsEnabled()
    {
        return sharedPreferences.getBoolean("is_notifications",true);
    }

    public void setLevel(String level)
    {
        sharedPreferences.edit().putString("level",level).apply();
    }

    public void setFCMToken(String token)
    {
        sharedPreferences.edit().putString("fcm_token",token).apply();
    }

    public String getFCMToken(){
        return sharedPreferences.getString("fcm_token",null);
    }

    public String getLevel()
    {
        return sharedPreferences.getString("level","0");
    }

    public void resetStorage()
    {
        sharedPreferences.edit().remove("level").apply();
        sharedPreferences.edit().remove("ref_code").apply();
        sharedPreferences.edit().remove("auth_xp").apply();
        sharedPreferences.edit().remove("auth_phone").apply();
        sharedPreferences.edit().remove("auth_email").apply();
        sharedPreferences.edit().remove("auth_name").apply();
        sharedPreferences.edit().remove("auth_balance").apply();
        sharedPreferences.edit().remove("bearer_token").apply();
        sharedPreferences.edit().remove("auth_avatar").apply();
        sharedPreferences.edit().remove("questions_list").apply();
    }

    public void setInformation(List<InformationResponse> information)
    {
        Gson gson = new Gson();
        String json = gson.toJson(information);
        sharedPreferences.edit().putString("information_list", json).apply();
    }

    public List<InformationResponse> getInformations()
    {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("information_list", null);
        Type type = new TypeToken<List<InformationResponse>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public void setTicketInformation(List<InformationResponse> information)
    {
        Gson gson = new Gson();
        String json = gson.toJson(information);
        sharedPreferences.edit().putString("information_list_ticket", json).apply();
    }

    public List<InformationResponse> getTicketInformation()
    {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("information_list_ticket", null);
        Type type = new TypeToken<List<InformationResponse>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public void setStatistic(List<Statistic> statistic)
    {
        Gson gson = new Gson();
        String json = gson.toJson(statistic);
        sharedPreferences.edit().putString("statistic_list", json).apply();
    }

    public List<Statistic> getStatistic()
    {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("statistic_list", null);
        Type type = new TypeToken<List<Statistic>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public void setQuestions(List<Question> questions)
    {
        Gson gson = new Gson();
        String json = gson.toJson(questions);
        sharedPreferences.edit().putString("questions_list", json).apply();
    }

    public List<Question>  getQuestions()
    {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("questions_list", null);
        Type type = new TypeToken<List<Question>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
