package com.dx.demi.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

/**
 * Created by demi on 2017/11/2.
 */

public class SplashActivity  extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
    public void 开始游戏(View view) {
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
    }
}
