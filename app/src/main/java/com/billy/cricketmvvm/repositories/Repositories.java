package com.billy.cricketmvvm.repositories;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;


import com.billy.cricketmvvm.models.games.GamesModel;
import com.billy.cricketmvvm.models.games.MatchListModel;
import com.billy.cricketmvvm.models.matches.MatchDetailsModel;
import com.billy.cricketmvvm.models.players.PlayerDetailsModel;
import com.billy.cricketmvvm.models.players.PlayersModel;
import com.billy.cricketmvvm.models.series.SeriesListModel;
import com.billy.cricketmvvm.models.series.SeriesModel;
import com.billy.cricketmvvm.models.standings.StandingsModel;
import com.billy.cricketmvvm.models.standings.TeamStandingModel;
import com.billy.cricketmvvm.models.teams.SeriesTeamModel;
import com.billy.cricketmvvm.models.teams.TeamsListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Repositories {

    private static Repositories instance;
    private CricketApi cricketApi;

    public static Repositories getInstance(){
        if(instance == null){
            instance = new Repositories();
        }
        return instance;
    }

    public Repositories() {
        this.cricketApi = RetrofitService.createService(CricketApi.class);
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

    public MutableLiveData<List<TeamsListModel>> getTeams(String series){
        MutableLiveData<List<TeamsListModel>> data = new MutableLiveData<>();
        cricketApi.getTeams(series).enqueue(new Callback<SeriesTeamModel>() {
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

    public  MutableLiveData<List<MatchListModel>> getFilteredUpcoming(String id) {
        MutableLiveData<List<MatchListModel>> data = new MutableLiveData<>();
        cricketApi.getSeriesGames(id).enqueue(new Callback<GamesModel>() {
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

    public MutableLiveData<List<PlayerDetailsModel>> getTeamPlayers(String teamId){
        MutableLiveData<List<PlayerDetailsModel>> data = new MutableLiveData<>();
        cricketApi.getTeamPlayers(teamId).enqueue(new Callback<PlayersModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<PlayersModel> call, retrofit2.Response<PlayersModel> response) {
                List<PlayerDetailsModel> teamPlayerList = response.body().getTeamPlayers().getPlayers();
                if(teamPlayerList.size() > 0){
                    data.setValue(teamPlayerList);
                }

            }
            @Override
            public void onFailure(Call<PlayersModel> call, Throwable t) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
        return data;
    }


    public MutableLiveData<List<TeamStandingModel>> getTeamStandings(String seriesId){
        MutableLiveData<List<TeamStandingModel>> data = new MutableLiveData<>();
        cricketApi.getStandings(seriesId).enqueue(new Callback<StandingsModel>() {
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


//    public  MutableLiveData<MatchDetailsModel.Match> getMatchDetails(String seriesId, String matchId) {
//        MutableLiveData<MatchDetailsModel.Match> data = new MutableLiveData<>();
//        cricketApi.getMatchDetails(seriesId, matchId).enqueue(new Callback<MatchDetailsModel>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<MatchDetailsModel> call, retrofit2.Response<MatchDetailsModel> response) {
//                MatchDetailsModel.Match list = response.body().getMatch();
//                data.setValue(list);
//            }
//            @Override
//            public void onFailure(Call<MatchDetailsModel> call, Throwable t) {
//                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
//            }
//        });
//        return data;
//    }
}
