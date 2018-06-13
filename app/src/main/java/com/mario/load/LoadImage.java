package com.mario.load;

import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.content.res.Resources;

import com.mario.ingame.Methods;

import game.image.Image;


public class LoadImage {
    public static ArrayList<Bitmap> mario = new ArrayList<Bitmap>();

    public static ArrayList<Bitmap> enemy = new ArrayList<Bitmap>();

    public static ArrayList<Bitmap> coin = new ArrayList<Bitmap>();

    public static ArrayList<Bitmap> blast = new ArrayList<Bitmap>();

    public static ArrayList<Bitmap> food = new ArrayList<Bitmap>();

    public static ArrayList<Bitmap> map = new ArrayList<Bitmap>();

    public static ArrayList<Bitmap> tile = new ArrayList<Bitmap>();

    public static ArrayList<Bitmap> weapon = new ArrayList<Bitmap>();

    public static ArrayList<Bitmap> ui = new ArrayList<Bitmap>();


    public static void LoadImage(Context context) {
        try {
            for (int i = 1; i <= 13; i++) {
                mario.add(BitmapFactory.decodeStream(context.getAssets().open("mario/mario" + i + ".png")));
            }

            for (int i = 1; i <= 12; i++) {
                Bitmap m = BitmapFactory.decodeStream(context.getAssets().open("enemy/enemy" + i + ".png"));
                m = Methods.zoomImg(m,Methods.getnewsize(),Methods.getnewsize());
                enemy.add(m);
            }

            for (int i = 1; i <= 4; i++) {
                coin.add(BitmapFactory.decodeStream(context.getAssets().open("coin/coin" + i + ".png")));
            }

            for (int i = 1; i <= 3; i++) {
                blast.add(BitmapFactory.decodeStream(context.getAssets().open("blast/blast" + i + ".png")));
            }

            for (int i = 1; i <= 3; i++) {
                food.add(BitmapFactory.decodeStream(context.getAssets().open("food/food" + i + ".png")));
            }

            for (int i = 1; i <= 4; i++) {
                Bitmap m = BitmapFactory.decodeStream(context.getAssets().open("map/map" + i + ".jpg"));

                m = Image.FitTheScreenSizeImage(m, Load.ScreenWidth, Load.ScreenHeight);
                map.add(m);
            }

            for (int i = 1; i <= 35; i++) {
                tile.add(BitmapFactory.decodeStream(context.getAssets().open("tile/tile" + i + ".png")));
            }

            for (int i = 1; i <= 2; i++) {
                weapon.add(BitmapFactory.decodeStream(context.getAssets().open("weapon/weapon" + i + ".png")));
            }

            for (int i = 1; i <= 3; i++) {
                ui.add(BitmapFactory.decodeStream(context.getAssets().open("ui/ui" + i + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}