package com.mario.load;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mario.menu.Menu;

import game.activity.GameActivity;

public class Load extends GameActivity implements Runnable {
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
        LoadImage.LoadImage(this);
        try {
            Thread.sleep(1000);
            Intent GotoMenu = new Intent(this,Menu.class);
            this.startActivity(GotoMenu);
            this.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}