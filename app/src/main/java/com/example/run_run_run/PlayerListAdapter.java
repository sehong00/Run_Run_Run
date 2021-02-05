package com.example.run_run_run;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {

    private ArrayList<User_Info> players_list;
    private Context context;

    public PlayerListAdapter(ArrayList<User_Info> players_list, Context context) {
        this.players_list = players_list;
        this.context = context;

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false);
        PlayerViewHolder holder = new PlayerViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {

        Glide.with(holder.itemView).load(players_list.get(position).getPhotouri()).into(holder.playerimg);

        holder.playername.setText(players_list.get(position).getPlayername());
        holder.playerhighscore.setText(""+players_list.get(position).getHighscore());
        holder.playergpa.setText(""+players_list.get(position).getPlayerscore());

    }

    @Override
    public int getItemCount() {
        return (players_list != null ? players_list.size() : 0);
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{

        ImageView playerimg;
        TextView playername;
        TextView playerhighscore;
        TextView playergpa;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            this.playerimg = itemView.findViewById(R.id.player_goggle_img);
            this.playername = itemView.findViewById(R.id.player_google_name);
            this.playerhighscore = itemView.findViewById(R.id.player_highScoreTxt);
            this.playergpa = itemView.findViewById(R.id.player_gpa);

        }
    }

        /*
        private static final String TAG = "PlayerListAddapter";

        private Context mContext;
        int mResouse;

        public PlayerListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User_Info> objects) {
            super(context, resource, objects);

            this.mContext = context;
            this.mResouse = resource;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            String player_img = getItem(position).getPhotouri();
            String player_name = getItem(position).getPlayername();
            Float player_highscore = getItem(position).getHighscore();
            Float player_gpa = getItem(position).getPlayerscore();

            User_Info person = new User_Info(player_img, player_name, player_highscore, player_gpa);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResouse, parent, false);

            ImageView playerimg = (ImageView) convertView.findViewById(R.id.player_goggle_img);
            TextView playername = (TextView) convertView.findViewById(R.id.player_google_name);
            TextView playerhighscore = (TextView) convertView.findViewById(R.id.player_highScoreTxt);
            TextView playergpa = (TextView) convertView.findViewById(R.id.player_gpa);

            playerimg.setImageURI(Uri.parse(player_img));
            playername.setText(player_name);
            playerhighscore.setText(String.valueOf(player_highscore));
            playergpa.setText(String.valueOf(player_gpa));

            return convertView;

        }
    */
}