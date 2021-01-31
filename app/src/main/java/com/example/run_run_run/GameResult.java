package com.example.run_run_run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameResult extends AppCompatActivity {

    public static float highscore, totalscore, meanscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);


        TextView highScoreTxt = findViewById(R.id.highestScoreTxt);
        TextView ScoreTxt = findViewById(R.id.ScoreTxt);
        TextView Numbers= findViewById(R.id.number_of_subjects);
        TextView MeanScoreTxt = findViewById(R.id.meanScoreTxt);
        TextView RoundTxt = findViewById(R.id.round);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        if (highscore < (float) prefs.getInt("score", 0) + (float) prefs.getInt("round", 0) / 2) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", (int) prefs.getInt("score", 0));
            editor.apply();
            SharedPreferences.Editor editor5 = prefs.edit();
            editor5.putInt("highscore_round", (int) prefs.getInt("round", 0));
            editor5.apply();
            highscore = (float) prefs.getInt("score", 0) + ((float) prefs.getInt("round", 0)) / 2;
            highScoreTxt.setText("HighScore: " + highscore);
        }

        ScoreTxt.setText("Score: " + prefs.getInt("score", 0));
        Numbers.setText("num:" + prefs.getInt("number_of_subjects", 0));
        totalscore = (float) prefs.getInt("score", 0) + ((float) prefs.getInt("round", 0)) / 2;
        meanscore = totalscore / prefs.getInt("number_of_subjects", 0);
        MeanScoreTxt.setText("Means: " + meanscore);
        RoundTxt.setText("Round: " + prefs.getInt("round", 0));

        Button btn_backhome = (Button) findViewById(R.id.btn_backhome);
        btn_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}