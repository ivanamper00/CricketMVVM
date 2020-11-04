package com.billy.cricketmvvm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.cricketmvvm.R;
import com.billy.cricketmvvm.models.teams.TeamsListModel;
import com.billy.cricketmvvm.repositories.Presets;
import com.billy.cricketmvvm.view.activities.TeamDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder> {
    Context context;
    List<TeamsListModel> teamsList;
    TeamsListModel team;
    public class TeamsViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        ImageView teamLogo;
        public TeamsViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.teams_name);
            teamLogo = itemView.findViewById(R.id.teams_logo);
        }
    }

    public TeamsAdapter(Context context, List<TeamsListModel> teamsList){
        this.context = context;
        this.teamsList = teamsList;
    }

    @NonNull
    @Override
    public TeamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_list,parent,false);
        return new TeamsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsViewHolder holder, int position) {
        team = teamsList.get(position);
        holder.teamName.setText(team.getName());
        Picasso.get().load(team.getLogoUrl()).into(holder.teamLogo);

        holder.itemView.setOnClickListener((View v)->{
            Presets.teamId = String.valueOf(teamsList.get(position).getId());
            Intent intent = new Intent(context, TeamDetailsActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }



}
