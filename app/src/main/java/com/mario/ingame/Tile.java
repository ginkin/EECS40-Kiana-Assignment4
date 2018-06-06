package com.mario.ingame;

import android.graphics.Bitmap;

import com.mario.load.LoadImage;

import java.util.Random;

import game.sprite.Sprite;

public class Tile extends Sprite {

    private int type;
    private int i;
    private int switchtime = 4;

    public Tile(float x, float y, Bitmap image, int type)
    {
        super(x, y, image);
        this.type = type;

        switch(type)
        {
            case 21:
                this.i= 8;
                break;

            case 17:
                this.i = 31;
                break;

            case 37:
                this.i = new Random().nextInt(6) + 1;
                break;
        }

    }

    public void SwitchImage()
    {
        switchtime -- ;
        switch(type)
        {
            case 21:
                image = LoadImage.tile.get(i);
                TimeToSwitch();
                if(i > 11 ) {i = 8;}
                break;
            case 17:
                image = LoadImage.tile.get(i);
                TimeToSwitch();
                if(i > 11 ) {i = 8;}
                break;
        }
    }

    public void TimeToSwitch()
    {
        while(switchtime <= 0)
        {
            i++;
            switchtime = 4;
        }
    }

}
