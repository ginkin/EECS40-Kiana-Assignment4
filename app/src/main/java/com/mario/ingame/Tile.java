package com.mario.ingame;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.mario.load.LoadImage;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

import game.sprite.Sprite;

public class Tile extends Sprite {

    public int getType() {
        return type;
    }

    private int type;
    private int i,i2;
    private int switchtime = 4;
    private static int movecount;

    private int jumptime;

    public void setCollision(int collision) {
        this.collision = collision;
    }

    public int getCollision() {
        return collision;
    }

    private int collision;

    public void setJumpTime(int jumpTime) {
        this.jumptime = jumpTime;
    }
    int movestep[] = {-6,-4,-2,0,2,4,6};

    public static int getMovecount() {
        return movecount;
    }

    public Tile(float x, float y, Bitmap image, int type)
    {
        super(x, y, image);
        this.type = type;
        switch(type)
        {
            case 21:
                this.i= 8;
                this.collision = 1;
                break;

            case 17:
                this.i = 31;
                break;

            case 37:
                this.collision = 1;
                break;
        }

    }

//    public static void Move(InGameView gv)
//    {
//        if(gv.getMario().hp == 0) return;
//
//        if(gv.getMario().getState().equals(""))
//        {
//            for(int i=0; i<gv.getCurrentLevel().getTile().size(); i++)
//            {
//                Tile t = gv.getCurrentLevel().getTile().get(i);
//                t.x -= gv.getMario().getxSpeed();
//            }
//            movecount+= gv.getMario().getxSpeed();
//        }
//        else if(gv.getMario().getState().equals("����"))
//        {
//            for(int i=0; i<gv.getCurrentLevel().getTile().size(); i++)
//            {
//                Tile t = gv.getCurrentLevel().getTile().get(i);
//                t.x += gv.getMario().getxSpeed();
//            }
//            movecount-= gv.getMario().getxSpeed();
//        }
//    }

    public void Jump()
    {
        if(this.jumptime > 0)
        {
            this.y += movestep[i2];
            if(i2 < movestep.length - 1)
            {
                i2++;
            }
            else
            {
                this.jumptime = 0;
                i2 = 0;
            }
        }
    }


    public static void Shift(InGameView gv, String state, Integer speed){
        if(state.equals("Rmove")){
            for (Tile t:gv.getCurrentLevel().getTile()) {
                t.x -= speed;
            }
            for (Enemy e:gv.getCurrentLevel().getEnemy()) {
                e.x -= speed;
            }
            for(Item i:gv.getCurrentLevel().getItem()) {
                i.x = i.x - speed;
            }

            movecount += speed;
        }
        else if(state.equals("Lmove")){
            for (Tile t:gv.getCurrentLevel().getTile()) {
                t.x += speed;
            }
            for (Enemy e:gv.getCurrentLevel().getEnemy()) {
                e.x += speed;
            }
            for(Item i:gv.getCurrentLevel().getItem()) {
                i.x = i.x + speed;
            }
            movecount -= speed;
        }
    }

    public void SwitchImage()
    {
        switchtime -- ;
        switch(type)
        {
            case 21: // "?" Block
                if(this.collision > 0){
                    image = Methods.zoomImg(LoadImage.tile.get(i),Methods.getnewsize(),Methods.getnewsize());
                    TimeToSwitch();
                    if(i > 11 ) {i = 8;}
                }
                else{
                    image = Methods.zoomImg(LoadImage.tile.get(7),Methods.getnewsize(),Methods.getnewsize());
                }
                break;
            case 17:
                image = Methods.zoomImg(LoadImage.tile.get(i),Methods.getnewsize(),Methods.getnewsize());
                TimeToSwitch();
                if(i > 11 ) {i = 8;}
                break;
            case 37: // Coin Block
                if(this.collision > 0){
                    image = Methods.zoomImg(LoadImage.tile.get(6),Methods.getnewsize(),Methods.getnewsize());
                }else{
                    image = Methods.zoomImg(LoadImage.tile.get(7),Methods.getnewsize(),Methods.getnewsize());
                }
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
