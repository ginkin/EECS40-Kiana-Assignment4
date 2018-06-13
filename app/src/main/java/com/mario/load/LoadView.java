package com.mario.load;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.SurfaceHolder;

import com.mario.ingame.Methods;

import game.view.GameView;

public class LoadView extends GameView implements Runnable{

    public LoadView(Context context){
        super(context);
        this.setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.flag = true;
        this.t = new Thread(this);
        this.t.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.flag = false;
    }

    public void Draw(){
        this.canvas = sh.lockCanvas();
        if (canvas != null){
            canvas.drawBitmap(Methods.zoomImg(LoadImage.ui.get(0),Methods.getScreenWidth(),Methods.getScreenHeight()),0,0,null);

            this.sh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public void run() {
        while (flag){
            this.Draw();
            try{
                Thread.sleep(25);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}