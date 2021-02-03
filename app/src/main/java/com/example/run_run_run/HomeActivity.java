package com.example.run_run_run;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    public static float highscore=0, totalscore=0, meanscore=0;
    public static String nickName, photoUrl;

    private static final String TAG = "HomeAcitvity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User_Info> arrayList;
//    private FirebaseDatabase databaseRef;
    private DatabaseReference databaseerence;

    FirebaseDatabase database;
    DatabaseReference ref, res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName"); // LoginActivity 로부터 닉네임 전달받음
        photoUrl = intent.getStringExtra("photoUrl"); // LoginActivity 로부터 프로필 사진 전달받음

        tv_google_name = findViewById(R.id.tv_google_name);
        tv_google_name.setText(nickName); // 닉네임 text를 텍스트 뷰에 세팅

        iv_google_img = findViewById(R.id.iv_goggle_img);
        Glide.with(this).load(photoUrl).into(iv_google_img); // 프로필 Url을 이미지 뷰에 세팅

        Button btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_2 = new Intent(getApplicationContext(), LoadingActivity.class);
                intent_2.putExtra("nickName2", nickName);
                intent_2.putExtra("photoUrl2", photoUrl);

                startActivity(intent_2);
            }
        });

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        TextView gpaTxt = findViewById(R.id.gpa);
/*
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
*/


        /////////////////////////////////////////////////////////

        //이거 게임 결과창으로 옮겨서 DB에 저장해서 오류 안나도록 하자!!
        ////////////////////////////
        ////////////
        ///////
        /////////////////////////////////////////



        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        User_Information u = new User_Information(photoUrl, nickName, highscore, meanscore);
        res = database.getReference("users/" + nickName);

//        res.setValue(null);
//        ref.child(nickName).setValue(u);


        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        int info_inserted = 0;
/*
        if (prefs.getInt("try", 0) == 1) {
            ref.child(nickName).setValue(null);
            ref.child(nickName).setValue(u);

        } else if (prefs.getInt("try", 0) == 2) {

        } else {
            ref.child(nickName).setValue(u);

            info_inserted = 1;

            SharedPreferences.Editor editor19 = prefs.edit();
            editor19.putInt("try", info_inserted);
            editor19.apply();
        }
*/
        SharedPreferences.Editor editor21 = prefs.edit();
        editor21.putString("nick", nickName);
        editor21.apply();
        SharedPreferences.Editor editor22 = prefs.edit();
        editor21.putString("url", photoUrl);
        editor22.apply();


        //만약에 최고점수 달성하고 학점 높아지면 DB 없애고 다시 만들어야 함
/*
        if (prefs.getInt("try", 0) == 0){
            ref.push().setValue(u);

        }
*/
        //https://javapp.tistory.com/148
/*
        Log.d(TAG, "onCreate: Started.");
        ListView player_listView = (ListView) findViewById(R.id.player_list);
        User_Info aaa = new User_Info(photoUrl, nickName, highscore, meanscore);
        User_Info steve = new User_Info("ada", "bbb", highscore, meanscore);
*/

/*
        players_list.add(aaa);
        players_list.add(steve);
*/


        ///////////////////////////////////////////////////////////////////////////////////

            ////  u를 넣을 때 key 값? -MSSPJ_RCggj-pxwtmkS 이걸 저장한다음에 이걸 삭제하자

        recyclerView = findViewById(R.id.player_list);
        recyclerView.setHasFixedSize(true); // RecyclerView 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // User 객체를 담을 Array List (Adapter 쪽으로)

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                // Firebase 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : datasnapshot.getChildren()) { // 반복문으로 데이터 List 를 추출함
                    User_Info user_info = snapshot1.getValue(User_Info.class); // 만들어줬던 User_Info 객체에 데이터를 담는다.
                    arrayList.add(user_info); // 담은 데이터들을 배열 리스트에 넣고 리스트 뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던 중 Error 발생 시
                Log.e("HomeActivity", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new PlayerListAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);




/*
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
*/







/*
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // RecyclerView 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

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

    private long time= 0;

    @Override

    public void onBackPressed(){

        if(System.currentTimeMillis() - time >= 2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

        else if(System.currentTimeMillis() - time < 2000 ){

            final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
            int info_inserted = 1;
            SharedPreferences.Editor editor19 = prefs.edit();
            editor19.putInt("try", info_inserted);
            editor19.apply();

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
