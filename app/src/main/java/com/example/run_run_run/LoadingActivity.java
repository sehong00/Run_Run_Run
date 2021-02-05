package com.example.run_run_run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar progressBar_2;
    TextView textView_2;
    private VideoView mVideoview;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        /*
        HomeActivity homeActivity = (HomeActivity) HomeActivity.homeactiviy;
        homeActivity.finish();
        */

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar_2 = findViewById(R.id.loading_progressbar);
        textView_2 = findViewById(R.id.loading_text);
/*
        mVideoview = (VideoView) findViewById(R.id.testVideo);
        mVideoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.loading));
        mVideoview.start();

        mVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
*/
        /*
        mediaPlayer = MediaPlayer.create(LoadingActivity.this, R.raw.pop2);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        */

        progressBar_2.setMax(100);
        progressBar_2.setScaleY(3f);

        progressAnimation_2();

    }

    public void progressAnimation_2() {
        ProgressBarAnimation_2 amin_2 = new ProgressBarAnimation_2(this, progressBar_2, textView_2, 0f, 100f);
        amin_2.setDuration(3000);
        progressBar_2.setAnimation(amin_2);

        // mVideoview.stopPlayback();
        // mediaPlayer.stop();

    }

}