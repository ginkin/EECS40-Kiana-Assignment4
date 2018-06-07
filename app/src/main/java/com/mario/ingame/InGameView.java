package com.mario.ingame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.mario.load.LoadImage;

import java.util.ArrayList;

import game.view.GameView;

public class InGameView extends GameView implements Runnable {

    private ArrayList<Level> levels = new ArrayList<Level>();

    private Level currentLevel;

    private Mario mario;

    private int mActivePointerId;

    public Mario getMario() {
        return mario;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public InGameView(Context context)
    {
        super(context);
        this.setKeepScreenOn(true);
        this.setFocusableInTouchMode(true);

        for(int i=1; i<=1; i++)
        {
            levels.add(new Level(i));
        }

        currentLevel = levels.get(0);


        mario = new Mario(0,Methods.getScreenHeight()-3  *Methods.getnewsize(),Methods.zoomImg(LoadImage.mario.get(0),Methods.getnewsize(),Methods.getnewsize()));
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
                Tile drawtile = currentLevel.getTile().get(i);
                if(drawtile.x>=-Methods.getnewsize()&&drawtile.x<=Methods.getScreenWidth()){
                    drawtile.Draw(canvas);
                    drawtile.SwitchImage();
                }
            }

            mario.Draw(canvas);
            mario.Move(this);
            mario.SwitchImage();

            this.sh.unlockCanvasAndPost(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent e){

        float touchdown1x, touchdown1y, touchdown2x, touchdown2y;
        float touchup1x, touchup1y, touchup2x, touchup2y;
        mActivePointerId = e.getPointerId(0);
        int pointerIndex = e.findPointerIndex(mActivePointerId);

        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchdown1x = e.getX();
                touchdown1y = e.getY();
                TouchDown(touchdown1x);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                touchdown2x = e.getX(pointerIndex);
                touchdown2y = e.getY(pointerIndex);
                TouchDown(touchdown2x);
                break;

            case MotionEvent.ACTION_UP:
                touchup1x = e.getX();
                touchup1y = e.getY();
                TouchUp(touchup1x);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                touchup2x = e.getX();
                touchup2y = e.getY();
                TouchUp(touchup2x);
                break;
        }

        return true;
    }

    public void TouchDown(float x){
        if(mario.hp < 0) return ;

        if(x<Methods.getScreenWidth()/6){
            mario.setState("Lmove");
        }else if(x>Methods.getScreenWidth()/6&&x<(Methods.getScreenWidth()/2)){
            mario.setState("Rmove");
        }
    }

    public void TouchUp(float x){
        if(mario.hp < 0) return ;

        if(x<Methods.getScreenWidth()/6){
            mario.setState("Lstop");
        }else if(x>Methods.getScreenWidth()/6&&x<(Methods.getScreenWidth()/2)){
            mario.setState("Rstop");
        }
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
