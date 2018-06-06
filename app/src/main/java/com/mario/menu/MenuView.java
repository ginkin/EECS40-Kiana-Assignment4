package com.mario.menu;

import game.button.GameButton;
import game.view.GameView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.mario.R;
import com.mario.ingame.InGame;
import com.mario.load.Load;
import com.mario.load.LoadImage;

public class MenuView extends GameView implements Runnable
{
    private GameButton Start,Options,Quit;

    private int textSize = 80;

    public MenuView(Context context)
    {
        super(context);
        Start = new GameButton((Load.ScreenWidth - 100)/2 , Load.ScreenHeight/2 - 40, textSize,"Start", context, R.raw.button);
        Quit  = new GameButton(Start.x, Start.y   + 300, textSize, "Exit",     context, R.raw.button);
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
            paint.setColor(Color.RED);
            canvas.drawBitmap(LoadImage.map.get(0), 0, 0, null);
            Start.Draw(canvas, paint);
            Quit.Draw(canvas, paint);
            this.sh.unlockCanvasAndPost(canvas);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(this.Start.OnTouch(event.getX(), event.getY())) {
            Intent startgame = new Intent(this.getContext(),InGame.class);
            this.getContext().startActivity(startgame);
        }
        else if(this.Quit.OnTouch(event.getX(), event.getY()))
        {
            Activity a = (Activity)this.getContext();
            a.finish();
            this.flag = false;
            System.exit(0);
        }
        return super.onTouchEvent(event);
    }




    @Override
    public void run()
    {
        while(flag)
        {
            this.Draw();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}