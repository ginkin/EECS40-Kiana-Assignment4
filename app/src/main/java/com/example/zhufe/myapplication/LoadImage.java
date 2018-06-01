package com.example.zhufe.myapplication;

import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.content.res.Resources;


public class LoadImage {
    public static ArrayList<Bitmap> mario = new ArrayList<Bitmap>();

    public static void Loading(Context context){
        try{
            for (int i = 1;i<=13;i++){
                mario.add(BitmapFactory.decodeStream(context.getAssets().open("mario/mario"+i+".png")));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
