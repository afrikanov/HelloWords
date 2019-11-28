package com.example.hellowords;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.hellowords.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.SharedMemory;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static final String PREF_VALUE = "PREF_VALUE";
    private static ArrayList<String> buttons = new ArrayList<>();
    private static ArrayList<String> question = new ArrayList<>();
    private static ArrayList<String> answer = new ArrayList<>();
    private TextView textView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private int number;
    private String textValue;
    private SharedPreferences preferences;
    static {
        buttons.add("Завтрак");
        buttons.add("Перерыв");
        buttons.add("Ужин");
        buttons.add("Обед");
        question.add("Breakfast");
        answer.add("Завтрак");

        buttons.add("Ворона");
        buttons.add("Машина");
        buttons.add("Стул");
        buttons.add("Карточка");
        question.add("Car");
        answer.add("Машина");

        buttons.add("Машина");
        buttons.add("Ворона");
        buttons.add("Стул");
        buttons.add("Карточка");
        question.add("Car");
        answer.add("Машина");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_home);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        textView = findViewById(R.id.textValue);
        button1 = findViewById(R.id.firstButton);
        button2 = findViewById(R.id.secondButton);
        button3 = findViewById(R.id.thirdButton);
        button4 = findViewById(R.id.fourthButton);
        if (getValue() == 0) {
            updateValue(getValue());
        } else {
            updateValue(getValue() - 1);
        }
        findViewById(R.id.firstButton).setOnClickListener(v -> {
            if (button1.getText().equals(textValue)) {
                Snackbar.make(v, "Your answer is true", Snackbar.LENGTH_SHORT).show();
            } else
                Snackbar.make(v, "Your answer is false", Snackbar.LENGTH_SHORT).show();
            updateValue(getValue());

        });
        findViewById(R.id.secondButton).setOnClickListener(v -> {
            if (button2.getText().equals(textValue))
                Snackbar.make(v, "Your answer is true", Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(v, "Your answer is false", Snackbar.LENGTH_SHORT).show();
            updateValue(getValue());
        });
        findViewById(R.id.thirdButton).setOnClickListener(v -> {
            if (button3.getText().equals(textValue))
                Snackbar.make(v, "Your answer is true", Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(v, "Your answer is false", Snackbar.LENGTH_SHORT).show();
            updateValue(getValue());
        });
        findViewById(R.id.fourthButton).setOnClickListener(v -> {
            if (button4.getText().equals(textValue))
                Snackbar.make(v, "Your answer is true", Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(v, "Your answer is false", Snackbar.LENGTH_SHORT).show();
            updateValue(getValue());
        });
    }

    private void updateValue(int newValue) {
        if (newValue <= 2)
            preferences.edit().putInt(PREF_VALUE, newValue + 1).apply();
        else
            preferences.edit().putInt(PREF_VALUE, 1).apply();
        initialize();

    }
    private void initialize() {
        number = getValue();
        button1.setText(buttons.get(number * 4 - 4));
        button2.setText(buttons.get(number * 4 - 3));
        button3.setText(buttons.get(number * 4 - 2));
        button4.setText(buttons.get(number * 4 - 1));
        textView.setText(question.get(number - 1));

        textValue = answer.get(number - 1);
    }
    private int getValue() {
        return preferences.getInt(PREF_VALUE, 0);
    }
}
