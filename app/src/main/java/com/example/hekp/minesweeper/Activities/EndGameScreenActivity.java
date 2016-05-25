package com.example.hekp.minesweeper.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.hekp.minesweeper.MineSweeperConstants;
import com.example.hekp.minesweeper.R;
import com.example.hekp.minesweeper.Fragments.AddRecordFragment;
import com.example.hekp.minesweeper.Fragments.LoseScreenFragment;
import com.example.hekp.minesweeper.Fragments.WinScreenFragment;
import com.example.hekp.minesweeper.Interfaces.IExitableRestartable;
import com.example.hekp.minesweeper.Interfaces.IRecordable;
import com.example.hekp.minesweeper.ScoresCollection.ScoresSerialize;
import com.example.hekp.minesweeper.ScoresCollection.ScoresCollection;
import com.example.hekp.minesweeper.ScoresCollection.ScoresUser;

import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.EASY;
import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.values;

public class EndGameScreenActivity extends FragmentActivity implements IExitableRestartable,IRecordable {

    MineSweeperConstants.Difficulty _difficulty;
    View view;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Fragment fragment;
    int _score;
    ScoresCollection _scoresCollection;
    ScoresSerialize _scoresSerializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end_screen);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            _difficulty = EASY;
            onBackPressed();
        } else {
            _difficulty = values()[extras.getInt("Difficulty")];
            if (extras.getBoolean("IsAWinScreen")) {
                _score = extras.getInt("Score");

                initScores();

                setWinFragmentScreen(_scoresCollection.getMinimalScore()<_score);
            }
            else{
                setLoseFragmentScreen();
            }
        }
    }

    private void newTransaction(){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    @Override
    public void onBackPressed(){
        Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
        nextScreen.putExtra("Difficulty",_difficulty.ordinal());
        startActivity(nextScreen);
    }

    @Override
    public void setRestart() {
        Intent restartGame = new Intent(getApplicationContext(), GameScreenActivity.class);
        restartGame.putExtra("Difficulty", _difficulty.ordinal());
        startActivity(restartGame);
    }

    @Override
    public void setMainMenu() {
        Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
        mainScreen.putExtra("Difficulty", _difficulty.ordinal());
        startActivity(mainScreen);
    }

    @Override
    public void addRecord(String name, double lat, double lng) {
        _scoresCollection.add(new ScoresUser(name,lat,lng,_score));
        _scoresSerializer.serializeScoresCollection(_scoresCollection);
        setWinFragmentScreen(false);
    }

    public void setAddRecordScreen() {
        fragment  = new AddRecordFragment();
        setMyFragment(fragment);
    }

    public void initScores(){
        _scoresSerializer = new ScoresSerialize(this);
        _scoresCollection = _scoresSerializer.deserializeScoresCollection();
        if(_scoresCollection == null)
            _scoresCollection = new ScoresCollection();
    }

    public void setWinFragmentScreen(boolean isRecord){
        fragment = WinScreenFragment.newInstance(isRecord);
        setMyFragment(fragment);
    }

    public void setLoseFragmentScreen(){
        fragment = new LoseScreenFragment();
        setMyFragment(fragment);
    }

    private void setMyFragment(Fragment new_fragment){
        newTransaction();
        fragmentTransaction.replace(R.id.frameLayoutFragmentContainer, new_fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
