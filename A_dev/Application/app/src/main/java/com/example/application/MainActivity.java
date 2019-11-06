package com.example.application;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity {

    private static final int MAX_WORDS_PER_DAY = 20;
    private static final int MAX_BAD_SIZE = 10;
    private static final int MAX_GREAT_SIZE = 15;
    boolean knowTapped = false, dontKnowTapped = false;
    /*
    condition == 0 if the learning process is running
    condition == 1 if the testing process is running
     */
    private int condition = 0;

    private static int indexInDataBase = 0;
    private HashSet<String> bad = new HashSet<>();
    private HashSet<String> normal = new HashSet<>();
    private HashSet<String> good = new HashSet<>();
    private HashSet<String> great = new HashSet<>();
    List<String> data = Arrays.asList("apple", "banana", "orange", "watermelon", "pear");
    TextView[] wordField = new TextView[1];
    Button know = null;
    Button dontKnow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*try {
            BufferedWriter br = new BufferedWriter(new FileWriter("Rrrrt.txt"));
            br.write(56);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            data = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        wordField[0] = findViewById(R.id.WordField);
        know = findViewById(R.id.know);
        dontKnow = findViewById(R.id.dont_know);
        wordField[0].setText(getWord());

        final View.OnClickListener choiceClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordField[0] = findViewById(R.id.WordField);
                if (v.equals(findViewById(R.id.dont_know))) {
                    Toast.makeText(getApplicationContext(), "" + 1,
                            Toast.LENGTH_SHORT).show();
                    bad.add(wordField[0].getText().toString());
                } else if (v.equals(findViewById(R.id.know))) {
                    Toast.makeText(getApplicationContext(), "" + 2,
                            Toast.LENGTH_SHORT).show();
                    great.add(wordField[0].toString());
                }
                choiceButtonTapped();
            }
        };

        know.setOnClickListener(choiceClick);
        dontKnow.setOnClickListener(choiceClick);

    }

    private void choiceButtonTapped() {
        wordField[0].setText(getWord());
        //if (wordField[0].equals("")) { }
        if (great.size() > MAX_GREAT_SIZE) {
            greatKnownWordsOverflow();
        }
        if (bad.size() > MAX_BAD_SIZE) {
            badKnownWordsOverflow();
        }
    }

    private String getWord() {
        ++indexInDataBase;
        if (indexInDataBase > data.size()) {
            Toast.makeText(getApplicationContext(), "bad size : " + bad.size(),
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "great size : " + great.size(),
                    Toast.LENGTH_SHORT).show();
            dontKnow.setEnabled(false);
            know.setEnabled(false);
            return "OVERFLOW_OF_DATABASE";
        }
        return data.get(indexInDataBase - 1);
    }

    private void badKnownWordsOverflow() {
        condition = 1;
    }

    private void greatKnownWordsOverflow() {
        condition = 1;
    }
}
