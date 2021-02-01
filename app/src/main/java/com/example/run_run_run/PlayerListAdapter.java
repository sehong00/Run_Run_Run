
package com.example.run_run_run;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PlayerListAdapter extends ArrayAdapter<Person> {

    private static final String TAG = "PlayerListAddapter";

    private Context mContext;
    int mResouse;

    public PlayerListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Person> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResouse = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String player_img = getItem(position).getPlayer_img();
        String player_name = getItem(position).getPlayer_name();
        String player_highscore = getItem(position).getPlayer_highscore();
        String player_gpa = getItem(position).getPlayer_gpa();

        Person person = new Person(player_img, player_name, player_highscore, player_gpa);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResouse, parent, false);


        ImageView playerimg = (ImageView) convertView.findViewById(R.id.player_goggle_img);

        TextView playername = (TextView) convertView.findViewById(R.id.player_google_name);
        TextView playerhighscore = (TextView) convertView.findViewById(R.id.player_highScoreTxt);
        TextView playergpa = (TextView) convertView.findViewById(R.id.player_gpa);

        playerimg.setImageURI(Uri.parse(player_img));
        playername.setText(player_name);
        playerhighscore.setText(player_highscore);
        playergpa.setText(player_gpa);

        return convertView;

    }
}