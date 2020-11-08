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
import com.billy.cricketmvvm.models.series.SeriesListModel;
import com.billy.cricketmvvm.viewmodels.SeriesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SeriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeriesFragment newInstance(String param1, String param2) {
        SeriesFragment fragment = new SeriesFragment();
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
    private SeriesViewModel seriesViewModel;
    private SeriesAdapter adapter;
    private RelativeLayout relativeLayout;
    List<SeriesListModel> seriesListModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_series, container, false);

        recyclerView = view.findViewById(R.id.series_recycler);
        relativeLayout = view.findViewById(R.id.relative_loading);

        seriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);
        seriesViewModel.init(getContext());
        relativeLayout.setVisibility(View.VISIBLE);

        seriesViewModel.getSeries().observe(this, result -> {

            seriesListModels.addAll(result);
            adapter.notifyDataSetChanged();
            if(adapter != null){
                relativeLayout.setVisibility(View.GONE);
            }
        });


        initRecyclerView();

        return view;
    }

    private void initRecyclerView(){
        adapter = new SeriesAdapter(getContext(), seriesListModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}