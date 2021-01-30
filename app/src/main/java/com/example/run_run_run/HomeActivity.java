package com.example.run_run_run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class HomeActivity extends AppCompatActivity {

    private TextView tv_google_name; // 닉네임 text
    private ImageView iv_google_img; // 이미지 View

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

    }
}