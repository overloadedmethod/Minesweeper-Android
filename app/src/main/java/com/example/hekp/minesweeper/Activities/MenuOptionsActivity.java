package com.example.hekp.minesweeper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hekp.minesweeper.MineSweeperConstants;
import com.example.hekp.minesweeper.R;

import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.EASY;
import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.HARD;
import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.MEDIUM;
import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.values;

public class MenuOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    Button bEasy;
    Button bMedium;
    Button bHard;
    MineSweeperConstants.Difficulty _level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_options);

        bEasy = (Button) findViewById(R.id.bEasy);
        bMedium = (Button) findViewById(R.id.bMedium);
        bHard = (Button) findViewById(R.id.bHard);

        bEasy.setOnClickListener(this);
        bMedium.setOnClickListener(this);
        bHard.setOnClickListener(this);

        int difficultyValue = getIntent().getExtras().getInt("Difficulty");
        _level = values()[difficultyValue];
        setDifficulty(_level);
    }

    void setDifficulty(MineSweeperConstants.Difficulty level) {
        initPressedStatus();
        _level = level;
        switch (level) {
            case EASY:
                bEasy.setPressed(true);
                break;
            case MEDIUM:
                bMedium.setPressed(true);
                break;
            case HARD:
                bHard.setPressed(true);
        }
    }

    void initPressedStatus() {
        bEasy.setPressed(false);
        bMedium.setPressed(false);
        bHard.setPressed(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bEasy:
                setDifficulty(EASY);
                break;
            case R.id.bMedium:
                setDifficulty(MEDIUM);
                break;
            case R.id.bHard:
                setDifficulty(HARD);
        }
        onBackPressed();
    }

    @Override
    public void onBackPressed(){
        Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
        nextScreen.putExtra("Difficulty",_level.ordinal());
        startActivity(nextScreen);
    }
}
