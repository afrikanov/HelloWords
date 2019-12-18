package com.example.hellowords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_main:
                                startActivity(new Intent(Settings.this, MainActivity.class));
                                return true;
                            case R.id.action_statistics:
                                startActivity(new Intent(Settings.this, Statistics.class));
                                return true;
                            case R.id.action_settings:
                                startActivity(new Intent(Settings.this, Settings.class));
                                return true;
                        }
                        return false;
                    }
                });

    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
