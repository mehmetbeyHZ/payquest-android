package com.payquestion.payquest.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.payquestion.payquest.MainActivity;
import com.payquestion.payquest.Storage.UserStorage;

import org.json.JSONObject;

import okhttp3.ResponseBody;

public class ErrorCollection {
    public String message;
    public int remains_second;
    private Activity activity;

    public ErrorCollection(ResponseBody responseBody, Activity activity) {
        this.activity = activity;
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            this.message = jsonObject.getString("message");

            isLogout();


            if (jsonObject.has("remains_second")) {
                this.remains_second = jsonObject.getInt("remains_second");
            } else {
                this.remains_second = 0;
            }
        } catch (Exception e) {
            this.message = "Server Error";
        }
    }

    public void isLogout() {
        if (!message.equals("Unauthenticated.")) {
            return;
        }
        final UserStorage userStorage = new UserStorage(activity);
        message = "Çıkış yaptınız.";
        new AlertDialog.Builder(activity)
                .setTitle("Çıkış yaptınız")
                .setMessage("Oturumunuz sona erdi, tekrar giriş yapın.")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userStorage.saveUserToken(null);
                        activity.finish();
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                }).show();
    }

    public String getMessage() {
        return message;
    }

    public int getRemains_second() {
        return remains_second;
    }

}
