package com.example.hellowords;

import android.content.Intent;
import android.os.Bundle;

import com.example.hellowords.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 3 * 1000);
    }
}
