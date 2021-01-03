package com.mygdx.game.listeners;

import com.badlogic.gdx.InputAdapter;

public class TouchListener extends InputAdapter {

    private static TouchListener instance;
    private TouchInfo[] touchInfos;

    private TouchListener() {
        touchInfos = new TouchInfo[5];
        for (int i = 0; i < touchInfos.length; i++) {
            touchInfos[i] = new TouchInfo();
        }
    }

    public static TouchListener getInstance() {
        if (instance == null) {
            instance = new TouchListener();
        }
        return instance;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer < touchInfos.length) {
            touchInfos[pointer].touchDown(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer < touchInfos.length) {
            touchInfos[pointer].touchUp();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer < touchInfos.length) {
            touchInfos[pointer].touchX = screenX;
            touchInfos[pointer].touchY = screenY;
        }
        return true;
    }

    public TouchInfo[] getTouchInfos() {
        return touchInfos;
    }

    public static class TouchInfo {
        float touchX = 0;
        float touchY = 0;
        float startX = 0;
        float startY = 0;
        boolean touched = false;

        public float getTouchX() {
            return touchX;
        }


        public float getTouchY() {
            return touchY;
        }


        public float getStartX() {
            return startX;
        }

        public float getStartY() {
            return startY;
        }


        public boolean isTouched() {
            return touched;
        }

        void touchDown(float x, float y) {
            touchX = x;
            startX = x;
            touchY = y;
            startY = y;
            touched = true;
        }

        void touchUp() {
            touchX = 0;
            touchY = 0;
            startX = 0;
            startY = 0;
            touched = false;
        }

    }
}
