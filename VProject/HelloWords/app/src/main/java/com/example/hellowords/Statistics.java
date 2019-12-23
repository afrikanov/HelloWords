package com.example.hellowords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Statistics extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferences2;
    public static final String APP_PREFERENCES = "myWindow";
    public static final String APP_PREFERENCES2 = "ifEmpty";
    private static int number = 0;
  //  private ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        List<StatisticsElement> list = new ArrayList<>();
        list.add(new StatisticsElement("Number of correct answers:", 4));
        list.add(new StatisticsElement("Number of incorrect answers:", 3));
        list.add(new StatisticsElement("Number of answers:", 7));
        StatisticsList counterList = new StatisticsList(findViewById(R.id.list));
        counterList.setStatistics(list);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_statistics);
        Intent intent;
        sharedPreferences2 =  getSharedPreferences(APP_PREFERENCES2, Context.MODE_PRIVATE);
        number = getInt();
    //    if (getInt() == 0) {
        //    intent = new Intent(Statistics.this, MainActivity.class);
            sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    //        String json = new Gson().toJson(intent);
       //     putString(json);
            putInt(1);
    //    } else {
            intent = new Gson().fromJson(getString(), Intent.class);
            String json = new Gson().toJson(intent);
            putString(json);
      // }
        Intent intent1 = new Intent(Statistics.this, MainActivity.class);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_main:
                                startActivity(intent);
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

    private void putString(String object) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES, object);
        editor.apply();
    }

    private String getString() {
        return sharedPreferences.getString(APP_PREFERENCES, new Gson().toJson(new Intent(Statistics.this, MainActivity.class)));
    }

    private void putInt(int number) {
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        editor.putInt(APP_PREFERENCES2, number);
        editor.apply();
    }

    private int getInt() {
        return sharedPreferences2.getInt(APP_PREFERENCES2, 0);
    }
}
