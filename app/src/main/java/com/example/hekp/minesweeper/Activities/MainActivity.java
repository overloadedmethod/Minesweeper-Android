package com.example.hekp.minesweeper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hekp.minesweeper.MineSweeperConstants.Difficulty;
import com.example.hekp.minesweeper.R;

import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.EASY;
import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.values;

public class MainActivity extends AppCompatActivity {

    Button bNewGame;
    Button bScores;
    Button bOptions;
    Button bQuit;
    Difficulty _difficulty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        bNewGame = (Button)findViewById(R.id.bRestartGame);
        bScores = (Button)findViewById(R.id.bScores);
        bOptions = (Button) findViewById(R.id.bOptions);
        bQuit = (Button)findViewById(R.id.bExit);

        Bundle extras = getIntent().getExtras();
        _difficulty = (extras == null)?EASY:values()[extras.getInt("Difficulty")];


        bNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), GameScreenActivity.class);
                nextScreen.putExtra("Difficulty",_difficulty.ordinal());
                startActivity(nextScreen);
            }
        });

        bScores.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), ScoresActivity.class);
                startActivity(nextScreen);
            }
        });

        bOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), MenuOptionsActivity.class);
                nextScreen.putExtra("Difficulty",_difficulty.ordinal());
                startActivity(nextScreen);
            }
        });

        bQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

    }

}

