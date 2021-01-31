package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;

public class GradeB {

    public int speed = 20;
    public boolean wasKilled = true;
    int x = 0, y, width, height, bCounter = 1;
    Bitmap grade_b1, grade_b2, grade_b3, grade_b4;

    GradeB(Resources res) {

        grade_b1 = BitmapFactory.decodeResource(res, R.drawable.grade_b1);
        grade_b2 = BitmapFactory.decodeResource(res, R.drawable.grade_b2);
        grade_b3 = BitmapFactory.decodeResource(res, R.drawable.grade_b3);
        grade_b4 = BitmapFactory.decodeResource(res, R.drawable.grade_b4);

        width = grade_b1.getWidth();
        height = grade_b1.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        grade_b1 = Bitmap.createScaledBitmap(grade_b1, width, height, false);
        grade_b2 = Bitmap.createScaledBitmap(grade_b2, width, height, false);
        grade_b3 = Bitmap.createScaledBitmap(grade_b3, width, height, false);
        grade_b4 = Bitmap.createScaledBitmap(grade_b4, width, height, false);

        y = -height;

    }

    Bitmap getGrade() {

        if (bCounter == 1) {
            bCounter++;
            return grade_b1;
        }

        if (bCounter == 2) {
            bCounter++;
            return grade_b2;
        }

        if (bCounter == 3) {
            bCounter++;
            return grade_b3;
        }

        bCounter = 1;

        return grade_b4;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}