package com.billy.cricketmvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.billy.cricketmvvm.view.activities.MainActivity;
import com.kaiguanjs.SplashLietener;
import com.kaiguanjs.utils.YQCUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
//                YQCUtils.splashAction(SplashActivity.this, new SplashLietener() {
//                    @Override
//                    public void startMySplash(int version, String downUrl) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
//                    }
//                });
            }
        }.start();


    }
}