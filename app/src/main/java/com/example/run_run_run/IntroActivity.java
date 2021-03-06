package com.example.run_run_run;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class IntroActivity extends AppCompatActivity {

    // public static Activity introactiviy;

    ProgressBar progressBar;
    TextView textView;
    static private VideoView mVideoview;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // introactiviy = IntroActivity.this;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.intro_progressbar);
        textView = findViewById(R.id.intro_text);

        mVideoview = (VideoView) findViewById(R.id.testVideo);
        mVideoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.itmrun1));
        mVideoview.start();

        mVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        /*
        mediaPlayer = MediaPlayer.create(IntroActivity.this, R.raw.pop);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        */

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressAnimation();

    }

    public void progressAnimation() {
        ProgressBarAnimation amin = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        amin.setDuration(8000);
        progressBar.setAnimation(amin);

        mVideoview.stopPlayback();
        // mediaPlayer.stop();

    }

}