package com.example.hellowords;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import ru.yandex.speechkit.Emotion;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineVocalizer;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;
import ru.yandex.speechkit.Voice;

public class MainActivity extends AppCompatActivity implements VocalizerListener {
    private static ArrayList<String> enWords = new ArrayList<>();
    private static ArrayList<String> ruWords = new ArrayList<>();
    private static int knowWordsMaxSize = 20;
    private static int dknowWordsMaxSize = 1;
    Cursor cursor = null;
    String selectSQL = null;
    int cursorPosition = 0;
    private TextView wordTV;
    private Button knowButton;
    private Button dknowButton;
    int position = 0;
    private static Set<String> knowWords = new HashSet<>();
    private static Set<String> dknowWords = new HashSet<>();
    private static HashMap<String, Integer> whatSelected = new HashMap<>();
    private LinearLayout preparing;
    private LinearLayout testing;
    private Button checkButton;
    Button hintButton;
    ImageButton listenWordButton;
    Button nextWordButton;
    TextView wordValueTV;
    EditText translationET;
    private static int newWordsAmount = 0;
    private int repetitionWordsAmount = 0;
    //private static final String API_KEY_SPEECH_KIT = "c91e372b-c94d-46ef-8020-b2f2bbb16aa1";
    private static final String API_KEY_SPEECH_KIT = "069b6659-984b-4c5f-880e-aaedcfd84102";
    private Vocalizer vocalizer;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preparing = findViewById(R.id.preparing);
        testing = findViewById(R.id.testing);
        preparing.setVisibility(View.VISIBLE);
        testing.setVisibility(View.GONE);
        wordTV = findViewById(R.id.textWord);
        knowButton = findViewById(R.id.knowButton);
        dknowButton = findViewById(R.id.dknowButton);
        mDBHelper = new DBHelper(this, "wordsDB.db");
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {

        }

