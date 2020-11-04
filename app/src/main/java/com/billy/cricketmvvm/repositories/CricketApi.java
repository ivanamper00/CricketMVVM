package com.billy.cricketmvvm.repositories;


import com.billy.cricketmvvm.models.games.GamesModel;
import com.billy.cricketmvvm.models.matches.MatchDetailsModel;
import com.billy.cricketmvvm.models.players.PlayersModel;
import com.billy.cricketmvvm.models.series.SeriesModel;
import com.billy.cricketmvvm.models.standings.StandingsModel;
import com.billy.cricketmvvm.models.teams.SeriesTeamModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CricketApi {

    String BASE_URL =  "https://rapidapi.p.rapidapi.com/";
//    String BASE_URL = "https://dev132-cricket-live-scores-v1.p.rapidapi.com/";


    @Headers({"x-rapidapi-host: dev132-cricket-live-scores-v1.p.rapidapi.com",
            "x-rapidapi-key: 07e55202eemshd454005e3a79774p103cccjsn4b32f05d3a2f",
            "useQueryString: true"})
    @GET("matchseries.php")
    Call<GamesModel> getSeriesGames(@Query("seriesid") String id);

    @Headers({"x-rapidapi-host: dev132-cricket-live-scores-v1.p.rapidapi.com",
            "x-rapidapi-key: 07e55202eemshd454005e3a79774p103cccjsn4b32f05d3a2f",
            "useQueryString: true"})
    @GET("series.php")
    Call<SeriesModel> getSeries();

    @Headers({"x-rapidapi-host: dev132-cricket-live-scores-v1.p.rapidapi.com",
            "x-rapidapi-key: 07e55202eemshd454005e3a79774p103cccjsn4b32f05d3a2f",
            "useQueryString: true"})
    @GET("seriesteams.php")
    Call<SeriesTeamModel> getTeams(@Query("seriesid") String id);

    @Headers({"x-rapidapi-host: dev132-cricket-live-scores-v1.p.rapidapi.com",
            "x-rapidapi-key: 07e55202eemshd454005e3a79774p103cccjsn4b32f05d3a2f",
            "useQueryString: true"})
    @GET("seriesstandings.php")
    Call<StandingsModel> getStandings(@Query("seriesid") String id);

    @Headers({"x-rapidapi-host: dev132-cricket-live-scores-v1.p.rapidapi.com",
            "x-rapidapi-key: 07e55202eemshd454005e3a79774p103cccjsn4b32f05d3a2f",
            "useQueryString: true"})
    @GET("teamplayers.php")
    Call<PlayersModel> getTeamPlayers(@Query("teamid") String id);

    @Headers({"x-rapidapi-host: dev132-cricket-live-scores-v1.p.rapidapi.com",
            "x-rapidapi-key: 07e55202eemshd454005e3a79774p103cccjsn4b32f05d3a2f",
            "useQueryString: true"})
    @GET("match.php")
    Call<MatchDetailsModel> getMatchDetails(@Query("seriesid") String seriesid, @Query("matchid") String matchid);
}
