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
    int x, y, width, height, wingCounter = 0, shootCounter = 1;
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

    Bitmap getFlight () {

        if (toThrow != 0) {

            toThrow--;
            gameView.newKnife();

            return throw_knifes;
        }

        /*
        if (wingCounter == 0) {
            wingCounter++;
            return flight1;
        }
        wingCounter--;
        */

        return flight;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead () {
        return dead;
    }

}
