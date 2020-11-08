package com.billy.cricketmvvm.repositories;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;


import com.billy.cricketmvvm.models.games.GamesModel;
import com.billy.cricketmvvm.models.games.MatchListModel;
import com.billy.cricketmvvm.models.players.PlayerDetailsModel;
import com.billy.cricketmvvm.models.players.PlayersModel;
import com.billy.cricketmvvm.models.series.SeriesListModel;
import com.billy.cricketmvvm.models.series.SeriesModel;
import com.billy.cricketmvvm.models.standings.StandingsModel;
import com.billy.cricketmvvm.models.standings.TeamStandingModel;
import com.billy.cricketmvvm.models.teams.SeriesTeamModel;
import com.billy.cricketmvvm.models.teams.TeamsListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Repositories {

    private static Repositories instance;
    private CricketApi cricketApi;
    Context context;

    public static Repositories getInstance(Context context){
        if(instance == null){
            instance = new Repositories(context);
        }
        return instance;
    }

    public Repositories(Context context) {
        this.cricketApi = RetrofitService.createService(CricketApi.class);
        this.context = context;
    }

    public MutableLiveData<List<SeriesListModel>> getSeries(){
        MutableLiveData<List<SeriesListModel>> data = new MutableLiveData<>();
        cricketApi.getSeries().enqueue(new Callback<SeriesModel>() {
            @Override
            public void onResponse(Call<SeriesModel> call, retrofit2.Response<SeriesModel> response) {
                if(response.body() != null){
                    data.setValue(response.body().getSeriesList().getSeries());
                }

            }
            @Override
            public void onFailure(Call<SeriesModel> call, Throwable t) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<List<TeamsListModel>> getTeams(){
        MutableLiveData<List<TeamsListModel>> data = new MutableLiveData<>();
        cricketApi.getTeams(Presets.seriesId).enqueue(new Callback<SeriesTeamModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<SeriesTeamModel> call, retrofit2.Response<SeriesTeamModel> response) {
                SeriesTeamModel standings = response.body();
                if(standings.getSeriesTeams() != null){
                    data.setValue(standings.getSeriesTeams().getTeams());
                }
            }
            @Override
            public void onFailure(Call<SeriesTeamModel> call, Throwable t) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<List<MatchListModel>> getSeriesGames(String id){
        MutableLiveData<List<MatchListModel>> data = new MutableLiveData<>();
        cricketApi.getSeriesGames(id).enqueue(new Callback<GamesModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<GamesModel> call, retrofit2.Response<GamesModel> response) {
                List<MatchListModel> list = response.body().getMatchList().getMatches();
                List<MatchListModel> filteredList = new ArrayList<>();
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).getStatus().equalsIgnoreCase("LIVE")){
                        filteredList.add(list.get(i));
                    }
                }
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).getStatus().equalsIgnoreCase("COMPLETED")){
                        filteredList.add(list.get(i));
                    }
                }
                if(filteredList.size() > 0){
                    data.setValue(filteredList);
                }

            }
            @Override
            public void onFailure(Call<GamesModel> call, Throwable t) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
        return data;
    }

    public  MutableLiveData<List<MatchListModel>> getFilteredUpcoming() {
        MutableLiveData<List<MatchListModel>> data = new MutableLiveData<>();
        cricketApi.getSeriesGames(Presets.seriesId).enqueue(new Callback<GamesModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<GamesModel> call, retrofit2.Response<GamesModel> response) {
                List<MatchListModel> list = response.body().getMatchList().getMatches();
                List<MatchListModel> filteredList = new ArrayList<>();
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).getStatus().equalsIgnoreCase("UPCOMING") && !list.get(i).getHomeTeam().getName().equalsIgnoreCase("unknown")){
                        filteredList.add(list.get(i));
                    }
                }
                if(filteredList.size() > 0){
                    data.setValue(filteredList);
                }
            }
            @Override
            public void onFailure(Call<GamesModel> call, Throwable t) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<List<PlayerDetailsModel>> getTeamPlayers(){
        MutableLiveData<List<PlayerDetailsModel>> data = new MutableLiveData<>();
        cricketApi.getTeamPlayers(Presets.teamId).enqueue(new Callback<PlayersModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<PlayersModel> call, retrofit2.Response<PlayersModel> response) {

                    try{
                        List<PlayerDetailsModel> teamPlayerList = response.body().getTeamPlayers().getPlayers();
                        data.setValue(teamPlayerList);
                    }catch (Exception e){
                        Toast.makeText(context, "No Player Data Available", Toast.LENGTH_SHORT).show();
                    }

            }
            @Override
            public void onFailure(Call<PlayersModel> call, Throwable t) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
        return data;
    }


    public MutableLiveData<List<TeamStandingModel>> getTeamStandings(){
        MutableLiveData<List<TeamStandingModel>> data = new MutableLiveData<>();
        cricketApi.getStandings(Presets.seriesId).enqueue(new Callback<StandingsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<StandingsModel> call, retrofit2.Response<StandingsModel> response) {
                List<TeamStandingModel> teamStanding = response.body().getTeams();
                if(teamStanding.size() > 0) {
                    data.setValue(teamStanding);
                }
            }
            @Override
            public void onFailure(Call<StandingsModel> call, Throwable t) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
        return data;
    }

}
