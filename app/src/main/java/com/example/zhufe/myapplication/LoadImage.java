package com.example.zhufe.myapplication;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.content.res.Resources;


public class LoadImage {
    public static ArrayList<Bitmap> mario = new ArrayList<Bitmap>();

    public static void Loading(Context context){
        mario.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher_background));
    }
}
