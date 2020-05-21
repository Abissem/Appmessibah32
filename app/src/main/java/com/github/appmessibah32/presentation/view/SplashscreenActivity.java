package com.github.appmessibah32.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.appmessibah32.R;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Thread() {
            @Override
            public void run() {
                try {
                        sleep(2000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent myIntent = new Intent(SplashscreenActivity.this, MainActivity.class);
                                startActivity(myIntent);
                                finish();

                            }
                        });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }
}
