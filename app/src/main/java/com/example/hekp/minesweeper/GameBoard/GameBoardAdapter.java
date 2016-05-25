package com.example.hekp.minesweeper.GameBoard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.hekp.minesweeper.R;
import com.example.hekp.minesweeper.Activities.GameScreenActivity;

import static com.example.hekp.minesweeper.MineSweeperConstants.CLOSED;
import static com.example.hekp.minesweeper.MineSweeperConstants.EMPTY;
import static com.example.hekp.minesweeper.MineSweeperConstants.FLAG;
import static com.example.hekp.minesweeper.MineSweeperConstants.MINE;

public class GameBoardAdapter extends ArrayAdapter {
    Context context;
    int _cols;
    GameBoardModel _model;


    public GameBoardAdapter(Context context, GameBoardModel model) {
        super(context, 0);
        this.context = context;
        _model = model;
        _cols = _model.getDynamicModel()[0].length;
    }

    @Override
    public int getCount() {
        int rows = _model.getDynamicModel().length;
        return rows * _cols;
    }

    public int getItemId(int row, int col) {
        return row * _cols + col;
    }

    public int getRow(int id) {
        return id / _cols;
    }

    public int getCol(int id) {
        return id % _cols;
    }

    public int[] getItemCoordinates(int id) {
        return new int[]{getRow(id), getCol(id)};
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void Render(ViewGroup Board, GameBoardModel Model) {
        int id;
        for (int i = 0; i < Board.getChildCount(); i++) {
            View v = Board.getChildAt(i);
            if (v instanceof Button) {
                Button bCell = (Button) v;
                id = bCell.getId();
                switch (Model.getDynamicModel()
                        [getRow(id)][getCol(id)]) {
                    case CLOSED:
                        bCell.setBackgroundResource(R.drawable.cell_background_closed);
                        break;
                    case EMPTY:
                        bCell.setBackgroundResource(R.drawable.cell_background_opened);
                        break;
                    case MINE:
                        bCell.setBackgroundResource(R.drawable.mine_element);
                        break;
                    case FLAG:
                        bCell.setBackgroundResource(R.drawable.flag_element);
                        break;
                    default:
                        bCell.setBackgroundResource(R.drawable.cell_background_opened);
                        bCell.setText(
                                String.valueOf(
                                        Model.getDynamicModel()
                                                [getRow(id)][getCol(id)]));
                        break;
                }

            }
        }
        ((GameScreenActivity)context).setTvFlagsCounter(_model.getFlag());
        Board.invalidate();
    }

    public void animateWin(ViewGroup Board){
        for (int i = 0; i < Board.getChildCount(); i++) {
            View v = Board.getChildAt(i);
            if (v instanceof Button) {
                Button bCell = (Button) v;
                Animation animation = new TranslateAnimation(bCell.getTranslationX(),
                        (float) (bCell.getTranslationX()+Math.random()*1000-500),
                        bCell.getTranslationY(), (float) (bCell.getTranslationY()+Math.random()*1000-500));
                animation.setDuration(2000);
                bCell.startAnimation(animation);
            }
        }
    }

    public void animateLose(ViewGroup Board){
        for (int i = 0; i < Board.getChildCount(); i++) {
            View v = Board.getChildAt(i);
            if (v instanceof Button) {
                Button bCell = (Button) v;
                Animation animation = new TranslateAnimation(bCell.getTranslationX(),
                        (float) (bCell.getTranslationX()-100),
                        bCell.getTranslationY(), (float) (bCell.getTranslationY()-100));
                animation.setDuration(2000);
                bCell.startAnimation(animation);
            }
        }
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View cell = convertView;

        if (cell == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            cell = inflater.inflate(R.layout.game_cell, parent, false);

            final Button gameCell = (Button) cell.findViewById(R.id.bGameCell);
            gameCell.setId(position);
            // Handling touch/click Event on GridView Item
            gameCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = gameCell.getId();
                    _model.OpenCell(getRow(id), getCol(id));
                    Render(parent, _model);
                }
            });

            gameCell.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int id = gameCell.getId();
                    _model.flipFlagState(getRow(id), getCol(id));
                    Render(parent, _model);
                    return true;
                }
            });

        }

        return cell;
    }
}
