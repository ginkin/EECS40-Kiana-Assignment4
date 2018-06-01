package com.example.zhufe.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;

import game.activity.GameActivity;

public class Load extends GameActivity implements Runnable{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.SetScreenToFull();
        this.GetScreenSize();


        super.onCreate(savedInstanceState);
        setContentView(new LoadView(this));
        new Thread(this).start();
    }

    @Override
    public void run() {
        LoadImage.Loading(this);
    }
}
