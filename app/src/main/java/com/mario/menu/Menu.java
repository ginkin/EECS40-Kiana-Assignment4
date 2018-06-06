package com.mario.menu;

import android.os.Bundle;
import game.activity.GameActivity;

public class Menu extends GameActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.SetScreenToFull();
        super.onCreate(savedInstanceState);
        super.setContentView(new MenuView(this));
    }

}
