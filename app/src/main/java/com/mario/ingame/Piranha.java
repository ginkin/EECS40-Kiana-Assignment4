package com.mario.ingame;

import com.mario.load.LoadImage;

import android.graphics.Bitmap;

public class Piranha extends Enemy
{
    //上下移动数组
    private int updownmove[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
            ,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
            ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    //updownmove数组累计
    private int count;
    private float startX,startY;


    public Piranha(float x, float y, Bitmap image)
    {
        super(x, y, image);
        this.startX = x;
        this.startY = y;
        this.dir = 1;
        this.index = 10;
        this.ySpeed = Methods.getnewsize()/12;
        this.hp = 1;
        this.name = "Piranha";
    }

    public void Move()
    {
        this.y+=this.updownmove[count]*ySpeed;
        count++;
        if(count > this.updownmove.length - 1)
        {
            count = 0;
        }
    }


    public void ChangeImage()
    {
        this.changeTime --;

        this.image = LoadImage.enemy.get(index);
        this.IsTimeOver();
        if(this.index == 12) this.index = 10;
    }

}
