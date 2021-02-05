package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;

public class GradeBplus {

    public int speed = 20;
    public boolean wasKilled = true;
    int x = 0, y, width, height, bplusCounter = 1;
    Bitmap grade_bplus1, grade_bplus2, grade_bplus3, grade_bplus4;

    GradeBplus(Resources res) {

        grade_bplus1 = BitmapFactory.decodeResource(res, R.drawable.grade_bplus1);
        grade_bplus2 = BitmapFactory.decodeResource(res, R.drawable.grade_bplus2);
        grade_bplus3 = BitmapFactory.decodeResource(res, R.drawable.grade_bplus3);
        grade_bplus4 = BitmapFactory.decodeResource(res, R.drawable.grade_bplus4);

        width = grade_bplus1.getWidth();
        height = grade_bplus1.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        grade_bplus1 = Bitmap.createScaledBitmap(grade_bplus1, width, height, false);
        grade_bplus2 = Bitmap.createScaledBitmap(grade_bplus2, width, height, false);
        grade_bplus3 = Bitmap.createScaledBitmap(grade_bplus3, width, height, false);
        grade_bplus4 = Bitmap.createScaledBitmap(grade_bplus4, width, height, false);

        y = -height;

    }

    Bitmap getGrade() {

        if (bplusCounter == 1) {
            bplusCounter++;
            return grade_bplus1;
        }

        if (bplusCounter == 2) {
            bplusCounter++;
            return grade_bplus2;
        }

        if (bplusCounter == 3) {
            bplusCounter++;
            return grade_bplus3;
        }

        bplusCounter = 1;

        return grade_bplus4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}