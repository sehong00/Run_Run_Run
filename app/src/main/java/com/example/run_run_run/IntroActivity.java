package com.example.run_run_run;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class IntroActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    private VideoView mVideoview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

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

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressAnimation();

    }

    public void progressAnimation() {
        ProgressBarAnimation amin = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        amin.setDuration(8000);
        progressBar.setAnimation(amin);
    }

}