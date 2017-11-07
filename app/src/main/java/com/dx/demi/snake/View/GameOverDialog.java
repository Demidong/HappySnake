package com.dx.demi.snake.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.dx.demi.snake.R;


/**
 * Created by wentong.chen on 17/5/31.
 */

public class GameOverDialog extends CenterNormalDialog {

    private TextView tv_number;

    public GameOverDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0x33, 66, 66, 66)));
        tv_number = (TextView) getContentView().findViewById(R.id.tv_number);
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.dialog_center_content;
    }

    public void setGoal(String goal){
        tv_number.setText(goal);
    }
}
