package com.example.hellowords;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class LearnActivity extends AppCompatActivity {
    private static ArrayList<String> enWords = new ArrayList<>();
    private static ArrayList<String> ruWords = new ArrayList<>();
    private TextView questionTV;
    private TextView answerTV;
    private Button nextButton;
    private Button previousButton;
    int position = 0;

    /*static {
        answers.add("Завтрак");
        questions.add("Breakfast");
        answers.add("Машина");
        questions.add("Car");
        answers.add("Дом");
        questions.add("House");
    }*/

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn);

        questionTV = findViewById(R.id.textQuestion);
        answerTV = findViewById(R.id.textAnswer);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);
        mDBHelper = new DBHelper(this);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDB = mDBHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        String selectSQL = "SELECT * FROM dictionary WHERE _id BETWEEN 1 AND 10";
        Cursor cursor = mDB.rawQuery(selectSQL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            enWords.add(cursor.getString(1));
            ruWords.add(cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();

        showCurrent();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (position < enWords.size() - 1) {
                    ++position;
                    showCurrent();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (position > 0) {
                    --position;
                    showCurrent();
                }
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_main:
                                startActivity(new Intent(LearnActivity.this, MainActivity.class));
                                return true;
                            case R.id.action_statistics:
                                startActivity(new Intent(LearnActivity.this, Statistics.class));
                                return true;
                            case R.id.action_settings:
                                startActivity(new Intent(LearnActivity.this, Settings.class));
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void showCurrent() {
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        answerTV.startAnimation(animAlpha);
        answerTV.setText(ruWords.get(position));
        questionTV.startAnimation(animAlpha);
        questionTV.setText(enWords.get(position));
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
