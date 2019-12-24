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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_home);
    }
}
