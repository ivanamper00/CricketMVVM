package com.billy.cricketmvvm.view.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.billy.cricketmvvm.R;
import com.billy.cricketmvvm.adapters.SeriesAdapter;
import com.billy.cricketmvvm.adapters.TeamsAdapter;
import com.billy.cricketmvvm.models.series.SeriesListModel;
import com.billy.cricketmvvm.models.teams.TeamsListModel;
import com.billy.cricketmvvm.repositories.Presets;
import com.billy.cricketmvvm.viewmodels.SeriesViewModel;
import com.billy.cricketmvvm.viewmodels.TeamsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeamsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamsFragment newInstance(String param1, String param2) {
        TeamsFragment fragment = new TeamsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TeamsViewModel teamsViewModel;
    private List<TeamsListModel> teamsList = new ArrayList<>();
    private TeamsAdapter adapter;
    private CardView noData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        recyclerView = view.findViewById(R.id.series_teams_recycler);
        relativeLayout = view.findViewById(R.id.relative_loading);
        noData = view.findViewById(R.id.card_no_data);
        teamsViewModel = ViewModelProviders.of(this).get(TeamsViewModel.class);
        teamsViewModel.init(getContext());
        relativeLayout.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);

        teamsViewModel.getSeries().observe(this, result -> {
            teamsList.addAll(result);
            adapter.notifyDataSetChanged();
            if(adapter != null){
                relativeLayout.setVisibility(View.GONE);
            }
            if(result.size() == 0){
                noData.setVisibility(View.VISIBLE);
            }
        });


        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        adapter = new TeamsAdapter(getContext(), teamsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}