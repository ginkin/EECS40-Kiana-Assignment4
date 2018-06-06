package com.mario.ingame;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.SurfaceHolder;

import com.mario.load.LoadImage;

import java.util.ArrayList;

import game.view.GameView;

public class MarioView extends GameView implements Runnable {

    private ArrayList<Level> levels = new ArrayList<Level>();

    private Level currentLevel;

    public MarioView(Context context)
    {
        super(context);
        this.setKeepScreenOn(true);
        this.setFocusableInTouchMode(true);

        for(int i=1; i<=1; i++)
        {
            levels.add(new Level(i));
        }

        currentLevel = levels.get(0);
    }




    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        this.flag = true;
        this.t = new Thread(this);
        this.t.start();
    }



    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        this.flag = false;
    }


    public void Draw()
    {
        this.canvas = sh.lockCanvas();
        if(canvas != null)
        {
            canvas.drawBitmap(LoadImage.map.get(0), 0, 0, null);
            for(int i=0; i<currentLevel.getTile().size(); i++)
            {
                Tile tile = currentLevel.getTile().get(i);
                tile.Draw(canvas);
                tile.SwitchImage();
            }
            this.sh.unlockCanvasAndPost(canvas);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return true;
    }


    @Override
    public void run()
    {
        while(flag)
        {
            this.Draw();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
