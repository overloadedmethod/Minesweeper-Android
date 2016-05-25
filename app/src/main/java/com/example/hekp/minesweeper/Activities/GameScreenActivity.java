package com.example.hekp.minesweeper.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.hekp.minesweeper.GameBoard.GameBoardAdapter;
import com.example.hekp.minesweeper.GameBoard.GameBoardBuilder;
import com.example.hekp.minesweeper.GameBoard.GameBoardModel;
import com.example.hekp.minesweeper.R;
import com.example.hekp.minesweeper.SensorAuxiliary;

import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty;
import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.EASY;
import static com.example.hekp.minesweeper.MineSweeperConstants.Difficulty.values;

public class GameScreenActivity extends AppCompatActivity implements View.OnClickListener {

    GameBoardAdapter adapter;
    GridView gvGameBoard;
    TextView tvTimeCounter;
    TextView tvFlagsCounter;
    Button bPause;
    Difficulty _difficulty;
    GameBoardModel _model;
    Thread timeCounter;
    boolean canUpdate;
    SensorAuxiliary _sensor;
    GameBoardBuilder _builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        gvGameBoard = (GridView) findViewById(R.id.gvGameBoard);
        tvTimeCounter = (TextView) findViewById(R.id.tvTimerCounterValue);
        tvFlagsCounter = (TextView) findViewById(R.id.tvFlagCounterValue);
        bPause = (Button) findViewById(R.id.bGameScreenPause);

        bPause.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        _difficulty = (extras == null) ? EASY : values()[extras.getInt("Difficulty")];


        _model = buildLevel(_difficulty, this);
        adapter = new GameBoardAdapter(this, _model);
        gvGameBoard.setNumColumns(_model.getCols());
        gvGameBoard.setColumnWidth(_model.getRows());
        gvGameBoard.setVerticalSpacing(0);
        gvGameBoard.setHorizontalSpacing(0);
        gvGameBoard.setAdapter(adapter);

        timeCounter = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    incTimeCount();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        timeCounter.start();

        _sensor = new SensorAuxiliary(this,4,4,4);
        _sensor.onCreate();
    }

    private GameBoardModel buildLevel(Difficulty difficulty, GameScreenActivity manager) {
         _builder = new GameBoardBuilder();
        switch (difficulty) {
            case EASY:
                _builder.setNumberOfCols(6)
                        .setNumberOfRows(6)
                        .setNumberOfMines(2)
                        .setManager(manager);
                break;
            case MEDIUM:
                _builder.setNumberOfCols(6)
                        .setNumberOfRows(6)
                        .setNumberOfMines(5)
                        .setManager(manager);
                break;
            case HARD:
                _builder.setNumberOfCols(6)
                        .setNumberOfRows(6)
                        .setNumberOfMines(9)
                        .setManager(manager);
        }
        return _builder.Build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        synchronized (this) {
            canUpdate = false;
        }
        _sensor.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVisibility();
        bPause.setVisibility(View.VISIBLE);
        _sensor.onResume();
    }

    public void setResume() {
        initVisibility();
        synchronized (this) {
            canUpdate = true;
            this.notifyAll();
        }
    }

    void resetTimeCount() {
        setTimeCount(0);
    }

    void setTimeCount(int count) {
        if(canUpdate)
            tvTimeCounter.setText(String.valueOf(count));
    }

    int getTimeCount() {
        return Integer.parseInt((String) tvTimeCounter.getText());
    }

    void incTimeCount() {
        setTimeCount(getTimeCount() + 1);
    }

    public void setTvFlagsCounter(int count) {
        tvFlagsCounter.setText(String.valueOf(count));
    }

    private void initVisibility() {
        bPause.setVisibility(View.GONE);
    }

    public void setWinScreen() {

        adapter.animateWin(gvGameBoard);
        int score = (int)((_model.getMines()/Double.parseDouble(tvTimeCounter.getText().toString()))*10000);
        Intent winScreen = new Intent(getApplicationContext(), EndGameScreenActivity.class);
        winScreen.putExtra("Difficulty", _difficulty.ordinal());
        winScreen.putExtra("IsAWinScreen",true);
        winScreen.putExtra("Score",score);
        startActivity(winScreen);
    }

    public void setLoseScreen() {

        adapter.animateLose(gvGameBoard);
        Intent loseScreen = new Intent(getApplicationContext(), EndGameScreenActivity.class);
        loseScreen.putExtra("Difficulty", _difficulty.ordinal());
        loseScreen.putExtra("IsAWinScreen",false);
        startActivity(loseScreen);
    }

    @Override
    public void onClick(View v) {
        setResume();
    }

    @Override
    public void onBackPressed(){
        Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
        nextScreen.putExtra("Difficulty",_difficulty.ordinal());
        startActivity(nextScreen);
    }

    public void notifyTresholdPass(){

        int numOfMines = 1;

        _builder.setStaticModel(_model.getStaticModel());
        _builder.setDynamicModel(_model.getDynamicModel());
        _builder.addMines(numOfMines);
        _model.setStaticModel(_builder.getStaticModel());
        _model.setDynamicModel(_builder.getDynamicModel());
        _model.setMines(_model.getMines()+numOfMines);
        _model.setMinesCount(_model.getMinesCount()+numOfMines);
        if(_model.getMines()>=_model.getRows()*_model.getCols()){
            this.setLoseScreen();
            return;
        }
        adapter.Render(gvGameBoard,_model);
    }

}


//////////////////////////////////


