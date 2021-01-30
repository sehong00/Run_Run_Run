package com.example.run_run_run;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation_2 extends Animation {
    private Context context_2;
    private ProgressBar progressBar_2;
    private TextView textView_2;
    private float from_2;
    private float to_2;

    public ProgressBarAnimation_2(Context context, ProgressBar progressBar, TextView textView, float from, float to) {
        this.context_2 = context;
        this.progressBar_2 = progressBar;
        this.textView_2 = textView;
        this.from_2 = from;
        this.to_2 = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value_2 = from_2 + (to_2 - from_2) * interpolatedTime;
        progressBar_2.setProgress((int)value_2);
        textView_2.setText((int)value_2+" %");

        if (value_2 == to_2) {
            context_2.startActivity(new Intent(context_2.getApplicationContext(), GameActivity.class));
        }

    }
}
