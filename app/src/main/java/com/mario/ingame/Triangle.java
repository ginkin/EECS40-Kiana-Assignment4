package com.mario.ingame;

import com.mario.load.LoadImage;
import android.graphics.Bitmap;

public class Triangle extends Enemy implements Runnable{

    private int deadTime = 20;
    private float startX,startY;

    public Triangle(float x, float y, Bitmap image)
    {
        super(x, y, image);
        this.startX = x;
        this.startY = y;
        this.hp = 1;
        this.name = "Bloober";
        this.index = 0;
        this.changeTime = 4;
        this.state = "not flat";
        this.dir = 2;
        this.xSpeed = Methods.getnewsize()/6;
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



    public void ChangeImage()
    {
        this.changeTime --;

        if(this.state.equals("not flat"))
        {
            this.image = LoadImage.enemy.get(index);
            this.IsTimeOver();
            if(this.index == 2) this.index = 0;
        }
        else
        {
            this.image = LoadImage.enemy.get(2);
        }
    }

    @Override
    public void Dead()
    {
        this.xSpeed = 0;
        this.state = "flat";
        new Thread(this).start();
    }


    @Override
    public void run()
    {
        while(this.deadTime > 0)
        {
            this.deadTime --;

            if(this.deadTime <= 0)
            {
                this.hp = 0;
            }

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}
