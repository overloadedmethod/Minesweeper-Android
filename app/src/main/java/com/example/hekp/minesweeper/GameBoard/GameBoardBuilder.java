package com.example.hekp.minesweeper.GameBoard;

import android.util.Log;

import com.example.hekp.minesweeper.MineSweeperConstants;
import com.example.hekp.minesweeper.Activities.GameScreenActivity;

public class GameBoardBuilder {
    private int _numberOfMines = 6;
    private int _numberOfRows = 6;
    private int _numberOfCols = 6;
    private int[][] _staticModel;
    private int[][] _dynamicModel;
    private GameScreenActivity _Manager = null;

    public GameBoardBuilder setManager(GameScreenActivity Manager) {
        _Manager = Manager;
        return this;
    }

    public GameBoardBuilder setNumberOfMines(int numberOfMines) {
        _numberOfMines = numberOfMines;
        return this;
    }

    public GameBoardBuilder setNumberOfRows(int numberOfRows) {
        _numberOfRows = numberOfRows;
        return this;
    }

    public GameBoardBuilder setStaticModel(int[][] staticModel){
        _staticModel = staticModel;
        return this;
    }

    public GameBoardBuilder setDynamicModel(int[][] dynamicModel){
        _dynamicModel = dynamicModel;
        return this;
    }

    public int[][] getStaticModel(){
        return _staticModel;
    }

    public int[][] getDynamicModel(){
        return _dynamicModel;
    }

    public GameBoardBuilder setNumberOfCols(int numberOfCols) {
        _numberOfCols = numberOfCols;
        return this;
    }

    public GameBoardModel Build() {
        int[][] gameboard = new int[_numberOfRows][_numberOfCols];
        initGameBoard(gameboard);
        populateGameboard(gameboard, _numberOfMines);
        return new GameBoardModel(gameboard, _numberOfMines, _Manager);
    }

    private static void initGameBoard(int[][] gameboard) {
        for (int i = 0; i < gameboard.length; i++)
            for (int j = 0; j < gameboard[i].length; j++)
                gameboard[i][j] = MineSweeperConstants.EMPTY;
    }

    private static int[][] populateGameboard(int[][] gameboard, int mines) {
        int row;
        int col;

        for (; mines > 0; --mines) {
            do {
                row = randInt(0, gameboard.length - 1);
                col = randInt(0, gameboard[row].length - 1);
            } while (gameboard[row][col] == MineSweeperConstants.MINE);

            gameboard[row][col] = MineSweeperConstants.MINE;
            updateNeighbours(gameboard, row, col);
        }
        return gameboard;
    }

    public void addMines(int numOfMines){
        if(_staticModel==null || _dynamicModel==null){
            Log.e("Data missing","No models were supplied");
            return;
        }

        int row;
        int col;

        //place a new mine
        for (; numOfMines > 0; --numOfMines) {
            do {
                row = randInt(0, _staticModel.length - 1);
                col = randInt(0, _staticModel[row].length - 1);
            } while (_staticModel[row][col] == MineSweeperConstants.MINE);

            _staticModel[row][col] = MineSweeperConstants.MINE;
            updateNeighbours(_staticModel, row, col);

            //close dynamic model area
            for (int i = row - 2; i < row + 2; i++)
                for (int j = col - 2; j < col + 2; j++) {
                    if (i < 0 ||
                            j < 0 ||
                            i >= _dynamicModel.length ||
                            j >= _dynamicModel[i].length ||
                            _dynamicModel[i][j] == MineSweeperConstants.FLAG)
                        continue;
                    _dynamicModel[i][j] = MineSweeperConstants.CLOSED;
                }
        }
    }

    private static void updateNeighbours(int[][] gameboard, int mineRow, int mineCol) {
        increaseCellValue(gameboard, mineRow, mineCol - 1);
        increaseCellValue(gameboard, mineRow - 1, mineCol);
        increaseCellValue(gameboard, mineRow - 1, mineCol - 1);
        increaseCellValue(gameboard, mineRow, mineCol + 1);
        increaseCellValue(gameboard, mineRow + 1, mineCol);
        increaseCellValue(gameboard, mineRow + 1, mineCol + 1);
        increaseCellValue(gameboard, mineRow - 1, mineCol + 1);
        increaseCellValue(gameboard, mineRow + 1, mineCol - 1);
    }

    private static void increaseCellValue(int[][] gameboard, int row, int col) {
        if (row < 0 ||
                col < 0 ||
                row >= gameboard.length ||
                col >= gameboard[row].length ||
                gameboard[row][col] == MineSweeperConstants.MINE)
            return;
        gameboard[row][col]++;
    }

    private static int randInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

}
