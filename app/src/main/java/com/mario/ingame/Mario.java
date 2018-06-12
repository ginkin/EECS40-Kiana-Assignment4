package com.mario.ingame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.KeyEvent;
import com.mario.load.LoadImage;

import java.util.ArrayList;

import game.sprite.Sprite;

public class Mario extends Sprite {

    private int xSpeed, ySpeed;



    public int status;
    private int lives;
    private String state;
    private int i,i2,i3;
    private int switchtime;

    public Rect getFrame() { return frame; }
    private Rect frame;
    boolean landed,canleft,canright;
    private String jumpstate;
    private int jumptime;
    int shiftspeed;

    private ArrayList<FireBall> fb = new ArrayList<FireBall>();

    public void setState(String state) { this.state = state; }
    public String getState() { return state; }
    public ArrayList<FireBall> getFireBall() { return fb; }
    public int getJumptime(){return jumptime;}
    public void setJumptime(int t){jumptime = t;}
    public int getySpeed(){return ySpeed;}

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
        shiftspeed = 0;
        if(state.equals("Rmove")&&canright) {
            if (this.x < Methods.getScreenWidth() / 2) {
                this.x += this.xSpeed;
            } else {
                if (Tile.getMovecount() < (Test.getmaparray(gv.getCurrentLevel().getLevel())[0].length-1) * Methods.getnewsize() - Methods.getScreenWidth()) {
                    shiftspeed = -xSpeed;
                    Tile.Shift(gv, this.state, this.xSpeed);
                    gv.getCurrentLevel().getBk().get(0).Shift("L");
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
                    shiftspeed = xSpeed;
                    Tile.Shift(gv, this.state, this.xSpeed);
                    gv.getCurrentLevel().getBk().get(0).Shift("R");
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
        if (this.state.equals("Rmove") &&!this.jumpstate.equals("jumping")|| this.state.equals("Lmove")&&!this.jumpstate.equals("jumping")) {
            if (this.status == 1) {
                this.image = Methods.zoomImg(LoadImage.mario.get(i), Methods.getnewsize(), Methods.getnewsize()*2);
                if (this.switchtime <= 0) {
                    i++;
                    this.switchtime = 2;
                }
                if (i == 2) i = 0;
            } else if (this.status == 2){
                this.image = Methods.zoomImg(LoadImage.mario.get(i2), Methods.getnewsize(), Methods.getnewsize()*2);
                if (this.switchtime <= 0) {
                    i2++;
                    this.switchtime = 2;
                }
                if(i2 == 6){i2 = 4;}
            } else if (this.status == 3){
                this.image = Methods.zoomImg(LoadImage.mario.get(i3), Methods.getnewsize(), Methods.getnewsize()*2);
                if (this.switchtime <= 0) {
                    i3++;
                    this.switchtime = 2;
                }
                if(i3 == 10){i3 = 8;}
            }
        }
        else if (this.state.equals("Rstop")&&!this.jumpstate.equals("jumping") || this.state.equals("Lstop")&&!this.jumpstate.equals("jumping")) {
            if (this.status == 1) {
                this.image = Methods.zoomImg(LoadImage.mario.get(0),Methods.getnewsize(),Methods.getnewsize()*2);
            }else if(this.status == 2) {
                this.image = Methods.zoomImg(LoadImage.mario.get(4),Methods.getnewsize(),Methods.getnewsize()*2);
            }else if(this.status == 3){
                this.image = Methods.zoomImg(LoadImage.mario.get(8),Methods.getnewsize(),Methods.getnewsize()*2);
            }
        }else if(this.jumpstate.equals("jumping")) {
            if (this.status == 1) {
                this.image = Methods.zoomImg(LoadImage.mario.get(2), Methods.getnewsize(), Methods.getnewsize() * 2);
            } else if (this.status == 2) {
                this.image = Methods.zoomImg(LoadImage.mario.get(6), Methods.getnewsize(), Methods.getnewsize() * 2);
            } else if (this.status == 3) {
                this.image = Methods.zoomImg(LoadImage.mario.get(10), Methods.getnewsize(), Methods.getnewsize() * 2);
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
                        if(t.getType() == 21){
                            if(t.getCollision() > 0){
                                switch(this.status){
                                    case 1:
                                        Level.item.add(new Item(t.x,t.y,Methods.zoomImg(LoadImage.food.get(0),Methods.getnewsize(),Methods.getnewsize()),2, t));
                                        break;
                                    case 2:
                                        Level.item.add(new Item(t.x,t.y,Methods.zoomImg(LoadImage.food.get(1),Methods.getnewsize(),Methods.getnewsize()),1, t));
                                        break;
                                    case 3:
                                        Level.item.add(new Item(t.x,t.y,Methods.zoomImg(LoadImage.food.get(1),Methods.getnewsize(),Methods.getnewsize()),1, t));
                                        break;
                                }
                                t.setCollision(t.getCollision()-1);
                            }
                        }
                        if(t.getType() == 37){
                            if(t.getCollision() > 0){
                                        Level.item.add(new Item(t.x,t.y,Methods.zoomImg(LoadImage.coin.get(0),Methods.getnewsize(),Methods.getnewsize()),3, t));
                                t.setCollision(t.getCollision()-1);
                            }
                        }

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


    public void Fire(){
        int firespeed;
        if(state.indexOf("R")!=-1){
            firespeed = Methods.getnewsize()/4;
        }else{
            firespeed = -Methods.getnewsize()/4;
        }
        if(fb.size()<1 && status==3){
            fb.add(new FireBall(x+ image.getWidth()/2,y+image.getHeight()/2,Methods.zoomImg(LoadImage.weapon.get(0),Methods.getnewsize()/2,Methods.getnewsize()/2),firespeed));
        }
    }

    public void Dead(){
        System.out.println("Dead");
    }
}