package com.example.run_run_run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar progressBar_2;
    TextView textView_2;
    public static String nickName, photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar_2 = findViewById(R.id.loading_progressbar);
        textView_2 = findViewById(R.id.loading_text);

        progressBar_2.setMax(100);
        progressBar_2.setScaleY(3f);

        progressAnimation_2();

        Intent intent2 = getIntent();
        nickName = intent2.getStringExtra("nickName3");
        photoUrl = intent2.getStringExtra("photoUrl3");

    }

    public void progressAnimation_2() {
        ProgressBarAnimation_2 amin_2 = new ProgressBarAnimation_2(this, progressBar_2, textView_2, 0f, 100f);
        amin_2.setDuration(3000);
        progressBar_2.setAnimation(amin_2);
    }

}