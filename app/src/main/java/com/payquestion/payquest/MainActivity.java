package com.payquestion.payquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.payquestion.payquest.Authenticate.ForgotPassword;
import com.payquestion.payquest.Authenticate.Login;
import com.payquestion.payquest.Authenticate.Register;
import com.payquestion.payquest.Storage.UserStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerMyTOKEN();
        if (isAuth()) {
            Intent intent = new Intent(MainActivity.this, AppActivity.class);
            startActivity(intent);
            this.finish();
            return;
        }
        goToLogin();
    }

    public Boolean isAuth() {
        String token = (new UserStorage(this)).getToken();
        return token != null;
    }

    public void goToLogin() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Login login = new Login();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.authFrame, login).commit();
    }

    public void registerMyTOKEN() {
        final UserStorage storage = new UserStorage(this);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String mToken = instanceIdResult.getToken();
                storage.setFCMToken(mToken);
            }
        });
    }

    public void goToRegister() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Register register = new Register();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.authFrame, register).commit();
    }

    public void goToForgotPassword() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ForgotPassword forgotPassword = new ForgotPassword();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.authFrame, forgotPassword).commit();
    }

    private boolean isFirstBackPressed = false;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 1) {
            super.onBackPressed();
        } else {
            if (isFirstBackPressed && getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
            } else {
                isFirstBackPressed = true;
                Toast.makeText(this, getString(R.string.double_logout), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isFirstBackPressed = false;
                    }
                }, 1500);
            }
        }
    }


}