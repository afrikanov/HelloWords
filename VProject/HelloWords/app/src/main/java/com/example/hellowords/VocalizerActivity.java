package com.example.hellowords;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class VocalizerActivity extends AppCompatActivity implements VocalizerListener {

    private static final String API_KEY_SPEECH_KIT = "c91e372b-c94d-46ef-8020-b2f2bbb16aa1";
    private Vocalizer vocalizer;

    public VocalizerActivity() {
        try {
            SpeechKit.getInstance().init(getApplicationContext(), API_KEY_SPEECH_KIT);
            SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
        } catch (SpeechKit.LibraryInitializationException ignored) {
            //do not ignore in a real app!
            //finish()
        }
        vocalizer = new OnlineVocalizer.Builder(Language.ENGLISH, this)
                .setEmotion(Emotion.GOOD)
                .setVoice(Voice.ERMIL)
                .setAutoPlay(true)
                .build();
        vocalizer.prepare();
        vocalizer.synthesize("56", Vocalizer.TextSynthesizingMode.INTERRUPT);
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

    }

    @Override
    public void onVocalizerError(@NonNull Vocalizer vocalizer, @NonNull Error error) {

    }
}
