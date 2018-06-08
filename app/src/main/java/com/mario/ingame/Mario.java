package com.mario.ingame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
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
    private Rect frame;
    private boolean landed,canleft,canright;
    private String jumpstate;
    private int jumptime;

    public void setState(String state) {
        this.state = state;
    }


    public Mario(float x, float y, Bitmap image) {
        super(x, y, image);
        this.status = 1;
        this.hp = 1;
        this.xSpeed = Methods.getnewsize()/4;
        this.lives = 3;
        this.switchtime = 2;
        this.state = "Rstop";
        this.frame = new Rect(0,11/16*Methods.getnewsize(),Methods.getnewsize(),2*Methods.getnewsize());
        this.jumpstate = "";
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
        if(state.equals("Rmove")&&canright) {
            if (this.x < Methods.getScreenWidth() / 2) {
                this.x += this.xSpeed;
            } else {
                if (Tile.getMovecount() < (Test.getmaparray(gv.getCurrentLevel().getLevel())[0].length-1) * Methods.getnewsize() - Methods.getScreenWidth()) {
                    Tile.Shift(gv, this.state, this.xSpeed);
                } else {
                    if (this.x < Methods.getScreenWidth() - this.image.getWidth()) {
                        this.x += this.xSpeed;
                    }
                }
            }
        }
        else if(this.state.equals("Lmove")&&canleft) {
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

    public void JumpEvent(){
        if(this.hp < 0) return;
        if(jumpstate.equals("")){
            jumptime = 15;
            ySpeed = Methods.getnewsize()/2;
            jumpstate = "jumping";
        }
    }

    public void SwitchImage() {
        if (this.hp < 0) return;

        switchtime--;
        if (this.state.equals("Rmove") || this.state.equals("Lmove")) {
            if (this.status == 1) {
                this.image = Methods.zoomImg(LoadImage.mario.get(i), Methods.getnewsize(), Methods.getnewsize()*2);
                if (this.switchtime <= 0) {
                    i++;
                    this.switchtime = 2;
                }
                if (i == 2) i = 0;
            }
        }
        else if (this.state.equals("Rstop") || this.state.equals("Lstop")) {
            if (this.status == 1) {
                this.image = Methods.zoomImg(LoadImage.mario.get(0),Methods.getnewsize(),Methods.getnewsize()*2);
            }
        }
        else if(this.jumpstate.equals("jumping"))
        {
            if(this.status == 1){
                this.image = LoadImage.mario.get(2);
            }
        }
    }

    public void Collision(InGameView gv){
        landed = false;
        canleft = true;
        canright = true;
        for (Tile t: gv.getCurrentLevel().getTile()) {
            if(t.x > x - image.getWidth()*2 && t.x < x + image.getWidth()*2){
                if(t.MoreRectangle_CollisionWithSprite(this, frame)){
                    if(x > t.x - Methods.getnewsize() && x < t.x + Methods.getnewsize()&& this.y + 11/16*Methods.getnewsize() < t.y)
                    {
                        y = t.y - image.getHeight();
                        landed= true;
                        jumpstate = "";
                        if(jumptime <=0) ySpeed = 0;
                    }

                    if(this.x > t.x - Methods.getnewsize() && this.x < t.x + Methods.getnewsize() && this.y + 11/16*Methods.getnewsize() > t.y)
                    {
                        jumptime = 0;
                        t.setJumpTime(1);
                    }

                    if(y >  t.y - image.getHeight() && x < t.x )
                    {
                        canright = false;
                    }

                    else if(y > t.y - image.getHeight() && x > t.x )
                    {
                        canleft = false;
                    }
                }
            }
        }
    }

    public void Jump(){
        if(jumptime <= 0)
        {
            if(!landed){
                y += ySpeed;
                if(ySpeed < Methods.getnewsize()/2) ySpeed++;
                jumpstate = "jumping ";
            }
        }
        else if(jumptime > 0)
        {
            this.jumpstate = "jumping";
            this.y-= ySpeed;
            if(this.ySpeed > 0) this.ySpeed--;
            jumptime --;
        }
    }

}