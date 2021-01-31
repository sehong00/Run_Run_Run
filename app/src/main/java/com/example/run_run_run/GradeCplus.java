package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;

public class GradeCplus {

    public int speed = 20;
    public boolean wasKilled = true;
    int x = 0, y, width, height, cplusCounter = 1;
    Bitmap grade_cplus1, grade_cplus2, grade_cplus3, grade_cplus4;

    GradeCplus(Resources res) {

        grade_cplus1 = BitmapFactory.decodeResource(res, R.drawable.grade_cplus1);
        grade_cplus2 = BitmapFactory.decodeResource(res, R.drawable.grade_cplus2);
        grade_cplus3 = BitmapFactory.decodeResource(res, R.drawable.grade_cplus3);
        grade_cplus4 = BitmapFactory.decodeResource(res, R.drawable.grade_cplus4);

        width = grade_cplus1.getWidth();
        height = grade_cplus1.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        grade_cplus1 = Bitmap.createScaledBitmap(grade_cplus1, width, height, false);
        grade_cplus2 = Bitmap.createScaledBitmap(grade_cplus2, width, height, false);
        grade_cplus3 = Bitmap.createScaledBitmap(grade_cplus3, width, height, false);
        grade_cplus4 = Bitmap.createScaledBitmap(grade_cplus4, width, height, false);

        y = -height;

    }

    Bitmap getGrade() {

        if (cplusCounter == 1) {
            cplusCounter++;
            return grade_cplus1;
        }

        if (cplusCounter == 2) {
            cplusCounter++;
            return grade_cplus2;
        }

        if (cplusCounter == 3) {
            cplusCounter++;
            return grade_cplus3;
        }

        cplusCounter = 1;

        return grade_cplus4;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}