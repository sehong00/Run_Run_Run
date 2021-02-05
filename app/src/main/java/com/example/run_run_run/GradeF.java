package com.example.run_run_run;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.run_run_run.GameView.screenRatioX;
import static com.example.run_run_run.GameView.screenRatioY;

public class GradeF {

    public int speed = 20;
    public boolean wasKilled = true;
    int x = 0, y, width, height, fCounter = 1;
    Bitmap grade_f1, grade_f2, grade_f3, grade_f4;

    GradeF(Resources res) {

        grade_f1 = BitmapFactory.decodeResource(res, R.drawable.grade_f1);
        grade_f2 = BitmapFactory.decodeResource(res, R.drawable.grade_f2);
        grade_f3 = BitmapFactory.decodeResource(res, R.drawable.grade_f3);
        grade_f4 = BitmapFactory.decodeResource(res, R.drawable.grade_f4);

        width = grade_f1.getWidth();
        height = grade_f1.getHeight();

        width /= 7;
        height /= 7;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        grade_f1 = Bitmap.createScaledBitmap(grade_f1, width, height, false);
        grade_f2 = Bitmap.createScaledBitmap(grade_f2, width, height, false);
        grade_f3 = Bitmap.createScaledBitmap(grade_f3, width, height, false);
        grade_f4 = Bitmap.createScaledBitmap(grade_f4, width, height, false);

        y = -height;
    }

    Bitmap getGrade() { // F 이미지가 위 아래로 움직이도록 보이게 함

        if (fCounter == 1) {
            fCounter++;
            return grade_f1;
        }

        if (fCounter == 2) {
            fCounter++;
            return grade_f2;
        }

        if (fCounter == 3) {
            fCounter++;
            return grade_f3;
        }

        fCounter = 1;

        return grade_f4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}
