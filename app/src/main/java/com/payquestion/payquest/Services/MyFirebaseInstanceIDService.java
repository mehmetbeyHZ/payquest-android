package com.payquestion.payquest.Services;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        System.out.println("My Token: " + token);
    }
}
