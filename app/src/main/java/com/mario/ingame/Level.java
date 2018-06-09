package com.mario.ingame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.mario.load.LoadImage;

import java.util.ArrayList;

import game.sprite.Sprite;

public class Level {

        private int Level;

        private ArrayList<Tile> tile = new ArrayList<Tile>();

        private ArrayList<Background> bk = new ArrayList<Background>();
        static ArrayList<Sprite> item = new ArrayList<Sprite>();
        private Bitmap backimg;
        private static Canvas canvas;
        public ArrayList<Tile> getTile()
    {
        return tile;
    }
        public int getLevel() {
        return Level;
    }

        public ArrayList<Background> getBk() {
            return bk;
        }

        public Level(int level)
        {
            Level = level;

            switch(level)
            {
                case 1:

                    this.CreatTile(Test.Level1_map0);
                    backimg = Bitmap.createBitmap(Methods.getScreenWidth()*Test.getcolumn(1)/20,Methods.getScreenHeight(), Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(backimg);
                    canvas.drawColor(Color.WHITE);
                    for (int i = 0; i < 15 ; i++) {
                        canvas.drawBitmap(LoadImage.map.get(level), Methods.getScreenWidth()*i,   0 ,null);
                    }
                    this.bk.add(new Background(0,0,backimg));
                    break;
            }

        }

        public void CreatTile(int maparray[][])
        {
            for(int i=0; i<maparray.length; i++)
            {
                for(int j=0; j<maparray[i].length; j++)
                {
                    if(maparray[i][j] > 0)
                    {
                        //Tile newtile = new Tile(j*16, i*16,GetImage(maparray[i][j]),maparray[i][j]);
                        Tile newtile = new Tile(j*Methods.getnewsize(), i*Methods.getnewsize(), Methods.zoomImg(GetImage(maparray[i][j]),Methods.getnewsize(),Methods.getnewsize()),maparray[i][j]);
                        tile.add(newtile);
                    }
                }
            }
        }


        public Bitmap GetImage(int index)
        {
            if(index == 130)  return LoadImage.tile.get(0);
            if(index == 146)  return LoadImage.tile.get(1);
            if(index == 11 )  return LoadImage.tile.get(2);
            if(index == 12 )  return LoadImage.tile.get(3);
            if(index == 27 )  return LoadImage.tile.get(4);
            if(index == 28 )  return LoadImage.tile.get(5);
            if(index == 37 )  return LoadImage.tile.get(6);
            if(index == 21 )  return LoadImage.tile.get(8);
            if(index == 15 )  return LoadImage.tile.get(12);
            if(index == 31 )  return LoadImage.tile.get(13);
            if(index == 47 )  return LoadImage.tile.get(14);
            if(index == 77 )  return LoadImage.tile.get(15);
            if(index == 93 )  return LoadImage.tile.get(16);
            if(index == 78 )  return LoadImage.tile.get(17);
            if(index == 94 )  return LoadImage.tile.get(18);
            if(index == 10 )  return LoadImage.tile.get(19);
            if(index == 131 ) return LoadImage.tile.get(20);
            if(index == 145 ) return LoadImage.tile.get(21);
            if(index == 129 ) return LoadImage.tile.get(22);
            if(index == 133 ) return LoadImage.tile.get(23);
            if(index == 134 ) return LoadImage.tile.get(24);
            if(index == 135 ) return LoadImage.tile.get(25);
            if(index == 149 ) return LoadImage.tile.get(26);
            if(index == 150 ) return LoadImage.tile.get(27);
            if(index == 151 ) return LoadImage.tile.get(28);
            if(index == 147 ) return LoadImage.tile.get(29);
            if(index == 152 ) return LoadImage.tile.get(30);
            if(index == 17 )  return LoadImage.tile.get(31);
            if(index == 18 )  return LoadImage.tile.get(32);
            if(index == 19 )  return LoadImage.tile.get(33);
            if(index == 20 )  return LoadImage.tile.get(34);
            return null;
        }
}
