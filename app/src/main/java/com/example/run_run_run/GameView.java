package com.example.run_run_run;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// import static com.example.run_run_run.GameActivity.mediaPlayer;


public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY;
    public static float score = 0;
    public static int number_of_subjects = 0;
    public static int round = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private GradeF[] grades_f;
    private GradeAplus[] grades_aplus;
    private GradeA[] grades_a;
    private GradeBplus[] grades_bplus;
    private GradeB[] grades_b;
    private GradeCplus[] grades_cplus;
    private GradeC[] grades_c;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<GameKnife> knifes;
    private int sound;
    private GameMoving flight;
    private GameActivity activity;
    private GameBackground background1, background2;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 무기를 던질 때 사운드 설정

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.knife, 2);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new GameBackground(screenX, screenY, getResources());
        background2 = new GameBackground(screenX, screenY, getResources());

        flight = new GameMoving(this, screenY, getResources());

        knifes = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        grades_f = new GradeF[4];
        grades_aplus = new GradeAplus[1];
        grades_a = new GradeA[1];
        grades_bplus = new GradeBplus[2];
        grades_b = new GradeB[2];
        grades_cplus = new GradeCplus[1];
        grades_c = new GradeC[1];

        for (int i = 0; i < 4; i++) { // 화면에 등장하는 F 의 개수: 4개

            GradeF grade_f = new GradeF(getResources());
            grades_f[i] = grade_f;

        }

        for (int i = 0; i < 2; i++) { // 화면에 등장하는 B+, B 의 개수: 2개

            GradeBplus grade_bplus = new GradeBplus(getResources());
            grades_bplus[i] = grade_bplus;
            GradeB grade_b = new GradeB(getResources());
            grades_b[i] = grade_b;

        }

        for (int i = 0; i < 1; i++) { // 화면에 등장하는 A+, A, C+, C 의 개수: 1개

            GradeAplus grade_aplus = new GradeAplus(getResources());
            grades_aplus[i] = grade_aplus;
            GradeA grade_a = new GradeA(getResources());
            grades_a[i] = grade_a;
            GradeCplus grade_cplus = new GradeCplus(getResources());
            grades_cplus[i] = grade_cplus;
            GradeC grade_c = new GradeC(getResources());
            grades_c[i] = grade_c;

        }


        random = new Random();
        
    }

    @Override
    public void run() { // 게임이 진행되는 동안 실행

        while (isPlaying) {

            update ();
            draw ();
            sleep ();

        }

    }

    private void update () {

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;



        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (flight.isGoingUp) { // 왼쪽 위 화면 터치했을 때 상승
            flight.isGoingDown = false;
            flight.y -= 30 * screenRatioY;
        } else {
            flight.y += 3 * screenRatioY;

        }

        if (flight.isGoingDown) { // 왼쪽 아래 화면 터치했을 때 하강
            flight.isGoingUp = false;
            flight.y += 30 * screenRatioY;
        } else {
            flight.y += 3 * screenRatioY;
        }

        if (flight.y < 0) // 화면 위에 닿았을 경우
            flight.y = 0;

        if (flight.y >= screenY - flight.height)
            flight.y = screenY - flight.height;

        List<GameKnife> trash = new ArrayList<>();

        for (GameKnife knife : knifes) {

            if (knife.x > screenX)
                trash.add(knife);

            knife.x += 50 * screenRatioX; // 칼의 이동 속도

            for (GradeF grade_f : grades_f) {

                if (Rect.intersects(grade_f.getCollisionShape(), knife.getCollisionShape())) { // 만약 칼과 F가 충돌하면

                    grade_f.x = -500;
                    knife.x = screenX + 500;
                    grade_f.wasKilled = true;

                }

            }

        }

        for (GameKnife knife : trash)
            knifes.remove(knife);

        for (GradeF grade_f : grades_f) {

            grade_f.x -= grade_f.speed;

            if (grade_f.x + grade_f.width < 0) {

                if (!grade_f.wasKilled) { // 만약 F가 칼에 맞지 않고 화면 왼쪽에 닿으면
                    isGameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                grade_f.speed = random.nextInt(bound);

                if (grade_f.speed < 10 * screenRatioX)
                    grade_f.speed = (int) (10 * screenRatioX);

                grade_f.x = screenX;
                grade_f.y = random.nextInt(screenY - grade_f.height);

                grade_f.wasKilled = false;
            }

            if (Rect.intersects(grade_f.getCollisionShape(), flight.getCollisionShape())) { // 만약 플레이어와 F가 충돌하면

                isGameOver = true;
                return;
            }

        }

        for (GradeAplus grade_aplus : grades_aplus) {

            grade_aplus.x -= grade_aplus.speed;

            if (grade_aplus.x + grade_aplus.width < 0) {

                int bound = (int) (30 * screenRatioX);
                grade_aplus.speed = random.nextInt(bound);

                if (grade_aplus.speed < 10 * screenRatioX)
                    grade_aplus.speed = (int) (10 * screenRatioX);

                grade_aplus.x = screenX;
                grade_aplus.y = random.nextInt(screenY - grade_aplus.height);

                grade_aplus.wasKilled = false;
            }

            if (Rect.intersects(grade_aplus.getCollisionShape(), flight.getCollisionShape())) {  // 만약 플레이어와 A+가 충돌하면

                score += 4.5;
                grade_aplus.x = -500;
                grade_aplus.wasKilled = true;
                number_of_subjects++;
                return;
            }

        }

        for (GradeA grade_a : grades_a) {

            grade_a.x -= grade_a.speed;

            if (grade_a.x + grade_a.width < 0) {

                int bound = (int) (30 * screenRatioX);
                grade_a.speed = random.nextInt(bound);

                if (grade_a.speed < 10 * screenRatioX)
                    grade_a.speed = (int) (10 * screenRatioX);

                grade_a.x = screenX;
                grade_a.y = random.nextInt(screenY - grade_a.height);

                grade_a.wasKilled = false;
            }

            if (Rect.intersects(grade_a.getCollisionShape(), flight.getCollisionShape())) { // 만약 플레이어와 A가 충돌하면

                score += 4;
                grade_a.x = -500;
                grade_a.wasKilled = true;
                number_of_subjects++;
                return;

            }

        }

        for (GradeBplus grade_bplus : grades_bplus) {

            grade_bplus.x -= grade_bplus.speed;

            if (grade_bplus.x + grade_bplus.width < 0) {

                int bound = (int) (30 * screenRatioX);
                grade_bplus.speed = random.nextInt(bound);

                if (grade_bplus.speed < 10 * screenRatioX)
                    grade_bplus.speed = (int) (10 * screenRatioX);

                grade_bplus.x = screenX;
                grade_bplus.y = random.nextInt(screenY - grade_bplus.height);

                grade_bplus.wasKilled = false;
            }

            if (Rect.intersects(grade_bplus.getCollisionShape(), flight.getCollisionShape())) { // 만약 플레이어와 B+가 충돌하면

                score += 3.5;
                grade_bplus.x = -500;
                grade_bplus.wasKilled = true;
                number_of_subjects++;
                return;

            }

        }

        for (GradeB grade_b : grades_b) {

            grade_b.x -= grade_b.speed;

            if (grade_b.x + grade_b.width < 0) {

                int bound = (int) (30 * screenRatioX);
                grade_b.speed = random.nextInt(bound);

                if (grade_b.speed < 10 * screenRatioX)
                    grade_b.speed = (int) (10 * screenRatioX);

                grade_b.x = screenX;
                grade_b.y = random.nextInt(screenY - grade_b.height);

                grade_b.wasKilled = false;
            }

            if (Rect.intersects(grade_b.getCollisionShape(), flight.getCollisionShape())) { // 만약 플레이어와 B가 충돌하면

                score += 3;
                grade_b.x = -500;
                grade_b.wasKilled = true;
                number_of_subjects++;
                return;

            }

        }

        for (GradeCplus grade_cplus : grades_cplus) {

            grade_cplus.x -= grade_cplus.speed;

            if (grade_cplus.x + grade_cplus.width < 0) {

                int bound = (int) (30 * screenRatioX);
                grade_cplus.speed = random.nextInt(bound);

                if (grade_cplus.speed < 10 * screenRatioX)
                    grade_cplus.speed = (int) (10 * screenRatioX);

                grade_cplus.x = screenX;
                grade_cplus.y = random.nextInt(screenY - grade_cplus.height);

                grade_cplus.wasKilled = false;
            }

            if (Rect.intersects(grade_cplus.getCollisionShape(), flight.getCollisionShape())) { // 만약 플레이어와 C+가 충돌하면

                score += 2.5;
                grade_cplus.x = -500;
                grade_cplus.wasKilled = true;
                number_of_subjects++;
                return;

            }

        }

        for (GradeC grade_c : grades_c) {

            grade_c.x -= grade_c.speed;

            if (grade_c.x + grade_c.width < 0) {

                int bound = (int) (30 * screenRatioX);
                grade_c.speed = random.nextInt(bound);

                if (grade_c.speed < 10 * screenRatioX)
                    grade_c.speed = (int) (10 * screenRatioX);

                grade_c.x = screenX;
                grade_c.y = random.nextInt(screenY - grade_c.height);

                grade_c.wasKilled = false;
            }

            if (Rect.intersects(grade_c.getCollisionShape(), flight.getCollisionShape())) { // 만약 플레이어와 C가 충돌하면

                score += 2;
                grade_c.x = -500;
                grade_c.wasKilled = true;
                number_of_subjects++;
                return;
            }

        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (GradeF grade_f : grades_f)
                canvas.drawBitmap(grade_f.getGrade(), grade_f.x, grade_f.y, paint);

            for (GradeAplus grade_aplus : grades_aplus)
                canvas.drawBitmap(grade_aplus.getGrade(),grade_aplus.x, grade_aplus.y, paint);
            for (GradeA grade_a : grades_a)
                canvas.drawBitmap(grade_a.getGrade(),grade_a.x, grade_a.y, paint);
            for (GradeBplus grade_bplus : grades_bplus)
                canvas.drawBitmap(grade_bplus.getGrade(),grade_bplus.x, grade_bplus.y, paint);
            for (GradeB grade_b : grades_b)
                canvas.drawBitmap(grade_b.getGrade(),grade_b.x, grade_b.y, paint);
            for (GradeCplus grade_cplus : grades_cplus)
                canvas.drawBitmap(grade_cplus.getGrade(),grade_cplus.x, grade_cplus.y, paint);
            for (GradeC grade_c : grades_c)
                canvas.drawBitmap(grade_c.getGrade(),grade_c.x, grade_c.y, paint);

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver) { // 게임이 종료 되었을 때
                isPlaying = false;
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();
                return;
            }

            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);

            for (GameKnife bullet : knifes)
                canvas.drawBitmap(bullet.knife, bullet.x, bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() { // 다음 화면으로 넘어가기 전 죽은 상태로 3초 딜레이

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, GameResult.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() { // 점수 저장

        SharedPreferences.Editor editor2 = prefs.edit();
        editor2.putFloat("score", score);
        editor2.apply();

        SharedPreferences.Editor editor3 = prefs.edit();
        editor3.putInt("number_of_subjects", number_of_subjects);
        editor3.apply();

        score = 0;
        number_of_subjects = 0;

    }

    private void sleep () { // 60 Frame
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2 && event.getY() < screenY / 2) { // 왼쪽 위 화면 터치했을 때
                    flight.isGoingUp = true;
                } else if (event.getX()< screenX / 2 && event.getY() > screenY / 2) // 왼쪽 아래 화면 터치했을 때
                    flight.isGoingDown = true;
                if (event.getX() > screenX / 2 ) // 오른쪽 화면 터치했을 때
                    flight.toThrow++;
                break;
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                break;
        }

        return true;
    }

    public void newKnife() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);

        GameKnife bullet = new GameKnife(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        knifes.add(bullet);

    }

}