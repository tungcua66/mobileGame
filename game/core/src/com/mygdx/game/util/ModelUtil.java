package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ModelUtil {
    public static List<Vector2> read(String name, float scaleX, float scaleY) {
        List<Vector2> ret = new ArrayList<>();
        Scanner sc = new Scanner(Gdx.files.internal(name).read());
        sc.useLocale(Locale.US);
        while (sc.hasNextFloat()) {
            float x = sc.nextFloat();
            float y = sc.nextFloat();
            ret.add(new Vector2(x * scaleX, y * scaleY));
        }
        return ret;
    }
}
