package com.billy.cricketmvvm.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.billy.cricketmvvm.R;
import com.billy.cricketmvvm.adapters.SeriesAdapter;
import com.billy.cricketmvvm.adapters.TeamPlayersAdapter;
import com.billy.cricketmvvm.adapters.TeamsAdapter;
import com.billy.cricketmvvm.models.players.PlayerDetailsModel;
import com.billy.cricketmvvm.models.series.SeriesListModel;
import com.billy.cricketmvvm.models.standings.TeamStandingModel;
import com.billy.cricketmvvm.models.teams.TeamsListModel;
import com.billy.cricketmvvm.repositories.Presets;
import com.billy.cricketmvvm.viewmodels.SeriesViewModel;
import com.billy.cricketmvvm.viewmodels.TeamViewModel;
import com.billy.cricketmvvm.viewmodels.TeamsViewModel;
import com.github.islamkhsh.CardSliderViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TeamDetailsActivity extends AppCompatActivity {

    private CardSliderViewPager cardSliderViewPager;
    private TeamViewModel teamViewModel;
    private List<PlayerDetailsModel> seriesTeamPlayers = new ArrayList<>();
    private List<TeamStandingModel> team = new ArrayList<>();

    TextView teamName;
    TextView teamWon;
    TextView teamLost;
    TextView teamPoints;
    ImageView teamLogo;
    TeamPlayersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        cardSliderViewPager = findViewById(R.id.team_player_card_slider);
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        teamViewModel.init(this);
        declaration();
        getData();

    }

    public void declaration(){
        teamName = findViewById(R.id.team_name);
        teamWon = findViewById(R.id.team_won);
        teamLost = findViewById(R.id.team_lost);
        teamPoints = findViewById(R.id.team_points);
        teamLogo = findViewById(R.id.team_logo);

    }


    @SuppressLint("SetTextI18n")
    public void getData(){
        teamViewModel.getTeamPlayers().observe(this, result -> {
            seriesTeamPlayers.addAll(result);
            adapter.notifyDataSetChanged();
        });
        teamViewModel.getTeam().observe(this, result -> {
            team.addAll(result);
//            Log.d("asdasd", team.get(0).getGroupName() + " " + Presets.teamId);

            for(int i =0; i < team.size();i++){
                if(String.valueOf(team.get(i).getId()).equalsIgnoreCase(Presets.teamId)){
//                    Log.d("asdasd", team.get(i).getName());
                    teamName.setText(String.valueOf(team.get(i).getName()));
                    teamWon.setText("Won: "+String.valueOf(team.get(i).getWon()));
                    teamLost.setText("Lost: "+String.valueOf(team.get(i).getLost()));
                    teamPoints.setText("Points: "+String.valueOf(team.get(i).getLost()));
                    Picasso.get().load(team.get(i).getLogoUrl()).into(teamLogo);
                    break;
                }
            }

        });
        adapter = new TeamPlayersAdapter(this,seriesTeamPlayers);
        cardSliderViewPager.setAdapter(adapter);
    }

}