package com.mario.ingame;

import android.graphics.Bitmap;

import game.sprite.Sprite;

public class Background extends Sprite{
    public static int speed;

    public Background(float x, float y, Bitmap image) {
        super(x, y, image);
    }
    public void Shift(String direction)
    {
        if(direction.equals("R"))
        {
            speed = Methods.getnewsize()/4;
        }
        else
        {
            speed = -Methods.getnewsize()/4;
        }

        this.x+=speed;
    }
    public static void Stop(Mario mario)
    {
        if(mario.getState().equals("Rmove")|| mario.getState().equals("Lmove") || mario.x != Methods.getScreenWidth()/2 || !mario.canleft || !mario.canright || mario.hp == 0) {speed = 0;}
    }
}
