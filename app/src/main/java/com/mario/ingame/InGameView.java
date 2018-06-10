package com.mario.ingame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.mario.load.LoadImage;

import java.util.ArrayList;

import game.sprite.Sprite;
import game.view.GameView;

public class InGameView extends GameView implements Runnable {

    private ArrayList<Level> levels = new ArrayList<Level>();

    private Level currentLevel;

    private Mario mario;

    private int mActivePointerId;

    private int points = 0;

    public Mario getMario() {
        return mario;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public InGameView(Context context)
    {
        super(context);
        this.setKeepScreenOn(true);
        this.setFocusableInTouchMode(true);

        for(int i=1; i<=1; i++)
        {
            levels.add(new Level(i));
        }

        currentLevel = levels.get(0);

        mario = new Mario(Methods.getnewsize(),Methods.getScreenHeight()-5  *Methods.getnewsize(),Methods.zoomImg(LoadImage.mario.get(0),Methods.getnewsize(),Methods.getnewsize()*2));
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        this.flag = true;
        this.t = new Thread(this);
        this.t.start();
    }



    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        this.flag = false;
    }


    public void Draw()
    {
        this.canvas = sh.lockCanvas();
        if(canvas != null)
        {
            for (Background bk:currentLevel.getBk()) {
                bk.Draw(canvas);
            }
            for(int i=0; i<currentLevel.getTile().size(); i++)
            {
                Tile drawtile = currentLevel.getTile().get(i);
                if(drawtile.x>=-Methods.getnewsize()&&drawtile.x<=Methods.getScreenWidth()){
                    drawtile.Draw(canvas);
                    drawtile.SwitchImage();
                    drawtile.Jump();
                }
            }
            paint.setColor(Color.WHITE);
            paint.setTextSize(Methods.getnewsize()/2);
            canvas.drawText("Score: "+String.valueOf(points), Methods.getScreenWidth()/2, Methods.getnewsize()/2, paint);
            for (Sprite s: Level.item) {
                if(s.hp>0){
                    Item it = (Item)s;
                    it.Jump();
                    it.Draw(canvas);
                    it.SwitchImage();
                    it.Move(this);
                }else{
                    Level.item.remove(s);
                }
            }

            for (FireBall fb: mario.getFireBall()) {
                if(fb.hp > 0)
                {
                    fb.Collision(this);
                    fb.Rotation();
                    fb.Draw(canvas,paint);
                    fb.Move(mario.shiftspeed);
                }
                else
                {
                    this.mario.getFireBall().remove(fb);
                }
            }

            mario.Draw(canvas);
            mario.Move(this);
            mario.SwitchImage();

            this.sh.unlockCanvasAndPost(canvas);
        }
    }

    public void ItemCollision()
    {
        mario.Collision(this);

        if(this.mario.hp > 0)
        {
            for (Sprite s: Level.item) {
                Item it = (Item) s;
                if(it.type == 2){
                    if(it.getJump() == 16)
                    {
                        if(it.MoreRectangle_CollisionWithSprite(mario, mario.getFrame()))
                        {
                            it.hp = 0;
                            if(mario.status< 2)
                            {
                                mario.status+=1;
                            }
                            points+=1000;
                        }
                    }
                }else if(it.type == 1){
                    if(it.getJump() == 16)
                    {
                        if(it.MoreRectangle_CollisionWithSprite(mario, mario.getFrame()))
                        {
                            it.hp = 0;
                            if(mario.status< 3)
                            {
                                mario.status+=1;
                            }
                            points+=1000;
                        }
                    }
                }else if(it.type == 3){
                    if(it.getJump() == 16)
                    {
                        if(it.MoreRectangle_CollisionWithSprite(mario, mario.getFrame()))
                        {
                            it.hp = 0;
                            points+=200;
                        }
                    }
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent e){

        mActivePointerId = e.getPointerId(0);
        int pointerCount = e.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            float x = e.getX(i);
            float y = e.getY(i);
            switch(e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    TouchDown(x);
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    TouchDown(x);
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    TouchUp(x);
                    break;

                case MotionEvent.ACTION_UP:
                    TouchUp(x);
                    break;

                case MotionEvent.ACTION_MOVE:
                    TouchDown(x);
                    break;
            }
        }

        return true;
    }

    public void TouchDown(float x){
        if(mario.hp < 0) return ;

        if(x<Methods.getScreenWidth()/6){
            mario.setState("Lmove");
        }else if(x>Methods.getScreenWidth()/6&&x<(Methods.getScreenWidth()/2)){
            mario.setState("Rmove");
        }else if(x>Methods.getScreenWidth()*5/6 && x<(Methods.getScreenWidth())){
            mario.JumpEvent();
        }else if(x>Methods.getScreenWidth()*1/2 && x<(Methods.getScreenWidth()*5/6)){
            mario.Fire();
        }
    }

    public void TouchUp(float x){
        if(mario.hp < 0) return ;

        if(x<Methods.getScreenWidth()/6){
            mario.setState("Lstop");
        }else if(x>Methods.getScreenWidth()/6&&x<(Methods.getScreenWidth()/2)){
            mario.setState("Rstop");
        }
    }

    @Override
    public void run()
    {
        while(flag)
        {
            mario.Collision(this);
            this.ItemCollision();
            Background.Stop(mario);
            mario.Jump();
            this.Draw();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
