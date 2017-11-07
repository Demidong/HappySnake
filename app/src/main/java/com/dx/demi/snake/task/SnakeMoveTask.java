package com.dx.demi.snake.task;

import android.os.Handler;

import com.dx.demi.snake.View.GridMapView;

import java.lang.ref.WeakReference;
import java.util.TimerTask;

/**
 * Created by demi on 2017/11/1.
 */

public class SnakeMoveTask extends TimerTask {
    GridMapView  map ;
    private MyHandler handler = new MyHandler(this);
    @Override
    public void run() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(map != null){
                    map.invalidate();
                }
            }
        });
    }

    public SnakeMoveTask(GridMapView map) {
        this.map = map;
    }

    private class MyHandler extends Handler {
        WeakReference<SnakeMoveTask> mWeakReferenceActivity;

        public MyHandler(SnakeMoveTask task) {
            mWeakReferenceActivity = new WeakReference<SnakeMoveTask>(task);
        }
    }
}
