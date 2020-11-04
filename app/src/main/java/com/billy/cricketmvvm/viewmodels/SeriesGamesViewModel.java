package com.billy.cricketmvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.billy.cricketmvvm.models.games.MatchListModel;
import com.billy.cricketmvvm.models.matches.MatchDetailsModel;
import com.billy.cricketmvvm.repositories.Presets;
import com.billy.cricketmvvm.repositories.Repositories;

import java.util.ArrayList;
import java.util.List;

public class SeriesGamesViewModel extends ViewModel {
    private MutableLiveData<List<MatchListModel>> seriesGames;
    private Repositories repositories;


    public void init(){
        if(seriesGames != null){
            return;
        }
        repositories = Repositories.getInstance();
        seriesGames = repositories.getSeriesGames(Presets.seriesId);
//        matchDetails = repositories.getMatchDetails(Presets.seriesId,Presets.matchId);
    }

    public LiveData<List<MatchListModel>> getSeriesGames(){
        return seriesGames;
    }
//    public LiveData<MatchDetailsModel.Match> getMatchDetails(){
//        return matchDetails;
//    }
}
