package com.dx.demi.snake;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.dx.demi.snake.View.GameOverDialog;
import com.dx.demi.snake.View.GridMapView;
import com.dx.demi.snake.listener.GameOverListner;

public class MainActivity extends Activity implements GameOverListner{
    private static final int UP = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;

    GridMapView map ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         map = (GridMapView) findViewById(R.id.map);
         map.setGameOverListner(this);
    }

    public void 向上(View view) {
        map.setUserDirection(UP);
        Log.e("MainActivity", "向上: " );
    }

    public void 向下(View view) {
        map.setUserDirection(DOWN);
        Log.e("MainActivity", "向下: " );
    }

    public void 向左(View view) {
        map.setUserDirection(LEFT);
        Log.e("MainActivity", "向左: " );
    }

    public void 向右(View view) {
        map.setUserDirection(RIGHT);
        Log.e("MainActivity", "向右: " );
    }

    @Override
    public void gameOver(String goal) {
        GameOverDialog  dialog= (GameOverDialog) new GameOverDialog(this)
                .setTitle("Game Over")
                .setCancelView("再来一局", ContextCompat.getColor(this, R.color.red), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        map.clearDatas();
                    }
                })
                .setConfirmView("结束", ContextCompat.getColor(this, R.color.blue), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        dialog.show();
        dialog.setGoal(goal);
    }
}
