package com.example.hellowords;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hellowords.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LearnAcrivity extends AppCompatActivity {
    private static final String PREF_VALUE = "PREF_VALUE";
    private SharedPreferences preferences;
    private static ArrayList<String> answers = new ArrayList<>();
    private static ArrayList<String> questions = new ArrayList<>();
    private TextView question;
    private TextView answer;
    private Button next;
    private Button previous;
    private int number;
    static {
        answers.add("Завтрак");
        questions.add("Breakfast");
        answers.add("Машина");
        questions.add("Car");
        answers.add("Дом");
        questions.add("House");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        question = findViewById(R.id.textQuestion);
        answer = findViewById(R.id.textAnswer);
        next = findViewById(R.id.nextButton);
        previous = findViewById(R.id.previousButton);
        if (getValue() == 0)
            updateValue(getValue());
        else
            updateValue(getValue());
        findViewById(R.id.nextButton).setOnClickListener(v -> {
            updateValue(getValue() + 1);

        });
        findViewById(R.id.previousButton).setOnClickListener(v -> {
            updateValue(getValue() - 1);

        });
    }

    private void updateValue(int newValue) {
        if ((newValue <= 2) && (newValue >= 0))
            preferences.edit().putInt(PREF_VALUE, newValue).apply();
        else
            preferences.edit().putInt(PREF_VALUE, 0).apply();
        initialize();

    }
    private void initialize() {
        number = getValue();
        question.setText(questions.get(number));
        answer.setText(answers.get(number));
    }
    private int getValue() {
        return preferences.getInt(PREF_VALUE, 0);
    }
}
