package com.example.hellowords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class MainActivity extends Activity {

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    BottomNavigationItemView itemMain;
    BottomNavigationItemView itemStatistics;
    BottomNavigationItemView itemSettings;
    BottomNavigationView bottomMenu;
    LinearLayout mainActivity;
    LinearLayout learnActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DBHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDB = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        bottomMenu = findViewById(R.id.bottom_menu);
        itemMain = findViewById(R.id.action_main);
        itemSettings = findViewById(R.id.action_settings);
        itemStatistics = findViewById(R.id.action_statistics);

        mainActivity = findViewById(R.id.mainActivity);
        learnActivity = findViewById(R.id.learnActivity);

        mainActivity.setVisibility(View.VISIBLE);
        learnActivity.setVisibility(View.GONE);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_main:
                                //startActivity(new Intent(MainActivity.this, MainActivity.class));
                                mainActivity.setVisibility(View.VISIBLE);
                                learnActivity.setVisibility(View.GONE);
                                return true;
                            case R.id.action_statistics:
                                mainActivity.setVisibility(View.GONE);
                                learnActivity.setVisibility(View.VISIBLE);
                                //startActivity(new Intent(MainActivity.this, ));
                                return true;
                            case R.id.action_settings:
                                //loadFragment(NotificationsFragment.newInstance());
                                return true;
                        }
                        return false;
                    }
                });

    }

    public void openCounter(View view) {
        startActivity(new Intent(this, LearnActivity.class));
    }
}
