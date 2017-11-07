package com.dx.demi.snake.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by wentong.chen on 17/5/17.
 */

public abstract class CenterDialog extends Dialog {
    protected final String TAG = getClass().getSimpleName();

    public CenterDialog(Context context) {
        super(context);
    }

    public CenterDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CenterDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        setCanceledOnTouchOutside(true);
        setDialogBackgroudDrawable(Color.TRANSPARENT);
        setWindowParams();
        setCancelable(false);
    }

    protected void setWindowParams() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wlp.gravity = Gravity.CENTER;
        setDialogParams(wlp);
        window.setAttributes(wlp);

    }

    protected void setDialogParams(WindowManager.LayoutParams wlp) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics( dm );
        getWindow().setLayout( dm.widthPixels, getWindow().getAttributes().height);
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected void setDialogBackgroudDrawable(@ColorInt int color) {
        getWindow().setBackgroundDrawable(new ColorDrawable(color));
    }

}
