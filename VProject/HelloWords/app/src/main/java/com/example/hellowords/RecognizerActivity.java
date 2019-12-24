package com.example.hellowords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import ru.yandex.speechkit.Emotion;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineModel;
import ru.yandex.speechkit.OnlineRecognizer;
import ru.yandex.speechkit.OnlineVocalizer;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Track;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;
import ru.yandex.speechkit.Voice;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class RecognizerActivity extends AppCompatActivity implements RecognizerListener, VocalizerListener {

    private static final String API_KEY_SPEECH_KIT = "069b6659-984b-4c5f-880e-aaedcfd84102";
    private static final int REQUEST_PERMISSION_CODE = 31;

    private ProgressBar progressBar;
    private TextView currentStatus;
    private TextView recognitionResult;
    private TextView vocalize;

    private Recognizer recognizer;
    private Vocalizer vocalizer;
    String answer = "";

    @Override
    public void onBackPressed()
    {
        Toast.makeText(this, "Please, pronounce the word correctly", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer);

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        builder.setTitle("Произнесите слово")
                .setMessage("Для продолжения необходимо правильно произнести слово")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        //alert.show();

        try {
            SpeechKit.getInstance().init(this, API_KEY_SPEECH_KIT);
            SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
        } catch (SpeechKit.LibraryInitializationException ignored) {
            //do not ignore in a prod version!
        }

        recognizer = new OnlineRecognizer.Builder(Language.RUSSIAN, OnlineModel.QUERIES,
                RecognizerActivity.this).build();


        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecognizer();
            }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.cancel();
            }
        });

        progressBar = findViewById(R.id.voice_power_bar);
        currentStatus = findViewById(R.id.current_state);
        recognitionResult = findViewById(R.id.result);
        vocalize = findViewById(R.id.vocalize);

        answer = getIntent().getStringExtra("word");

        vocalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animAlpha = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alpha);
                vocalize.startAnimation(animAlpha);
                try {
                    SpeechKit.getInstance().init(getApplicationContext(), API_KEY_SPEECH_KIT);
                    SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
                } catch (SpeechKit.LibraryInitializationException ignored) {
                    throw new RuntimeException();
                }
                vocalizer = new OnlineVocalizer.Builder(Language.ENGLISH, RecognizerActivity.this)
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

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != REQUEST_PERMISSION_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PERMISSION_GRANTED) {
            startRecognizer();
        } else {
            updateStatus("Record audio permission was not granted");
        }
    }

    @Override
    public void onRecordingBegin(@NonNull Recognizer recognizer) {
        updateStatus("Recording begin");
        updateProgress(0);
        updateResult("");
    }

    @Override
    public void onSpeechDetected(@NonNull Recognizer recognizer) {
        updateStatus("Speech detected");
    }

    @Override
    public void onSpeechEnds(@NonNull Recognizer recognizer) {
        updateStatus("Speech ends");
        if (recognitionResult.getText().toString().equals(answer)) {
            this.finish();
        }
    }

    @Override
    public void onRecordingDone(@NonNull Recognizer recognizer) {
        updateStatus("Recording done");
    }

    @Override
    public void onPowerUpdated(@NonNull Recognizer recognizer, float power) {
        updateProgress((int) (power * progressBar.getMax()));
    }

    @Override
    public void onPartialResults(@NonNull Recognizer recognizer, @NonNull Recognition recognition, boolean eOfU) {
        updateStatus("Partial results " + recognition.getBestResultText() + " endOfUtterrance = " + eOfU);
        if (eOfU) {
            updateResult(recognition.getBestResultText());
        }
    }

    @Override
    public void onRecognitionDone(@NonNull Recognizer recognizer) {
        updateStatus("Recognition done");
        updateProgress(0);
    }

    @Override
    public void onRecognizerError(@NonNull Recognizer recognizer, @NonNull Error error) {
        updateStatus("Error occurred " + error);
        updateProgress(0);
        updateResult("");
    }

    @Override
    public void onMusicResults(@NonNull Recognizer recognizer, @NonNull Track track) {
    }


    private void startRecognizer() {
        if (ContextCompat.checkSelfPermission(RecognizerActivity.this, RECORD_AUDIO) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            recognizer.startRecording();
        }
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

    private void updateResult(String text) {
        recognitionResult.setText(text);
    }

    private void updateStatus(final String text) {
        currentStatus.setText(text);
    }

    private void updateProgress(int progress) {
        progressBar.setProgress(progress);
    }
}
