package com.mario.ingame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.mario.load.LoadImage;

import game.sprite.Sprite;

public class FireBall extends Sprite{
    private int angle;

    private boolean landed;


    private String state = "";

    public FireBall(float x, float y, Bitmap image,int xSpeed) {
        super(x, y, image);
        this.xSpeed = xSpeed*3/2;
        this.hp = 1;
    }

    public void Draw(Canvas canvas, Paint paint)
    {
        canvas.save();
        canvas.rotate(angle, x+image.getWidth()/2, y+image.getHeight()/2);
        canvas.drawBitmap(image, x, y, paint);
        canvas.restore();
    }

    public void Rotation()
    {
        angle+=30;
        if(angle==360){ angle=0; }
    }

    public void Move(int shiftspeed)
    {
        x += xSpeed;
        x += shiftspeed;

        if(x + image.getWidth() < 0 || x > Methods.getScreenWidth() || y > Methods.getScreenHeight())
        {
            hp = 0;
        }
    }

    public void Collision(InGameView gv){
        if(gv.getMario().getFireBall().size()<=0) return;
        landed = false;
        for (Tile t: gv.getCurrentLevel().getTile()) {
            if(t.x > x - image.getWidth()*2 && t.x < x + image.getWidth()*2){
                if(Rectangle_CollisionWithSprite(t)){

                    if(this.y <= t.y)
                    {
                        this.landed = true;
                        this.ySpeed=0;
                        this.state="";
                    }
                    else
                    {
                        this.hp=0;
//                        Level.blast.add(new Blast(this.x,this.y,LoadResource.blast.get(0)));
                    }
                }
            }
        }
    }
}
