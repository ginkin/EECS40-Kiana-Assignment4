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
import com.mario.load.LoadView;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.ArrayList;

import game.sprite.Sprite;
import game.view.GameView;

public class InGameView extends GameView implements Runnable {

    ArrayList<Level> levels = new ArrayList<Level>();

    private Level currentLevel;

    boolean win,lose;
    private Mario mario;

    public static final int GAME_ING = 0;
    public static final int GAME_WIN = 1;
    public static final int GAME_OVER = 2;
    public static final int GAME_PANNEL = 3;
    private int gameState = GAME_PANNEL;

    private int mActivePointerId;

    private int points = 0;

    public Mario getMario() {
        return mario;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public InGameView(Context context)
    {
        super(context);
        this.setKeepScreenOn(true);
        this.setFocusableInTouchMode(true);

        for(int i=1; i<=2; i++)
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
        if (this.getCurrentLevel().goToNextLevelTime != 0) {this.getCurrentLevel().GotoNextLevel(this);return;}
        this.canvas = sh.lockCanvas();

        if(canvas != null)
        {
            if(this.mario.y > Methods.getScreenHeight()*1.5)
            {
                mario.reborn();
            }

            for (Background bk:currentLevel.getBk()) {
                bk.Draw(canvas);
            }
            for(int i=0; i<this.currentLevel.getEnemy().size(); i++)
            {
                Enemy e = this.currentLevel.getEnemy().get(i);
                if(e.hp > 0)
                {
                    e.Logic(this);
                    e.Draw(canvas);
                    e.Move();
                    e.ChangeImage();
                }
                else
                {
                    this.currentLevel.getEnemy().remove(i);
                }
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
            paint.setTextSize(Methods.getnewsize());
            paint.setColor(Color.RED);
            paint.setStrokeWidth(32);
            canvas.drawText("Score: "+String.valueOf(points), Methods.getScreenWidth()/12*10- Methods.getnewsize(),Methods.getnewsize(), paint);
            canvas.drawText("Lives:"+String.valueOf(mario.getLives()), Methods.getScreenWidth()/12- Methods.getnewsize(),Methods.getnewsize(),paint);
            canvas.drawText("Level:"+String.valueOf(currentLevel.getLevel()),Methods.getScreenWidth()/2- Methods.getnewsize(), Methods.getnewsize(),paint);
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
            if (mario.x>Methods.getScreenWidth()/2+Methods.getnewsize()*2) {
                this.getCurrentLevel().isWin = true;
                this.getCurrentLevel().goToNextLevelTime = 100;
                canvas.drawBitmap(Methods.zoomImg(LoadImage.ui.get(2),Methods.getScreenWidth(),Methods.getScreenHeight()),0,0,null);
                paint.setColor(Color.RED);
                paint.setTextSize((float)(Methods.getnewsize()*1.5));
                paint.setStrokeWidth(Methods.getScreenWidth()/2);
                canvas.drawText("Going to next Level",Methods.getnewsize()*6,Methods.getScreenHeight()/2-Methods.getnewsize()*1,paint);
            }
            this.currentLevel.GotoNextLevel(this);
            this.sh.unlockCanvasAndPost(canvas);
        }
    }

    public void EnemyCollision(){
        mario.Collision(this);
        if(this.mario.hp > 0 && !(this.mario.getNoCheckCollisionTime()>0))
        {
            for(int i=0; i<this.currentLevel.getEnemy().size(); i++)
            {
                Enemy e = this.currentLevel.getEnemy().get(i);

                if(e.is(e, Methods.getScreenWidth(), Methods.getScreenHeight()))
                {
                    if(e.MoreRectangle_CollisionWithSprite(mario,mario.getFrame()))
                    {
                        System.out.println("mark");
                        if(e.name.equals("Bloober"))
                        {
                            if(mario.y + (mario.image.getHeight()/2 - mario.getFrame().top) < e.y && !mario.landed)
                            {
                                mario.setJumptime(mario.getySpeed()/2);
                                e.Dead();
                            }
                            else
                            {
                                mario.Dead();
                            }

                        }
                        else if(e.name.equals("Turtle") || e.name.equals("shell"))
                        {
                            if(e.xSpeed != 0)
                            {
                                if(mario.y < e.y && !mario.landed)
                                {
                                    mario.setJumptime(mario.getySpeed()/2);
                                    e.ChangeState();
                                }
                                else
                                {
                                    mario.Dead();
                                }
                            }
                            else
                            {
                                e.xSpeed = Methods.getnewsize()/3;
                                if(mario.x > e.x)
                                {
                                    e.dir = 1;
                                }
                                else
                                {
                                    e.dir = 2;
                                }
                            }
                        }
                        else
                        {
                            mario.Dead();
                        }

                    }
                }
            }
        }

    }

    public void FireballCollision(){
        for(int i=0; i<mario.getFb().size(); i++)
        {
            FireBall b = mario.getFb().get(i);

            for(int j=0; j<this.currentLevel.getEnemy().size(); j++)
            {
                Enemy e = this.currentLevel.getEnemy().get(j);
                if(b.Rectangle_CollisionWithSprite(e))
                {
                    b.hp = 0;
                    if (!e.name.equals("shell")){
                        e.Dead2();
                    }
                }
            }
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
            this.EnemyCollision();
            this.FireballCollision();
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
