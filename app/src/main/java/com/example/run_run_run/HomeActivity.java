package com.example.run_run_run;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private TextView tv_google_name; // 닉네임 text
    private ImageView iv_google_img; // 이미지 View
    public static float highscore, totalscore, meanscore;

    private static final String TAG = "HomeAcitvity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<User> arrayList;
//    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    FirebaseDatabase database;
    DatabaseReference ref;

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



        /////////////////////////////////////////////////////////

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        User_Info u = new User_Info(photoUrl, nickName, meanscore, highscore);

        int info_inserted = 0;
        
        //만약에 최고점수 달성하고 학점 높아지면 DB 없애고 다시 만들어야 함

        if (prefs.getInt("try", 0) == 0){
            ref.push().setValue(u);
            info_inserted++;
            SharedPreferences.Editor editor19 = prefs.edit();
            editor19.putInt("try", (int) info_inserted);
            editor19.apply();
        }



        ////  u를 넣을 때 key 값? -MSSPJ_RCggj-pxwtmkS 이걸 저장한다음에 이걸 삭제하자











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

        ///////////////////////////////////////////////////////////////////////////////////





/*
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // RecyclerView 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // User 객체를 담을 Array List (Adapter 쪽으로)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("User"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열 리스트가 존재하지 않도록 초기화
                for (DataSnapshot snapshot1 : datasnapshot.getChildren()) { // 반복문으로 데이터 List를 추출함
                    User user = snapshot1.getValue(User.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList.add(user); // 담은 데이터들을 배열리스트에 넣고 RecyclerView로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("HomeActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // RecyclerView 에 Adapter 연결
*/
    }
}

class User_Info{
    public String photouri;
    public String playername;
    public String playergpa;
    public String playerscore;

    public User_Info(String photoUrl, String nickName, float meanscore, float highscore){

    }

    public User_Info(String photouri, String playername, String playergpa, String playerscore){
            this.photouri = photouri;
            this.playername = playername;
            this.playergpa = playergpa;
            this.playerscore = playerscore;
    }
}