package com.mario.ingame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import com.mario.load.LoadImage;

import game.sprite.Sprite;

public class Mario extends Sprite {

    private int xSpeed, ySpeed;
    private int status;
    private int lives;
    private String state;
    private int i;
    private int switchtime;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public Mario(float x, float y, Bitmap image) {
        super(x, y, image);
        this.status = 1;
        this.hp = 1;
        this.xSpeed = Methods.getnewsize();
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

    public void Move(InGameView gv){
        if(this.hp < 0) return;
        if(state.equals("Rmove")) {
            if (this.x < Methods.getScreenWidth() / 2) {
                this.x += this.xSpeed;
            } else {
                if (Tile.getMovecount() != Test.getmaparray(gv.getCurrentLevel().getLevel())[0].length * Methods.getnewsize() - Methods.getScreenWidth()) {
                    Tile.Shift(gv, this.state, this.xSpeed);
                } else {
                    if (this.x < Methods.getScreenWidth() - this.image.getWidth()) {
                        this.x += this.xSpeed;
                    }
                }
            }
        }
        else if(this.state.equals("Lmove")) {
            if(this.x > Methods.getScreenWidth()/2) {
                this.x -=this.xSpeed;
            }
            else {
                if(Tile.getMovecount()> 0) {
                    Tile.Shift(gv, this.state, this.xSpeed);
                }
                else {
                    if(this.x > 0) {
                        this.x -=this.xSpeed;
                    }
                }
            }
        }
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

    public void TouchDown(int x){
        if(this.hp < 0) return ;

        if(x<Methods.getScreenWidth()/4){
            this.state = "Lmove";
        }else if(x>Methods.getScreenWidth()/4&&x<(Methods.getScreenWidth()/2)){
            this.state = "Rmove";
        }
    }

    public void TouchUp(int x){
        if(this.hp < 0) return ;

        if(x<Methods.getScreenWidth()/4){
            this.state = "Lstop";
        }else if(x>Methods.getScreenWidth()/4&&x<(Methods.getScreenWidth()/2)){
            this.state = "Rstop";
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