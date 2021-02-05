package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;

public class GradeA {

    public int speed = 20;
    public boolean wasKilled = true;
    int x = 0, y, width, height, aCounter = 1;
    Bitmap grade_a1, grade_a2, grade_a3, grade_a4;

    GradeA(Resources res) {

        grade_a1 = BitmapFactory.decodeResource(res, R.drawable.grade_a1);
        grade_a2 = BitmapFactory.decodeResource(res, R.drawable.grade_a2);
        grade_a3 = BitmapFactory.decodeResource(res, R.drawable.grade_a3);
        grade_a4 = BitmapFactory.decodeResource(res, R.drawable.grade_a4);

        width = grade_a1.getWidth();
        height = grade_a1.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        grade_a1 = Bitmap.createScaledBitmap(grade_a1, width, height, false);
        grade_a2 = Bitmap.createScaledBitmap(grade_a2, width, height, false);
        grade_a3 = Bitmap.createScaledBitmap(grade_a3, width, height, false);
        grade_a4 = Bitmap.createScaledBitmap(grade_a4, width, height, false);

        y = -height;

    }

    Bitmap getGrade() { // A 이미지가 위 아래로 움직이도록 보이게 함

        if (aCounter == 1) {
            aCounter++;
            return grade_a1;
        }

        if (aCounter == 2) {
            aCounter++;
            return grade_a2;
        }

        if (aCounter == 3) {
            aCounter++;
            return grade_a3;
        }

        aCounter = 1;

        return grade_a4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}