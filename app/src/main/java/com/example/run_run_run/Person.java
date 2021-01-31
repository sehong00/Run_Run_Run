package com.example.run_run_run;

import android.widget.ImageView;

public class Person {

    private String player_img;
    private String player_name;
    private String player_highscore;
    private String player_gpa;

    public Person(String player_img, String player_name, String player_highscore, String player_gpa){
        this.player_img = player_img;
        this.player_name = player_name;
        this.player_highscore = player_highscore;
        this.player_gpa = player_gpa;
    }

    public String getPlayer_img() {
        return player_img;
    }

    public void setPlayer_img(String player_img) {
        this.player_img = player_img;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_highscore() {
        return player_highscore;
    }

    public void setPlayer_highscore(String player_highscore) {
        this.player_highscore = player_highscore;
    }

    public String getPlayer_gpa() {
        return player_gpa;
    }

    public void setPlayer_gpa(String player_gpa) {
        this.player_gpa = player_gpa;
    }
}
