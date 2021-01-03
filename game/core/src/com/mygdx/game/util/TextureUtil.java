package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class TextureUtil {
    private static TextureUtil instance;
    private Map<String, Texture> textures;
    private BitmapFont gameFont;

    public TextureUtil() {
        textures = new HashMap<>();
        load();
    }

    public static TextureUtil getInstance() {
        if (instance == null) {
            instance = new TextureUtil();
        }
        return instance;
    }

    public void load() {
        textures.put("ball", new Texture("images/Ballon.png"));
        textures.put("goal", new Texture("images/But.bmp"));
        textures.put("intro", new Texture("images/Intro.jpg"));
        textures.put("left", new Texture("images/JoueurGauche.png"));
        textures.put("right", new Texture("images/JoueurDroite.png"));
        textures.put("menu", new Texture("images/Menu.jpg"));
        textures.put("pad", new Texture("images/Pad.png"));
        textures.put("background", new Texture("images/Terrain.png"));

        //generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        int size = (int) (Gdx.graphics.getWidth() * (0.05859375f));
        int border = (int) (Gdx.graphics.getWidth() * (0.0029296875f));
        parameter.size = size;
        parameter.color = new Color(1f, 1f, 0f, 0.75f);
        parameter.borderWidth = border;
        parameter.borderColor = new Color(0f, 0f, 0f, 0.75f);
        gameFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public Texture get(String key) {
        return textures.get(key);
    }

    public void dispose() {
        for (Texture t : textures.values()) {
            t.dispose();
        }
        gameFont.dispose();
    }

    public BitmapFont getGameFont() {
        return gameFont;
    }
}
