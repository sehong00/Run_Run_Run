package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;

public class GradeAplus {

    public int speed = 20;
    public boolean wasKilled = true;
    int x = 0, y, width, height, aplusCounter = 1;
    Bitmap grade_aplus1, grade_aplus2, grade_aplus3, grade_aplus4;

    GradeAplus(Resources res) {

        grade_aplus1 = BitmapFactory.decodeResource(res, R.drawable.grade_aplus1);
        grade_aplus2 = BitmapFactory.decodeResource(res, R.drawable.grade_aplus2);
        grade_aplus3 = BitmapFactory.decodeResource(res, R.drawable.grade_aplus3);
        grade_aplus4 = BitmapFactory.decodeResource(res, R.drawable.grade_aplus4);

        width = grade_aplus1.getWidth();
        height = grade_aplus1.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        grade_aplus1 = Bitmap.createScaledBitmap(grade_aplus1, width, height, false);
        grade_aplus2 = Bitmap.createScaledBitmap(grade_aplus2, width, height, false);
        grade_aplus3 = Bitmap.createScaledBitmap(grade_aplus3, width, height, false);
        grade_aplus4 = Bitmap.createScaledBitmap(grade_aplus4, width, height, false);

        y = -height;

    }

    Bitmap getGrade() { // A+ 이미지가 위 아래로 움직이도록 보이게 함

        if (aplusCounter == 1) {
            aplusCounter++;
            return grade_aplus1;
        }

        if (aplusCounter == 2) {
            aplusCounter++;
            return grade_aplus2;
        }

        if (aplusCounter == 3) {
            aplusCounter++;
            return grade_aplus3;
        }

        aplusCounter = 1;

        return grade_aplus4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}