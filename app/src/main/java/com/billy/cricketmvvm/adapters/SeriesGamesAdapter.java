package com.billy.cricketmvvm.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import com.billy.cricketmvvm.R;
import com.billy.cricketmvvm.models.games.MatchListModel;
import com.billy.cricketmvvm.models.matches.MatchDetailsModel;
import com.billy.cricketmvvm.repositories.CricketApi;
import com.billy.cricketmvvm.repositories.Presets;
import com.billy.cricketmvvm.repositories.RetrofitService;
import com.billy.cricketmvvm.view.fragments.SeriesGamesFragment;
import com.billy.cricketmvvm.viewmodels.SeriesGamesViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.billy.cricketmvvm.repositories.Presets.nullable;

public class SeriesGamesAdapter extends RecyclerView.Adapter<SeriesGamesAdapter.SeriesGamesViewHolder> {
    private Context context;
    private Dialog dialog;
    private List<MatchListModel> matchList;
    private MatchListModel match;
    private CricketApi cricketApi;
    public class SeriesGamesViewHolder extends RecyclerView.ViewHolder {
        private TextView series;
        private TextView status;
        private TextView homeTeam;
        private TextView homeScore;
        private ImageView homeLogo;
        private TextView awayTeam;
        private TextView awayScore;
        private TextView gameDate;
        ImageView awayLogo;
        public SeriesGamesViewHolder(@NonNull View itemView) {
            super(itemView);
            series = itemView.findViewById(R.id.games_series);
            status = itemView.findViewById(R.id.games_status);
            homeTeam = itemView.findViewById(R.id.games_home_name);
            homeScore = itemView.findViewById(R.id.games_home_score);
            homeLogo = itemView.findViewById(R.id.games_home_logo);
            awayTeam = itemView.findViewById(R.id.games_away_name);
            awayScore = itemView.findViewById(R.id.games_away_score);
            awayLogo = itemView.findViewById(R.id.games_away_logo);
            gameDate= itemView.findViewById(R.id.games_date);

        }
    }

    public SeriesGamesAdapter(Context context,List<MatchListModel> matchList){
        this.context = context;
        this.matchList = matchList;
        this.cricketApi = RetrofitService.createService(CricketApi.class);
    }

    @NonNull
    @Override
    public SeriesGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_list,parent,false);
        return new SeriesGamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesGamesViewHolder holder, int position) {
        match = matchList.get(position);

        holder.series.setText(match.getSeries().getName());
        holder.status.setText(match.getStatus());
        holder.gameDate.setText(match.getStartDateTime().substring(0,10));

        holder.homeTeam.setText(match.getHomeTeam().getName());
        Picasso.get().load(match.getHomeTeam().getLogoUrl()).into(holder.homeLogo);

        holder.awayTeam.setText(match.getAwayTeam().getName());
        Picasso.get().load(match.getAwayTeam().getLogoUrl()).into(holder.awayLogo);


        if(!match.getStatus().equalsIgnoreCase("UPCOMING")){
            holder.homeScore.setText(nullable(match.getScores().getHomeScore()));
            holder.awayScore.setText(nullable(match.getScores().getAwayScore()));
        }


        holder.itemView.setOnClickListener((View v) ->{
            if(!matchList.get(position).getStatus().equalsIgnoreCase("UPCOMING")){
//                Toast.makeText(context, ), Toast.LENGTH_SHORT).show();
                Presets.matchId = String.valueOf(matchList.get(position).getId());
                teamsDetails();
            }
        });

    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public void teamsDetails(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.match_details);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView homeName = dialog.findViewById(R.id.match_details_home_name);
        TextView homeScore = dialog.findViewById(R.id.match_details_home_score);
        TextView homeOvers = dialog.findViewById(R.id.match_details_home_overs);
        TextView awayName = dialog.findViewById(R.id.match_details_away_name);
        TextView awayScore = dialog.findViewById(R.id.match_details_away_score);
        TextView awayOvers = dialog.findViewById(R.id.match_details_away_overs);
        TextView batting = dialog.findViewById(R.id.match_details_batting);
        TextView summary = dialog.findViewById(R.id.match_details_summary);
        ImageView homeLogo = dialog.findViewById(R.id.match_details_home_logo);
        ImageView awayLogo = dialog.findViewById(R.id.match_details_away_logo);
        ProgressBar progressBar = dialog.findViewById(R.id.progress_loading);
        LinearLayout linearLayout = dialog.findViewById(R.id.match_details_linear);
        LinearLayout homeDetails = dialog.findViewById(R.id.home_team_details);
        LinearLayout awayDetails = dialog.findViewById(R.id.away_team_details);
        homeDetails.setVisibility(View.INVISIBLE);
        awayDetails.setVisibility(View.INVISIBLE);
        cricketApi.getMatchDetails(Presets.seriesId,Presets.matchId).enqueue(new Callback<MatchDetailsModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<MatchDetailsModel> call, retrofit2.Response<MatchDetailsModel> response) {
                MatchDetailsModel.Matches list = response.body().getMatches();

                homeName.setText(list.getHomeTeam().getName());
                homeScore.setText("Home Score: \t\t\t" + list.getScores().getHomeScore());
                homeOvers.setText("Home Overs: \t\t\t" + list.getScores().getHomeOvers());
                awayName.setText(list.getAwayTeam().getName());
                awayScore.setText("Away Score: \t\t\t" + list.getScores().getAwayScore());
                awayOvers.setText("Away Overs: \t\t\t" + list.getScores().getAwayOvers());
                if(list.getHomeTeam().getBatting()){
                    batting.setText("Batting Team: " + list.getHomeTeam().getName());
                }else{
                    batting.setText("Batting Team: " + list.getAwayTeam().getName());
                }
                summary.setText(list.getMatchSummaryText());
                Picasso.get().load(list.getHomeTeam().getLogoUrl()).into(homeLogo);
                Picasso.get().load(list.getAwayTeam().getLogoUrl()).into(awayLogo);
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                homeDetails.setVisibility(View.VISIBLE);
                awayDetails.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<MatchDetailsModel> call, Throwable t) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });

    }
}

