package com.example.hekp.minesweeper;

public class MineSweeperConstants {

    public final static int EMPTY = 0;
    public final static int MINE = -1;
    public final static int FLAG = -2;
    public final static int CLOSED = -3;


    public enum GameScreen {
        MENU_SCREEN, OPTIONS_SCREEN, GAME_SCREEN, END_SCREEN
    }

    public enum Difficulty{
        EASY,MEDIUM,HARD
    }
}
