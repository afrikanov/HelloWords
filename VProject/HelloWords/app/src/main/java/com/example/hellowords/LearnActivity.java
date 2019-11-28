package com.example.hellowords;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    }

    private void showCurrent() {
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        answerTV.startAnimation(animAlpha);
        answerTV.setText(ruWords.get(position));
        questionTV.startAnimation(animAlpha);
        questionTV.setText(enWords.get(position));
    }
}
