package com.example.tim.settingsun;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.util.Observable;

/**
* This class is the controller for our game. It detects and handles user input.
* @authors Ward Theunisse, Tim van Dijk, Martijn Heitkönig en Luuk van Bitterswijk
*/
public class Controller extends Observable implements OnTouchListener{

    private float startx, starty;

    private Game game;

    private Handler handler;

    private SunView view;

    private Block touchedBlock;

    Controller (Game game, SunView view) {
        this.game = game;
        this.handler = new Handler();
        this.resetTouchLocation();
        this.view = view;
    }

    /**
     * Sets the x and y location to -1, which means that no presses are registered at this moment
     */
    private void resetTouchLocation() {
        this.startx = -1;
        this.starty = -1;
    }



    @Override
    public boolean onTouch (View v, MotionEvent event) {
        /**
         * Checks whether the user touches the screen. If it does, it finds out which block is pressed,
         * and in what direction the user swipes. If it makes for a valid move (a block is pressed,
         * no blocks in the way, new position not outside the playing field), it moves it.
         */

        if (startx == -1 || starty == -1) {
            startx = event.getX();
            starty = event.getY();

            boolean found = false;

            for (Block b: game.getPuzzle().getBlocks()) {
                float left = view.getBlockCoords(b).x;
                float right = view.getBlockSize(b).x + left;
                float top = view.getBlockCoords(b).y;
                float bottom = view.getBlockSize(b).y + top;

                if (startx >= left && startx <= right && starty >= top && starty <= bottom) {
                    touchedBlock= b;
                    found = true;
                }
            }
            if (!found) {
                touchedBlock = null;
                this.resetTouchLocation();
            }

        }

        if (touchedBlock != null) {

        }

        if(event.getAction() == MotionEvent.ACTION_UP && touchedBlock != null)
        {
            Direction d = Direction.between(event.getX()-startx, event.getY()-starty);
            //check if can move
            if (game.getPuzzle().canMove((int) touchedBlock.x,(int) touchedBlock.y,d)) {
                game.addPuzzle(new Puzzle(game.getPuzzle()));
                game.getPuzzle().moveBlock((int) touchedBlock.x,(int) touchedBlock.y,d);
                touchedBlock =null;
                view.postInvalidate();
                if (game.isGameWon()) {
                    setChanged();
                    notifyObservers();
                }
            }
            else {
                touchedBlock = null;
            }
            this.resetTouchLocation();
        }
        return true;
    }
}
