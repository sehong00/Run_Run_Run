package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;


public class GameBullet {

    int x, y, width, height;
    Bitmap bullet;

    GameBullet(Resources res) {

        //원본 이미지 Bitmap
        bullet = BitmapFactory.decodeResource(res, R.drawable.knife);

        //좌우반전 이미지 효과 및 Bitmap 만들기
        Matrix sideInversion = new Matrix();
        sideInversion.setScale(-1, 1);
        Bitmap bullet = Bitmap.createBitmap(this.bullet, 0, 0,
                this.bullet.getWidth(), this.bullet.getHeight(), sideInversion, false);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 20;
        height /= 20;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        this.bullet = Bitmap.createScaledBitmap(bullet, width, height, false);

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}
