package com.mario.ingame;

import android.os.Bundle;

import game.activity.GameActivity;

public class Mario extends GameActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.SetScreenToFull();
        super.onCreate(savedInstanceState);
        super.setContentView(new MarioView(this));
    }

}
