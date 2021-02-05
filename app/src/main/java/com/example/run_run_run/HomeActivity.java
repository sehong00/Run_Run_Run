package com.example.run_run_run;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView tv_google_name; // 닉네임 text
    private ImageView iv_google_img; // 이미지 View
    public String nickName, photoUrl;

    private static final String TAG = "HomeAcitvity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User_Info> arrayList;

    FirebaseDatabase database;
    DatabaseReference ref;

    // public static Activity homeactiviy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*
        homeactiviy = HomeActivity.this;

        IntroActivity introActivity = (IntroActivity) IntroActivity.introactiviy;
        introActivity.finish();
        */

        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName"); // LoginActivity 로부터 닉네임 전달받음
        photoUrl = intent.getStringExtra("photoUrl"); // LoginActivity 로부터 프로필 사진 전달받음

        tv_google_name = findViewById(R.id.tv_google_name);
        tv_google_name.setText(nickName); // 닉네임 text를 텍스트 뷰에 세팅

        iv_google_img = findViewById(R.id.iv_goggle_img);
        Glide.with(this).load(photoUrl).into(iv_google_img); // 프로필 Url을 이미지 뷰에 세팅

        ImageView imageView = (ImageView) findViewById(R.id.select_character);
        imageView.setImageResource(R.drawable.amongus_white);
        ImageView imageView2 = (ImageView) findViewById(R.id.select_weapon);
        imageView2.setImageResource(R.drawable.notebook);
        ImageView imageView3 = (ImageView) findViewById(R.id.select_map);
        imageView3.setImageResource(R.drawable.map);

        Button btn_map = (Button) findViewById(R.id.btn_map);
        Button btn_weapon = (Button) findViewById(R.id.btn_weapon);
        Button btn_character = (Button) findViewById(R.id.btn_character);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btn_weapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btn_character.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), LoadingActivity.class);
                // intent2.putExtra("nickName2", nickName);
                // intent2.putExtra("photoUrl2", photoUrl);
                startActivity(intent2);

            }
        });

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        TextView gpaTxt = findViewById(R.id.gpa);

/*
        Log.d(TAG, "onCreate: Started.");
        ListView player_listView = (ListView) findViewById(R.id.player_list);
        User_Info aaa = new User_Info(photoUrl, nickName, highscore, meanscore);
        User_Info steve = new User_Info("ada", "bbb", highscore, meanscore);

        players_list.add(aaa);
        players_list.add(steve);
*/

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        ref = database.getReference("users");  // DB 테이블 연결

        recyclerView = findViewById(R.id.player_list);
        recyclerView.setHasFixedSize(true); // RecyclerView 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // User 객체를 담을 Array List (Adapter 쪽으로)

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                // Firebase Database 의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지 않도록 초기화

                for (DataSnapshot snapshot1 : datasnapshot.getChildren()) { // 반복문으로 데이터 List 를 추출함
                    User_Info user_info = snapshot1.getValue(User_Info.class); // 만들어줬던 User_Info 객체에 데이터를 담는다.
                    arrayList.add(user_info); // 담은 데이터들을 배열 리스트에 넣고 RecyclerView 로 보낼 준비
                }

                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던 중 Error 발생 시
                Log.e("HomeActivity", String.valueOf(databaseError.toException())); // 에러문 출력

            }
        });

        adapter = new PlayerListAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);

    }

    private long time= 0;

    @Override
    public void onBackPressed(){

        if(System.currentTimeMillis() - time >= 2000){
            time=System.currentTimeMillis();
            // Toast.makeText(getApplicationContext(),"한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();

        }

        else if(System.currentTimeMillis() - time < 2000 ){

            finishAffinity();
            System.runFinalization();
            System.exit(0);

        }

    }
}

class User_Information{

    public String photouri;
    public String playername;
    public float highscore;
    public float playerscore;

    public User_Information(String photouri, String playername, Float playergpa, Float playerscore){
            this.photouri = photouri;
            this.playername = playername;
            this.highscore = playergpa;
            this.playerscore = playerscore;

    }

}
