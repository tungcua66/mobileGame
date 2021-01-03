package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundUtil {
    private static SoundUtil instance;
    private Map<String, Sound> soundsMap;

    private SoundUtil() {
        soundsMap = new HashMap<>();
        init();
    }

    public static SoundUtil getInstance() {
        if (instance == null) {
            instance = new SoundUtil();
        }
        return instance;
    }

    public void init() {
        soundsMap.put("goal", Gdx.audio.newSound(Gdx.files.internal("sounds/goal.ogg")));
        soundsMap.put("shoot", Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.ogg")));
    }

    public void play(String sound) {
        if (soundsMap.containsKey(sound)) {
            soundsMap.get(sound).play();
        }
    }

    public void dispose() {
        for(Sound s : soundsMap.values()) {
            s.dispose();
        }
    }

}
