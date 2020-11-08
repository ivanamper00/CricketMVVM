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
import com.billy.cricketmvvm.adapters.SeriesGamesAdapter;
import com.billy.cricketmvvm.models.games.MatchListModel;
import com.billy.cricketmvvm.viewmodels.SeriesGamesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeriesGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeriesGamesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SeriesGamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeriesGamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeriesGamesFragment newInstance(String param1, String param2) {
        SeriesGamesFragment fragment = new SeriesGamesFragment();
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
    public static SeriesGamesViewModel seriesGamesViewModel;
    private SeriesGamesAdapter adapter;
    private RelativeLayout relativeLayout;
    List<MatchListModel> seriesMatchList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private CardView noData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_series_games, container, false);

        recyclerView = view.findViewById(R.id.series_games_recycler);
        relativeLayout = view.findViewById(R.id.relative_loading);
        noData = view.findViewById(R.id.card_no_data);
        noData.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        seriesGamesViewModel = ViewModelProviders.of(this).get(SeriesGamesViewModel.class);
        seriesGamesViewModel.init(getContext());

        seriesGamesViewModel.getSeriesGames().observe(this, result -> {
            seriesMatchList.addAll(result);
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

    private void initRecyclerView(){
        adapter = new SeriesGamesAdapter(getContext(), seriesMatchList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}