package com.mario.ingame;

import android.graphics.Bitmap;

import com.mario.load.LoadImage;

import game.sprite.Sprite;

public class Item extends Tile {

    private int jump;
    private int type;
    private int i,i2;
    private Tile t;

    public Item(float x, float y, Bitmap image, int type, Tile t) {
        super(x, y, image, type);
        this.t  = t;
    }

    @Override
    public void Jump(){
        if(jump < 8){
            y-=Methods.getnewsize()/8;
            jump++;
        }
        this. x = t.x;
    }
    @Override
    public void SwitchImage(){
        switch(type){
            case 1:
                image = LoadImage.food.get(i);
                i++;
                while(i==3) i =1;
        }
    }
}
