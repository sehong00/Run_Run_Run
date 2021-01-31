package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;


public class GameKnife {

    int x, y, width, height;
    Bitmap knife;

    GameKnife(Resources res) {

        knife = BitmapFactory.decodeResource(res, R.drawable.knife); // 원본 이미지 Bitmap

        Matrix sideInversion = new Matrix(); // 좌우반전 이미지 효과 및 Bitmap 만들기
        sideInversion.setScale(-1, 1);
        Bitmap knife_reversion = Bitmap.createBitmap(this.knife, 0, 0,
                this.knife.getWidth(), this.knife.getHeight(), sideInversion, false);

        width = knife_reversion.getWidth();
        height = knife_reversion.getHeight();

        width /= 20;
        height /= 20;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        this.knife = Bitmap.createScaledBitmap(knife_reversion, width, height, false);

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}
