package com.example.hellowords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
        list.add(new StatisticsElement("Number of bad answer:", 0));
        StatisticsList counterList = new StatisticsList(findViewById(R.id.list));
        counterList.setStatistics(list);
        findViewById(R.id.firstButton).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));

        });
    }
}