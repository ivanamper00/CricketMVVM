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
import com.billy.cricketmvvm.viewmodels.SeriesUpcomingViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
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

    private SeriesUpcomingViewModel seriesUpcomingViewModel;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private SeriesGamesAdapter adapter;
    List<MatchListModel> seriesUpcomingMatchList = new ArrayList<>();
    private CardView noData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        seriesUpcomingViewModel = ViewModelProviders.of(this).get(SeriesUpcomingViewModel.class);

        recyclerView = view.findViewById(R.id.series_upcoming_recycler);
        relativeLayout = view.findViewById(R.id.relative_loading);
        noData = view.findViewById(R.id.card_no_data);
        relativeLayout.setVisibility(View.VISIBLE);
        seriesUpcomingViewModel.init(getContext());
        noData.setVisibility(View.GONE);

        seriesUpcomingViewModel.getSeriesUpcoming().observe(this, result -> {
            seriesUpcomingMatchList.addAll(result);
            if(seriesUpcomingMatchList.size() == 0){
                noData.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        });

        initRecyclerView();
        return view;
    }
    private void initRecyclerView(){
        adapter = new SeriesGamesAdapter(getContext(), seriesUpcomingMatchList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
}