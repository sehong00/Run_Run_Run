
package com.example.run_run_run;

import android.widget.ImageView;

public class User_Info {

    private String photouri;
    private String playername;
    private float highscore;
    private float playerscore;
/*
    public User_Info(String photouri, String playername, Float highscore, Float playerscore){
        this.photouri = photouri;
        this.playername = playername;
        this.highscore = highscore;
        this.playerscore = playerscore;
    }
*/
    public User_Info(){

    }


    public String getPhotouri() {
        return photouri;
    }

    public void setPhotouri(String photouri) {
        this.photouri = photouri;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public float getHighscore() {
        return highscore;
    }

    public void setHighscore(float highscore) {
        this.highscore = highscore;
    }

    public float getPlayerscore() {
        return playerscore;
    }

    public void setPlayerscore(float playerscore) {
        this.playerscore = playerscore;
    }
}
