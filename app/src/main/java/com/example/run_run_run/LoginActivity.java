package com.example.run_run_run;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SignInButton btn_google; // 구글 로그인 버튼
    private FirebaseAuth auth; // 파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient; // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드

    public static float highscore=0, meanscore=0;

    FirebaseDatabase database;
    DatabaseReference ref, res;
    public static String nickName, photoUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString((R.string.default_web_client_id)))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance(); // 파이어베이스 인증 객체 초기화

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() { // 구글 로그인 버튼을 클릭했을 때 이곳을 수행
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);

                /////////////////////////////
            }
        });
/*
        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        nickName = prefs.getString("nick", "a");
        photoUrl = prefs.getString("url", "b");

 */
        System.out.println("ㅇㅇ1" + nickName);
        System.out.println("ㅇㅇ2" + photoUrl);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // 구글 로그인 인증을 요청했을 때 결과 값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) { //인증 결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount(); // account 라는 데이터는 구글 로그인 정보를 담고 있습니다 (닉네임, 프로필 사진 Url, 이메일 주소 등)
                resultLogin(account); // 로그인 결과 값 출력 수행하라는 메소드
            }
        }

    }

    public void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) { // 로그인이 성공했으면
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
/*
                            Intent intent2 = new Intent(getApplicationContext(), GameResult.class);
                            intent2.putExtra("nickName2", account.getDisplayName());
                            intent2.putExtra("photoUrl2", String.valueOf(account.getPhotoUrl()));
*/

/*
                            String aaa = account.getDisplayName();
                            String bbb = String.valueOf(account.getPhotoUrl());
                            final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
                            SharedPreferences.Editor editor11 = prefs.edit();
                            editor11.putString("nick", aaa);
                            editor11.apply();
                            SharedPreferences.Editor editor12 = prefs.edit();
                            editor12.putString("url", bbb);
                            editor12.apply();
*/




                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("nickName", account.getDisplayName());
                            intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl())); // String.valueOf() : 특정 자료형을 String 형태로 변환
                            startActivity(intent);




                            database = FirebaseDatabase.getInstance();
                            ref = database.getReference("users");
                            if (account.getDisplayName() == ref.getKey()) {

                            } else {
                                User_Information u = new User_Information(String.valueOf(account.getPhotoUrl()), account.getDisplayName(), highscore, meanscore);
                                ref.child(account.getDisplayName()).setValue(u);
                            }



                        } else { // 로그인이 실패했으면
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}