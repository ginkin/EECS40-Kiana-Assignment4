package com.mario.ingame;

import game.sprite.Sprite;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Enemy extends Sprite
{
    //名字
    public String name;

    //切图下标
    public int index,moveindex;

    //切图间隔
    public int changeTime;

    //移动方向
    public int dir;

    //状态
    public String state;

    private boolean onLand;


    //下降数组
    private int move[] = {1,2,3,4,5,6,7,8};

    private Rect frame;


    public Enemy(float x, float y, Bitmap image)
    {
        super(x, y, image);
        this.frame = new Rect(0,11/16*Methods.getnewsize(),Methods.getnewsize(),2*Methods.getnewsize());

    }



    //绘图
    public void Draw(Canvas canvas)
    {
        if(dir == 1)
        {
            canvas.save();
            canvas.scale(-1, 1, this.x+this.image.getWidth()/2, this.y+this.image.getHeight()/2);
            canvas.drawBitmap(image, x, y, null);
            canvas.restore();
        }
        else
        {
            canvas.drawBitmap(image, x, y, null);
        }
    }



    public void Move(){};
    public void ChangeImage(){};
    public void ChangeState(){}
    public void ChangeStateTime(){}


    //判断是否到时间切图了
    public void IsTimeOver()
    {
        if(this.changeTime <= 0)
        {
            this.index++;
            this.changeTime = 4;
        }
    }

    public void Dead(){

    }

    //碰撞检测(类似马里奥的碰撞检测)
    public void Logic(InGameView mv)
    {
        if (this.name.equals("Piranha")) return;
        this.onLand = false;

        for(int i=0; i<mv.getCurrentLevel().getTile().size(); i++)
        {
            Tile t = mv.getCurrentLevel().getTile().get(i);

            if(t.x > this.x - this.image.getWidth()*2  && t.x < this.x + this.image.getWidth()*2 )
            {
                if(this.Rectangle_CollisionWithSprite(t))
                {
                    //是否处于障碍物上
                    if(this.x > t.x - this.image.getHeight() && this.x < t.x + this.image.getHeight() && this.y < t.y)
                    {
                        this.y = t.y - this.image.getHeight();
                        this.onLand = true;
                        this.moveindex = 0;
                    }

                    //右边是否碰到障碍物了
                    if(this.y > t.y - this.image.getHeight() && this.x < t.x )
                    {
                        this.dir = 1;
                    }

                    //左边是否碰到障碍物了
                    else if(this.y > t.y - this.image.getHeight() && this.x > t.x )
                    {
                        this.dir = 2;
                    }
                }
            }
        }

        //下降
        if(!onLand)
        {
            this.y+=this.move[moveindex];
            if(moveindex < move.length -1) moveindex++;
        }
    }


















}
