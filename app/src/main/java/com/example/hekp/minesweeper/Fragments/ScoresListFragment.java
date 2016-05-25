package com.example.hekp.minesweeper.Fragments;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hekp.minesweeper.R;
import com.example.hekp.minesweeper.Interfaces.IOnListItemClickListener;
import com.example.hekp.minesweeper.ScoresCollection.ScoresCollection;
import com.example.hekp.minesweeper.ScoresCollection.ScoresSerialize;

public class ScoresListFragment extends ListFragment {

    private IOnListItemClickListener _activity;
    ScoresCollection _scoresCollection;
    ScoresSerialize _scoresSerializer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScores();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_scores_list, container, false);
        //TODO:replace this dirty hack by normal adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
               R.layout.fragment_text, _scoresCollection.extractScoreNames());
        setListAdapter(adapter);

        try {
            _activity = (IOnListItemClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        _activity.onItemClick(_scoresCollection.getLat(position),_scoresCollection.getLng(position));
    }

    public void initScores(){
        _scoresSerializer = new ScoresSerialize(getActivity());
        _scoresCollection = _scoresSerializer.deserializeScoresCollection();
        if(_scoresCollection == null)
            _scoresCollection = new ScoresCollection();
    }

}

