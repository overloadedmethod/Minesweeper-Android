package com.example.hekp.minesweeper.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hekp.minesweeper.R;
import com.example.hekp.minesweeper.Interfaces.IExitableRestartable;

public class LoseScreenFragment extends Fragment implements View.OnClickListener {

    private IExitableRestartable mListener;
    Button bMainMenu;
    Button bRestart;

    public LoseScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lose_screen, container, false);
        bMainMenu = (Button) view.findViewById(R.id.bMainMenu);
        bRestart = (Button) view.findViewById(R.id.bRestartGame);

        bMainMenu.setOnClickListener(this);
        bRestart.setOnClickListener(this);

        if (getActivity() instanceof IExitableRestartable) {
            mListener = (IExitableRestartable) getActivity();
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bMainMenu:
                mListener.setMainMenu();
                Log.d("click event","main clicked");
                break;
            case R.id.bRestartGame:
                mListener.setRestart();
                Log.d("click event","main clicked");
                break;
        }
    }
}
