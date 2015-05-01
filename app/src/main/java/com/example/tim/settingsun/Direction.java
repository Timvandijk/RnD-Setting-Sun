package com.example.tim.settingsun;

/**
 * This class is an enumeration of directions.
 * @author Maurice Knoop (re-used his code from  his "Snake" game)
 */
public enum Direction {

    UP(0,-1), RIGHT(1,0),DOWN(0,1),LEFT(-1,0);

    final int dx,dy;

    private Direction(int dx, int dy)
    {
        this.dx=dx;
        this.dy=dy;
    }

    public static Direction between(float f, float g)
    {
        if(Math.abs(f) > Math.abs(g))
            if(f > 0)
                return RIGHT;
            else
                return LEFT;
        else
        if(g > 0)
            return DOWN;
        else
            return UP;
    }

}