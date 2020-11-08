package com.billy.cricketmvvm.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.billy.cricketmvvm.models.games.MatchListModel;
import com.billy.cricketmvvm.models.players.PlayerDetailsModel;
import com.billy.cricketmvvm.models.standings.TeamStandingModel;
import com.billy.cricketmvvm.models.teams.TeamsListModel;
import com.billy.cricketmvvm.repositories.Presets;
import com.billy.cricketmvvm.repositories.Repositories;

import java.util.List;

public class TeamViewModel extends ViewModel {
    private MutableLiveData<List<PlayerDetailsModel>> seriesTeamPlayers;
    private MutableLiveData<List<TeamStandingModel>> seriesTeams;
    private Repositories repositories;


    public void init(Context context){
        if(seriesTeamPlayers != null){
            return;
        }
        repositories = Repositories.getInstance(context);
        seriesTeamPlayers = repositories.getTeamPlayers();
        seriesTeams = repositories.getTeamStandings();
    }

    public LiveData<List<PlayerDetailsModel>> getTeamPlayers(){
        return seriesTeamPlayers;
    }

    public LiveData<List<TeamStandingModel>> getTeam(){
        return seriesTeams;
    }
}
