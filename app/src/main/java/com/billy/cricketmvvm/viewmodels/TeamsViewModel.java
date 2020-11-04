package com.billy.cricketmvvm.viewmodels;

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


    public void init(String id){
        if(teamsList != null){
            return;
        }
        repositories = Repositories.getInstance();
        teamsList = repositories.getTeams(id);
    }

    public LiveData<List<TeamsListModel>> getSeries(){
        return teamsList;
    }
}
