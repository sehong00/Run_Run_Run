package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;

public class GameMoving {

    int toThrow = 0;
    boolean isGoingUp = false, isGoingDown = false;
    int x, y, width, height;
    Bitmap flight, throw_knifes, dead;
    private GameView gameView;

    GameMoving(GameView gameView, int screenY, Resources res) {

        this.gameView = gameView;

        flight = BitmapFactory.decodeResource(res, R.drawable.amongus_white);

        width = flight.getWidth();
        height = flight.getHeight();

        width /= 2;
        height /= 2;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        flight = Bitmap.createScaledBitmap(flight, width, height, false);

        throw_knifes = BitmapFactory.decodeResource(res, R.drawable.amongus_black);
        throw_knifes = Bitmap.createScaledBitmap(throw_knifes, width, height, false);

        dead = BitmapFactory.decodeResource(res, R.drawable.amongus_dead);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);

    }

    Bitmap getFlight () { // 칼을 던질 때 모션 넣기

        if (toThrow != 0) {

            toThrow--;
            gameView.newKnife();

            return throw_knifes; // 칼을 던질 때 검은 색 임포스터로 바뀜
        }
        
        return flight; // 다시 흰 색 임포스터로 바뀜

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead () {
        return dead;
    }

}
