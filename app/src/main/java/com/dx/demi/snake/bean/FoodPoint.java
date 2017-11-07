package com.dx.demi.snake.bean;

/**
 * Created by demi on 2017/10/31.
 * 网格中的食物点
 */

public class FoodPoint {
    private  float x;
    private  float y;

    public FoodPoint(float x, float y) {
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
