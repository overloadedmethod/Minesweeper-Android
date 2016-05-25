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
import com.example.hekp.minesweeper.Interfaces.IRecordable;

public class WinScreenFragment extends Fragment  implements View.OnClickListener {
    private Button bMainMenu;
    private Button bRestart;
    private Button bAddRecord;
    private IExitableRestartable exitableRestartable;
    private IRecordable recordable;
    private boolean _isRecord;

    private static final String IS_ADD_RECORD = "isRecord";


    public WinScreenFragment() {
        // Required empty public constructor
    }

    public static WinScreenFragment newInstance(boolean showAddRecord) {
        WinScreenFragment fragment = new WinScreenFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_ADD_RECORD, showAddRecord);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            _isRecord = getArguments().getBoolean(IS_ADD_RECORD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_win_screen, container, false);
        bMainMenu = (Button) view.findViewById(R.id.bMainMenu);
        bRestart = (Button) view.findViewById(R.id.bRestartGame);
        bAddRecord = (Button) view.findViewById(R.id.bAddRecord);

        if  (getActivity() instanceof IExitableRestartable) {
            exitableRestartable = (IExitableRestartable) getActivity();
        }
        if(getActivity() instanceof IRecordable){
            recordable = (IRecordable)getActivity();
        }
        else
            throw new RuntimeException(getActivity().toString()
                    + " must implement IRecordableExitableRestartable");

        bMainMenu.setOnClickListener(this);
        bRestart.setOnClickListener(this);
        bAddRecord.setOnClickListener(this);

        if(!_isRecord)
            bAddRecord.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.d("context",context.getClass().getCanonicalName());
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        exitableRestartable = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bMainMenu:
                Log.d("click event","main clicked");
                exitableRestartable.setMainMenu();
                break;
            case R.id.bRestartGame:
                Log.d("click event","restart clicked");
                exitableRestartable.setRestart();
                break;
            case R.id.bAddRecord:
                Log.d("click event","addRecord clicked");
                recordable.setAddRecordScreen();
                break;
        }
    }
}
