package com.dx.demi.snake.bean;

/**
 * Created by demi on 2017/11/3.
 * 网格中的障碍点
 */

public class BlockPoint {
    private  float x;
    private  float y;

    public BlockPoint(float x, float y) {
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