        try {
            mDB = mDBHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        selectSQL = "SELECT * FROM dictionary WHERE _id BETWEEN 1 AND 1";
        cursorPosition = 1;
        cursor = mDB.rawQuery(selectSQL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            enWords.add(cursor.getString(1));
            ruWords.add(cursor.getString(2));
            cursor.moveToNext();
        }

        showCurrent();

        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordNow = wordTV.getText().toString();
                knowWords.add(wordNow);
                whatSelected.put(wordNow, 1);
                goNext(v);
            }
        });

        listenWordButton = findViewById(R.id.listenWordButton);
        nextWordButton = findViewById(R.id.nextWordButton);
        checkButton = findViewById(R.id.checkButton);
        hintButton = findViewById(R.id.hint);
        wordValueTV = findViewById(R.id.wordValueTV);
        translationET = findViewById(R.id.translationET);

        dknowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordNow = wordTV.getText().toString();
                dknowWords.add(wordNow);
                whatSelected.put(wordNow, 0);
                goNext(v);
            }
        });

        /*nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if (position < enWords.size() - 1) {
                    ++position;
                    showCurrent();
                } else if (position == enWords.size() - 1) {
                    if (knowWords.size() > knowWordsMaxSize) {
                        //... start Activity
                    } else if (dknowWords.size() > dknowWordsMaxSize) {
                        //... start Activity
                    } else {
                        downloadMoreWords();
                        ++position;
                        showCurrent();
                    }
                }
                knowButton.setBackgroundResource(R.color.colorDefaultButton);
                dknowButton.setBackgroundResource(R.color.colorDefaultButton);
                String wordNow = wordTV.getText().toString();
                if (whatSelected.getOrDefault(wordNow, -1) == 1) {
                    knowButton.setBackgroundResource(R.color.colorKnowButton);
                } else if (whatSelected.getOrDefault(wordNow, - 1) == 0) {
                    dknowButton.setBackgroundResource(R.color.colorDKnowButton);
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
                knowButton.setBackgroundResource(R.color.colorDefaultButton);
                dknowButton.setBackgroundResource(R.color.colorDefaultButton);
                String wordNow = wordTV.getText().toString();
                if (whatSelected.getOrDefault(wordNow, -1) == 1) {
                    knowButton.setBackgroundResource(R.color.colorKnowButton);
                } else if (whatSelected.getOrDefault(wordNow, - 1) == 0) {
                    dknowButton.setBackgroundResource(R.color.colorDKnowButton);
                }
            }
        });*/

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
                                return true;
                            case R.id.action_statistics:
                                startActivity(new Intent(MainActivity.this, Statistics.class));
                                return true;
                            case R.id.action_settings:
                                startActivity(new Intent(MainActivity.this, Settings.class));
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void goNext(@NonNull View view) {
        final Animation animAlpha = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alpha);
        view.startAnimation(animAlpha);
        if (position < enWords.size() - 1) {
            ++position;
            showCurrent();
        } else if (position == enWords.size() - 1) {
            if (knowWords.size() >= knowWordsMaxSize) {
                repetitionWordsAmount += knowWordsMaxSize;
                startTesting();
            } else if (dknowWords.size() >= dknowWordsMaxSize) {
                newWordsAmount += dknowWordsMaxSize;
                startTesting();
            } else {
                downloadMoreWords();
                ++position;
                showCurrent();
            }
        }
        String wordNow = wordTV.getText().toString();
        if (whatSelected.getOrDefault(wordNow, -1) == 1) {
            knowButton.setBackgroundResource(R.color.colorKnowButton);
        } else if (whatSelected.getOrDefault(wordNow, - 1) == 0) {
            dknowButton.setBackgroundResource(R.color.colorDKnowButton);
        }
    }

    private void downloadMoreWords() {
        selectSQL = "SELECT * FROM dictionary WHERE _id BETWEEN " + cursorPosition + 1 + " AND " + cursorPosition + 1;
        cursorPosition++;
        cursor = mDB.rawQuery(selectSQL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            enWords.add(cursor.getString(1));
            ruWords.add(cursor.getString(2));
            cursor.moveToNext();
        }
    }

    private void showCurrent() {
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        wordTV.startAnimation(animAlpha);
        wordTV.setText(enWords.get(position));
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    int hintsAmount = 0;

    ArrayList<String> enWordsCollection;
    ArrayList<String> ruWordsCollection;
    String enWordNow;
    String ruWordNow;

    private void startTesting() {
        listenWordButton.setVisibility(View.GONE);
        preparing.setVisibility(View.GONE);
        testing.setVisibility(View.VISIBLE);
        enWordsCollection = new ArrayList<>(enWords);
        ruWordsCollection = new ArrayList<>(ruWords);
        for (String e : ruWordsCollection) {
            System.out.print(e + " ");
        }
        enWordNow = enWordsCollection.get(0);
        ruWordNow = ruWordsCollection.get(0);
        enWordsCollection.remove(enWordNow);
        ruWordsCollection.remove(ruWordNow);
        wordValueTV.setText(ruWordNow);

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(enWordNow.charAt(0));
                if (hintsAmount == 0) {
                    translationET.setText(enWordNow.substring(0, 1));
                    ++hintsAmount;
                } else if (hintsAmount == 1) {
                    translationET.setText(enWordNow.substring(0, 2));
                    ++hintsAmount;
                } else {
                    showAnswer(enWordNow);
                }
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(enWordNow);
            }
        });
        System.out.println("END");
    }

    private void showAnswer(String answer) {
        System.out.println(enWordsCollection.size());
        String userWord = translationET.getText().toString();
        translationET.setText(answer);
        translationET.setEnabled(false);
        listenWordButton.setVisibility(View.VISIBLE);
        nextWordButton.setVisibility(View.VISIBLE);
        checkButton.setVisibility(View.GONE);
        hintButton.setVisibility(View.GONE);

        final Animation animAlpha = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alpha);
        translationET.startAnimation(animAlpha);
        boolean isRight = false;
        if (answer.equals(userWord)) {
            isRight = true;
            translationET.setBackgroundResource(R.color.colorKnowButton);
            enWordsCollection.remove(answer);
            ruWordsCollection.remove(wordValueTV.getText().toString());
        } else {
            translationET.setBackgroundResource(R.color.colorDKnowButton);
            enWordsCollection.remove(answer);
            ruWordsCollection.remove(wordValueTV.getText().toString());
            enWordsCollection.add(answer);
            ruWordsCollection.add(wordValueTV.getText().toString());
            for (String e : ruWordsCollection) {
                System.out.print(e + " ");
            }
            System.out.println();
        }

        listenWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animAlpha = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alpha);
                listenWordButton.startAnimation(animAlpha);
                try {
                    SpeechKit.getInstance().init(getApplicationContext(), API_KEY_SPEECH_KIT);
                    SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
                } catch (SpeechKit.LibraryInitializationException ignored) {
                    throw new RuntimeException();
                }
                vocalizer = new OnlineVocalizer.Builder(Language.ENGLISH, MainActivity.this)
                        .setEmotion(Emotion.GOOD)
                        .setVoice(Voice.ZAHAR)
                        .setAutoPlay(true)
                        .setSpeed(0.5f)
                        .build();
                vocalizer.prepare();
                if (TextUtils.isEmpty(answer)) {
                    Toast.makeText(getApplicationContext(), "Write smth to be vocalized!", Toast.LENGTH_SHORT).show();
                } else {
                    vocalizer.synthesize(answer, Vocalizer.TextSynthesizingMode.INTERRUPT);
                }
            }
        });

        boolean finalIsRight = isRight;
        nextWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalIsRight) {
                    Intent intent = new Intent(MainActivity.this, RecognizerActivity.class);
                    intent.putExtra("word", answer);
                    startActivity(intent);
                }
                if (ruWordsCollection.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Молодец! За сегодня ты узнал " + newWordsAmount + " новых слов и повторил " + repetitionWordsAmount + " слов.", Toast.LENGTH_SHORT).show();
                    startLearning();
                    return;
                }
                enWordNow = enWordsCollection.get(0);
                ruWordNow = ruWordsCollection.get(0);
                enWordsCollection.remove(enWordNow);
                ruWordsCollection.remove(ruWordNow);
                wordValueTV.setText(ruWordNow);
                translationET.setBackgroundResource(R.color.colorDefaultButton);
                translationET.setText("");
                translationET.setEnabled(true);
                listenWordButton.setVisibility(View.GONE);
                nextWordButton.setVisibility(View.GONE);
                checkButton.setVisibility(View.VISIBLE);
                hintButton.setVisibility(View.VISIBLE);
                hintsAmount = 0;
            }
        });

    }

    private void startLearning() {
        testing.setVisibility(View.GONE);
        preparing.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSynthesisDone(@NonNull Vocalizer vocalizer) {

    }

    @Override
    public void onPartialSynthesis(@NonNull Vocalizer vocalizer, @NonNull Synthesis synthesis) {

    }

    @Override
    public void onPlayingBegin(@NonNull Vocalizer vocalizer) {

    }

    @Override
    public void onPlayingDone(@NonNull Vocalizer vocalizer) {
        Toast.makeText(getApplicationContext(), "FINISH", Toast.LENGTH_LONG);
    }

    @Override
    public void onVocalizerError(@NonNull Vocalizer vocalizer, @NonNull Error error) {

    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
}
