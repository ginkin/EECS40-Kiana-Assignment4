package com.mario.ingame;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.mario.load.LoadImage;

import java.util.Random;

import game.sprite.Sprite;

public class Tile extends Sprite {

    private int type;
    private int i;
    private int switchtime = 4;
    private static int movecount;

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
                break;

            case 17:
                this.i = 31;
                break;

            case 37:
                this.i = new Random().nextInt(6) + 1;
                break;
        }

    }
//
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

    public static void Shift(InGameView gv, String state, Integer speed){
        if(state.equals("Rmove")){
            for (Tile t:gv.getCurrentLevel().getTile()) {
                t.x -= speed;
            }
            movecount += speed;
        }
        else if(state.equals("Lmove")){
            for (Tile t:gv.getCurrentLevel().getTile()) {
                t.x += speed;
            }
            movecount -= speed;
        }
    }

    public void SwitchImage()
    {
        switchtime -- ;
        switch(type)
        {
            case 21:
                image = Methods.zoomImg(LoadImage.tile.get(i),Methods.getnewsize(),Methods.getnewsize());
                TimeToSwitch();
                if(i > 11 ) {i = 8;}
                break;
            case 17:
                image = Methods.zoomImg(LoadImage.tile.get(i),Methods.getnewsize(),Methods.getnewsize());
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
