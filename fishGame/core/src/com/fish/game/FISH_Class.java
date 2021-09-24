package com.fish.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class FISH_Class extends Game {

    private final AssetManager assetManager = new AssetManager();


    @Override
    public void create() {
        setScreen(new StartScreen(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}

