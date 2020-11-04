package com.billy.cricketmvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.billy.cricketmvvm.models.series.SeriesListModel;
import com.billy.cricketmvvm.repositories.Repositories;

import java.util.List;

public class SeriesViewModel extends ViewModel {

    private MutableLiveData<List<SeriesListModel>> seriesList;
    private Repositories repositories;


    public void init(){
        if(seriesList != null){
            return;
        }
        repositories = Repositories.getInstance();
        seriesList = repositories.getSeries();
    }

    public LiveData<List<SeriesListModel>> getSeries(){
        return seriesList;
    }

    public void saveSeriesId(){

    }
}
