package com.example.run_run_run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView tv_google_name; // 닉네임 text
    private ImageView iv_google_img; // 이미지 View
    public static float highscore, totalscore, meanscore;
    private static final String TAG = "HomeAcitvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName"); // LoginActivity 로부터 닉네임 전달받음
        String photoUrl = intent.getStringExtra("photoUrl"); // LoginActivity 로부터 프로필 사진 전달받음

        tv_google_name = findViewById(R.id.tv_google_name);
        tv_google_name.setText(nickName); // 닉네임 text를 텍스트 뷰에 세팅

        iv_google_img = findViewById(R.id.iv_goggle_img);
        Glide.with(this).load(photoUrl).into(iv_google_img); // 프로필 Url을 이미지 뷰에 세팅

        Button btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_2 = new Intent(getApplicationContext(), LoadingActivity.class);
                startActivity(intent_2);
            }
        });

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        TextView gpaTxt = findViewById(R.id.gpa);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        if (highscore < (float) prefs.getInt("score", 0) + (float) prefs.getInt("round", 0) / 2) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", (int) prefs.getInt("score", 0));
            editor.apply();
            SharedPreferences.Editor editor5 = prefs.edit();
            editor5.putInt("highscore_round", (int) prefs.getInt("round", 0));
            editor5.apply();
            highscore = (float) prefs.getInt("score", 0) + ((float) prefs.getInt("round", 0)) / 2;
            highScoreTxt.setText("최고 점수" + highscore + " 점");
        }

        meanscore = highscore / prefs.getInt("number_of_subjects", 0);
        gpaTxt.setText("GPA: " + meanscore);


/*
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
*/

        Log.d(TAG, "onCreate: Started.");
        ListView player_listView = (ListView) findViewById(R.id.player_list);

        Person john = new Person("aaa", "bbb", "ccc", "ddd");
        Person steve = new Person("ada", "bbb", "ccc", "ddd");

        ArrayList<Person> players_list = new ArrayList<>();
        players_list.add(john);
        players_list.add(steve);

        PlayerListAdapter adapter = new PlayerListAdapter(this, R.layout.adapter_view_layout, players_list);
        player_listView.setAdapter(adapter);





    }
}