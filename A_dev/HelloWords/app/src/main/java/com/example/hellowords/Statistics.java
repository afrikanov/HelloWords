package com.example.hellowords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        List<StatisticsElement> list = new ArrayList<>();
        list.add(new StatisticsElement("Number of hours", 0));
        list.add(new StatisticsElement("Number of hours:", 0));
        list.add(new StatisticsElement("Number of bad answers:", 0));
        StatisticsList counterList = new StatisticsList(findViewById(R.id.list));
        counterList.setStatistics(list);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_statistics);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_main:
                                startActivity(new Intent(Statistics.this, MainActivity.class));
                                return true;
                            case R.id.action_statistics:
                                startActivity(new Intent(Statistics.this, Statistics.class));
                                return true;
                            case R.id.action_settings:
                                startActivity(new Intent(Statistics.this, Settings.class));
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
