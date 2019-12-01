package com.example.hellowords;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellowords.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LearnGrammar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_grammar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_main:
                                startActivity(new Intent(LearnGrammar.this, MainActivity.class));
                                return true;
                            case R.id.action_statistics:
                                startActivity(new Intent(LearnGrammar.this, Statistics.class));
                                return true;
                            case R.id.action_settings:
                                startActivity(new Intent(LearnGrammar.this, Settings.class));
                                return true;
                        }
                        return false;
                    }
                });
    }
}

