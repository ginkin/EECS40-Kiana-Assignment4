package com.mario.ingame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;

import com.mario.load.LoadImage;

import game.sprite.Sprite;

public class Mario extends Sprite {
    private int xspeed, yspeed;
    private int status;
    private int lives;
    private String state;
    private int i;
    private int switchtime;

    public Mario(float x, float y, Bitmap image) {
        super(x, y, image);
        this.status = 1;
        this.hp = 1;
        this.xspeed = 4;
        this.lives = 3;
        this.switchtime = 2;
        this.state = "Rstop";
    }

    public void Draw(Canvas canvas) {
        if (this.state.indexOf("L") != -1) {
            canvas.save();
            canvas.scale(-1, 1, x + image.getWidth() / 2, y + image.getHeight() / 2);
            canvas.drawBitmap(image, x, y, null);
            canvas.restore();
        }
        else
        {
            canvas.drawBitmap(image, x, y, null);
        }
    }

    public void Move() {
        if (this.hp < 0) return;
        if (this.state.equals("Lmove")) this.x -= this.xSpeed;
        if (this.state.equals("Rmove")) this.x += this.xSpeed;
    }

    public void SwitchImage() {
        if (this.hp < 0) return;

        this.switchtime--;

        if (this.state.equals("Rmove") || this.state.equals("Lmove")) {
            if (this.status == 1) {
                this.image = Methods.zoomImg(LoadImage.mario.get(i), Methods.getnewsize(), Methods.getnewsize());
                if (this.switchtime <= 0) {
                    i++;
                    this.switchtime = 2;
                }
                if (i == 2) i = 0;
            }
        }
        else if (this.state.equals("Rstop") || this.state.equals("Lstop")) {
            if (this.status == 1) {
                this.image = Methods.zoomImg(LoadImage.mario.get(0),Methods.getnewsize(),Methods.getnewsize());
            }
        }
    }

    public void onKeyDown(int keyCode, KeyEvent event)
    {
        if(this.hp < 0) return ;

        switch(keyCode)
        {
            case KeyEvent.KEYCODE_DPAD_LEFT :
                this.state = "Lmove";
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT :
                this.state = "Rmove";
                break;
        }

    }


    public void onKeyUp(int keyCode, KeyEvent event)
    {
        if(this.hp < 0) return ;

        switch(keyCode)
        {
            case KeyEvent.KEYCODE_DPAD_LEFT :
                this.state = "Lstop";
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT :
                this.state = "Rstop";
                break;
        }
    }
}