package com.billy.cricketmvvm.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.billy.cricketmvvm.models.games.MatchListModel;
import com.billy.cricketmvvm.repositories.Presets;
import com.billy.cricketmvvm.repositories.Repositories;

import java.util.List;

public class SeriesGamesViewModel extends ViewModel {
    private MutableLiveData<List<MatchListModel>> seriesGames;
    private Repositories repositories;

    public void init(Context context){
        if(seriesGames != null){
            return;
        }
        repositories = Repositories.getInstance(context);
        seriesGames = repositories.getSeriesGames(Presets.seriesId);
    }

    public LiveData<List<MatchListModel>> getSeriesGames(){
        return seriesGames;
    }
}
