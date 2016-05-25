package com.example.hekp.minesweeper.GameBoard;

import com.example.hekp.minesweeper.MineSweeperConstants;
import com.example.hekp.minesweeper.Activities.GameScreenActivity;

public class GameBoardModel {

    private int _mines;
    private int _minesCount;
    private int _flags;

    private int[][] _staticModel;
    private int[][] _dynamicModel;
    private GameScreenActivity _Manager;

    public GameBoardModel(GameBoardModel model, GameScreenActivity Manager) {
        this(model._staticModel, model._mines, Manager);
    }

    public GameBoardModel(int[][] staticModel, int mines) {
        this(staticModel, mines, null);
    }

    public GameBoardModel(int[][] staticModel, int mines, GameScreenActivity Manager) {
        _staticModel = staticModel;
        _mines = mines;
        _minesCount = mines;
        _flags = 0;
        _Manager = Manager;

        _dynamicModel = new int[staticModel.length][staticModel[0].length];
        for (int i = 0; i < _dynamicModel.length; i++)
            for (int j = 0; j < _dynamicModel[i].length; j++)
                _dynamicModel[i][j] = MineSweeperConstants.CLOSED;
    }

    public void SetFlag(int row, int col) {
        final int cell = _dynamicModel[row][col];
        if (cell == MineSweeperConstants.CLOSED) {
            _dynamicModel[row][col] = MineSweeperConstants.FLAG;
            _flags++;
            if (_staticModel[row][col] == MineSweeperConstants.MINE)
                _minesCount--;
            if (_minesCount == 0 && _flags == _mines)
                notifyWin();
        }
    }

    public void UnsetFlag(int row, int col) {
        final int cell = _dynamicModel[row][col];
        if (cell == MineSweeperConstants.FLAG)
            _dynamicModel[row][col] = MineSweeperConstants.CLOSED;
        _flags--;
        if (_staticModel[row][col] == MineSweeperConstants.MINE)
            _minesCount--;
    }

    public void flipFlagState(int row, int col) {
        final int cell = _dynamicModel[row][col];
        if (cell == MineSweeperConstants.FLAG)
            UnsetFlag(row, col);
        else if (cell == MineSweeperConstants.CLOSED)
            SetFlag(row, col);
    }

    public void OpenCell(int row, int col) {
        final int cell = _dynamicModel[row][col];
        if (cell == MineSweeperConstants.CLOSED) {
            int inStaticModelCell = _staticModel[row][col];
            //opened a mine?Good for ya , try another game
            if (inStaticModelCell == MineSweeperConstants.MINE) {
                _dynamicModel = _staticModel;
                notifyLose();
            } else
                fillEmptySpace(row, col);
        }
    }

    private void fillEmptySpace(int row, int col) {
        //boundaries check
        if (row < 0 ||
                col < 0 ||
                row >= _staticModel.length ||
                col >= _staticModel[row].length ||
                _staticModel[row][col] == MineSweeperConstants.MINE ||
                _dynamicModel[row][col] != MineSweeperConstants.CLOSED)
            return;
        //probably you hit a number
        if (_staticModel[row][col] != MineSweeperConstants.EMPTY) {
            _dynamicModel[row][col] = _staticModel[row][col];
            return;
        }
        //hiya!Empty space
        _dynamicModel[row][col] = _staticModel[row][col];
        fillEmptySpace(row - 1, col);
        fillEmptySpace(row + 1, col);
        fillEmptySpace(row, col - 1);
        fillEmptySpace(row, col + 1);
    }

    public int getMinesCount(){return _minesCount;}

    public void setMinesCount(int minesCount){_minesCount = minesCount;}

    public int getMines(){return _mines;}

    public void setMines(int mines){_mines = mines;}

    public int[][] getDynamicModel() {
        return _dynamicModel;
    }

    public int[][] getStaticModel() {return _staticModel;}

    public void setDynamicModel(int[][] dynamicModel){
        _dynamicModel = dynamicModel;
    }

    public void setStaticModel(int[][] staticModel){
        _staticModel = staticModel;
    }

    public int getItem(int row, int col) {
        return _dynamicModel[row][col];
    }

    public int getFlag() {
        return _flags;
    }

    public int getCols() {
        return _staticModel[0].length;
    }

    public int getRows() {
        return _staticModel.length;
    }

    public void setManager(GameScreenActivity Manager) {
        this._Manager = Manager;
    }

    private void notifyWin() {
        _Manager.setWinScreen();
    }

    private void notifyLose() {
        _Manager.setLoseScreen();
    }
}
