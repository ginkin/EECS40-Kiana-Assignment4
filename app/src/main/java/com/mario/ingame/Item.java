package com.mario.ingame;

import android.graphics.Bitmap;

import com.mario.load.LoadImage;

public class Item extends Tile {


    private int jump;

    public int Gettype() { return type; }
    public int getJump() { return jump; }
    int type;
    private int i,i2;
    private String direction;
    private Tile t;
    private boolean landed,canleft,canright;
    static int m = Methods.getnewsize()/16;
    private int movestep[] = {m,2*m,3*m,4*m,5*m,6*m,7*m,8*m};

    //type1 = Fire Flower
    //type2 = mushroom

    public Item(float x, float y, Bitmap image, int type, Tile t) {
        super(x, y, image, type);
        this.t  = t;
        this.type = type;
        direction = "R";
        this.hp =1;
    }

    @Override
    public void Jump(){
        if(jump < 16){
            y-=Methods.getnewsize()/8;
            jump+=2;
            if(type==2)this.x =t.x;
        }
        if(type==1)this.x = t.x;
    }

    public void Move(InGameView gv){
       if(this.type == 2){
            if(jump>=16){
                if(direction.equals("L")){
                    x = x - Methods.getnewsize()/8 + gv.getMario().shiftspeed;
                }else{
                    x = x + Methods.getnewsize()/8 + gv.getMario().shiftspeed;
                }
                //x+= Background.speed;
                this.Collision(gv);
            }
      }
    }
    public void Collision(InGameView gv){
            landed = false;
            for (Tile t: gv.getCurrentLevel().getTile()) {
                if(t.x > x - image.getWidth()*2 && t.x < x + image.getWidth()*2){
                    if(Rectangle_CollisionWithSprite(t)){
                        if(x > t.x - Methods.getnewsize() && x < t.x + Methods.getnewsize()&& this.y < t.y)
                        {
                            y = t.y - image.getHeight();
                            landed= true;
                            i2 = 0;
                        }

                        if(y >  t.y - image.getHeight() && x < t.x )
                        {
                            direction = "L";
                        }

                        else if(y > t.y - image.getHeight() && x > t.x )
                        {
                            direction = "R";
                        }
                    }
                }
            }
            if(!landed){
                this.y+=movestep[i2];
                while(i2<movestep.length-1){i2++;};
            }
    }
    @Override
    public void SwitchImage(){
        if(this.type == 1) {
            image = Methods.zoomImg(LoadImage.food.get(i), Methods.getnewsize(), Methods.getnewsize());
            i++;
            if (i == 3) i = 1;
        }
    }


}
