package com.mario.ingame;

import com.mario.load.LoadImage;
import android.graphics.Bitmap;

public class Turtle extends Enemy
{
    private int changeStateTime = 150;
    private float startX,startY;
    public Turtle(float x, float y, Bitmap image)
    {
        super(x, y, image);
        this.startX = x;
        this.startY = y;
        this.name = "Turtle";
        this.state = "move";
        this.hp = 1;
        this.index = 7;
        this.xSpeed = Methods.getnewsize()/6;
        this.changeTime = 4;
        this.dir = 2;
    }

    //切图
    public void ChangeImage()
    {
        this.changeTime --;

        if(this.state.equals("move"))
        {
            this.image = LoadImage.enemy.get(index);
            this.IsTimeOver();
            if(this.index == 9) this.index = 7;
        }
        else
        {
            this.image = LoadImage.enemy.get(9);
        }

    }


    @Override
    public void ChangeState()
    {
        this.xSpeed = 0;
        this.state = "stop";
        this.name = "shell";
    }


    @Override
    public void ChangeStateTime()
    {
        if(this.state.equals("move") || this.xSpeed >= 8) return;

        if(this.changeStateTime > 0)
        {
            this.changeStateTime --;
        }
        else
        {
            this.state = "move";
            this.changeStateTime = 150;
            this.xSpeed = 2;
        }
    }


    public void Move()
    {
        if(dir == 1)
        {
            this.x-=this.xSpeed;
        }
        else
        {
            this.x+=this.xSpeed;
        }
    }
}
