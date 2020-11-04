package com.billy.cricketmvvm.models.players;

import com.google.gson.annotations.SerializedName;

public class PlayersModel {
    @SerializedName("teamPlayers")
    private PlayerListModel teamPlayers;

    public PlayersModel(PlayerListModel teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public PlayerListModel getTeamPlayers() {
        return teamPlayers;
    }
}
