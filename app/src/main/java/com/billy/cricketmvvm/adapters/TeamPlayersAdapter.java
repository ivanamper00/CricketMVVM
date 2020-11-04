package com.billy.cricketmvvm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.cricketmvvm.R;
import com.billy.cricketmvvm.models.players.PlayerDetailsModel;
import com.github.islamkhsh.CardSliderAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamPlayersAdapter extends CardSliderAdapter<TeamPlayersAdapter.TeamPlayersViewHolder> {
    Context context;
    List<PlayerDetailsModel> seriesTeamPlayerList;
    PlayerDetailsModel seriesTeamPlayers;
    public class TeamPlayersViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView playerBatting;
        TextView playerBowling;
        TextView playerType;
        ImageView playerImage;
        public TeamPlayersViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.player_name);
            playerBatting = itemView.findViewById(R.id.player_batting);
            playerBowling = itemView.findViewById(R.id.player_bowling);
            playerType = itemView.findViewById(R.id.player_type);
            playerImage = itemView.findViewById(R.id.player_image);
        }
    }
    public TeamPlayersAdapter(Context context, List<PlayerDetailsModel> seriesTeamPlayerList){
        this.context = context;
        this.seriesTeamPlayerList = seriesTeamPlayerList;
    }

    @NonNull
    @Override
    public TeamPlayersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_players_list,parent,false);
        return new TeamPlayersViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindVH(TeamPlayersViewHolder holder, int position) {
        seriesTeamPlayers = seriesTeamPlayerList.get(position);

        holder.playerName.setText(seriesTeamPlayers.getFullName());
        holder.playerBatting.setText("Batting: "+seriesTeamPlayers.getBattingStyle());
        holder.playerBowling.setText("Bowling: "+seriesTeamPlayers.getBowlingStyle());
        holder.playerType.setText("Player Type: "+seriesTeamPlayers.getPlayerType());
        if(seriesTeamPlayers.getImageURL() != null){
            Picasso.get().load(seriesTeamPlayers.getImageURL()).into(holder.playerImage, new Callback.EmptyCallback() {
                @Override public void onSuccess() {
                }

                @Override public void onError(Exception e) {
                    holder.playerImage.setImageResource(R.drawable.sample);
                }
            });
        }else{
            holder.playerImage.setImageResource(R.drawable.sample);
        }

    }

    @Override
    public int getItemCount() {
        return seriesTeamPlayerList.size();
    }


}
