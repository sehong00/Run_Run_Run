package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;

public class GradeC {

    public int speed = 20;
    public boolean wasKilled = true;
    int x = 0, y, width, height, cCounter = 1;
    Bitmap grade_c1, grade_c2, grade_c3, grade_c4;

    GradeC(Resources res) {

        grade_c1 = BitmapFactory.decodeResource(res, R.drawable.grade_c1);
        grade_c2 = BitmapFactory.decodeResource(res, R.drawable.grade_c2);
        grade_c3 = BitmapFactory.decodeResource(res, R.drawable.grade_c3);
        grade_c4 = BitmapFactory.decodeResource(res, R.drawable.grade_c4);

        width = grade_c1.getWidth();
        height = grade_c1.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        grade_c1 = Bitmap.createScaledBitmap(grade_c1, width, height, false);
        grade_c2 = Bitmap.createScaledBitmap(grade_c2, width, height, false);
        grade_c3 = Bitmap.createScaledBitmap(grade_c3, width, height, false);
        grade_c4 = Bitmap.createScaledBitmap(grade_c4, width, height, false);

        y = -height;

    }

    Bitmap getGrade() {

        if (cCounter == 1) {
            cCounter++;
            return grade_c1;
        }

        if (cCounter == 2) {
            cCounter++;
            return grade_c2;
        }

        if (cCounter == 3) {
            cCounter++;
            return grade_c3;
        }

        cCounter = 1;

        return grade_c4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}