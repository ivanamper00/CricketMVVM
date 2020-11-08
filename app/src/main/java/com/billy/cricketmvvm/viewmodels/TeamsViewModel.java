package com.billy.cricketmvvm.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.billy.cricketmvvm.models.series.SeriesListModel;
import com.billy.cricketmvvm.models.teams.TeamsListModel;
import com.billy.cricketmvvm.repositories.Repositories;

import java.util.List;

public class TeamsViewModel extends ViewModel {
    private MutableLiveData<List<TeamsListModel>> teamsList;
    private Repositories repositories;


    public void init(Context context){
        if(teamsList != null){
            return;
        }
        repositories = Repositories.getInstance(context);
        teamsList = repositories.getTeams();
    }

    public LiveData<List<TeamsListModel>> getSeries(){
        return teamsList;
    }
}
