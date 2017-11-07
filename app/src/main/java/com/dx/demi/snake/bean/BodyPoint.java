package com.dx.demi.snake.bean;

/**
 * Created by demi on 2017/10/31.
 * 蛇身体的点
 */

public class BodyPoint {
    private  float x;
    private  float y;

    public BodyPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
