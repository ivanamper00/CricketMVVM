package com.billy.cricketmvvm.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.billy.cricketmvvm.models.games.MatchListModel;
import com.billy.cricketmvvm.repositories.Repositories;

import java.util.List;

public class SeriesUpcomingViewModel extends ViewModel {
    private MutableLiveData<List<MatchListModel>> seriesUpcoming;
    private Repositories repositories;


    public void init(Context context){
        if(seriesUpcoming != null){
            return;
        }
        repositories = Repositories.getInstance(context);
        seriesUpcoming = repositories.getFilteredUpcoming();
    }

    public LiveData<List<MatchListModel>> getSeriesUpcoming(){
        return seriesUpcoming;
    }
}
