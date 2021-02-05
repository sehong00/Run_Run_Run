package com.example.run_run_run;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameResult extends AppCompatActivity {

    public static float highscore;
    FirebaseDatabase database;
    DatabaseReference ref, res;

    private Float scoreresult, gpa;
    private int number_of_subjects;
    public static String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        TextView highScoreTxt = findViewById(R.id.highestScoreTxt);
        TextView ScoreTxt = findViewById(R.id.ScoreTxt);
        TextView Numbers= findViewById(R.id.number_of_subjects);
        TextView gpaTxt = findViewById(R.id.meanScoreTxt);

        ImageView imageView = (ImageView) findViewById(R.id.background_result);
        imageView.setImageResource(R.drawable.background_result);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        nickName = SharedPreference.getAttribute(getApplicationContext(), "user_name");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        res = ref.child(nickName);

        /*
        res.child("highscore").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                highscore = (float) snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

        scoreresult = prefs.getFloat("score", 0.0f);
        number_of_subjects = prefs.getInt("number_of_subjects", 0);
        gpa = scoreresult / number_of_subjects;

//        highScoreTxt.setText("최고점수: " + highscore);
        ScoreTxt.setText("점수: " + scoreresult);
        Numbers.setText("과목 수: " + number_of_subjects);
        gpaTxt.setText("학점: " + gpa);
/*
        if (highscore < scoreresult) {
        }
*/
        res.child("highscore").setValue(scoreresult);
        res.child("playerscore").setValue(gpa);

        /*
        Float highscoreload = Float.valueOf(res.child("highscore").child(String.valueOf(scoreresult)).child("1").getKey());
        if (highscoreload <= scoreresult) {
            res.child("highscore").setValue(scoreresult);
            res.child("highscore").child(String.valueOf(scoreresult)).setValue(1);
        }
        */

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