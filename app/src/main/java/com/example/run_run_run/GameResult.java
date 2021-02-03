package com.example.run_run_run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import static com.example.run_run_run.HomeActivity.photoUrl;
//import static com.example.run_run_run.HomeActivity.nickName;

public class GameResult extends AppCompatActivity {

    public static float highscore, totalscore, meanscore;
    FirebaseDatabase database;
    DatabaseReference ref, res, rer;

    public static String nickName, photoUrl;

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

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");

        if (highscore <= (float) prefs.getInt("score", 0) + (float) prefs.getInt("round", 0) / 2) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", (int) prefs.getInt("score", 0));
            editor.apply();
            SharedPreferences.Editor editor5 = prefs.edit();
            editor5.putInt("highscore_round", (int) prefs.getInt("round", 0));
            editor5.apply();
            highscore = (float) prefs.getInt("score", 0) + ((float) prefs.getInt("round", 0)) / 2;
            highScoreTxt.setText("HighScore: " + highscore);

            nickName = SharedPreference.getAttribute(getApplicationContext(), "user_name");


            System.out.println("ㅇㅇ3" + nickName);
/*
            rer = database.getReference("using/" + nickName);
            String sss = rer.getKey();
            System.out.println("제발" + sss);


            User_Information u = new User_Information(photoUrl, sss, highscore, meanscore);
            System.out.println("ㅇㅇ5" + sss);

*/

            res = database.getReference("users/" + nickName);
            res.child("highscore").setValue(null);
            res.child("playerscore").setValue(null);
            res.child("highscore").setValue(highscore);
            res.child("playerscore").setValue(meanscore);
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

        int info_inserted = 2;
        SharedPreferences.Editor editor19 = prefs.edit();
        editor19.putInt("try", info_inserted);
        editor19.apply();

    }
}