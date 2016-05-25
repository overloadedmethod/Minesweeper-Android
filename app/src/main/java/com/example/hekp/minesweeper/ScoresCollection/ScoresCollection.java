package com.example.hekp.minesweeper.ScoresCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by HEKP on 17-Jan-16.
 */
public class ScoresCollection {
    private TreeSet<ScoresUser> mScores;

    public ScoresCollection(Comparator<ScoresUser> comp){
        mScores = new TreeSet<ScoresUser>(comp);
    }

    public void setScores(ScoresUser[] users){
        mScores.addAll(Arrays.asList(users));
    }

    public ScoresCollection(){
        this(new Comparator<ScoresUser>() {
            @Override
            public int compare(ScoresUser lhs, ScoresUser rhs) {
                if(lhs.getScore() < rhs.getScore())
                    return 1;
                if(lhs.getScore() == rhs.getScore())
                    return 0;
                else
                    return -1;
            }
        });
    }

    public boolean add(ScoresUser user){
        if(user.getScore()>getMinimalScore()){
            if(mScores.size()>=10){
                mScores.remove(mScores.last());
                return mScores.add(user);
            }
            else
                return mScores.add(user);
        }
        else
            return false;
    }

    public TreeSet<ScoresUser> getScores(){
        return mScores;
    }


    //TODO:optimize
    public double getLat(int position){
        return ((ScoresUser)mScores.toArray()[position]).getLat();
    }

    //TODO:optimize
    public double getLng(int position){
        return ((ScoresUser)mScores.toArray()[position]).getLng();
    }

    public int getMinimalScore(){
        if(!mScores.isEmpty())
            return mScores.last().getScore();
        else
            return -1;
    }

    public String[] extractScoreNames(){
        ArrayList<String> temp = new ArrayList<>();
       Iterator iterator = mScores.iterator();
        while (iterator.hasNext())
            temp.add(iterator.next().toString());
        String[] result = new String[temp.size()];
        temp.toArray(result);
        return result;
    }

}
