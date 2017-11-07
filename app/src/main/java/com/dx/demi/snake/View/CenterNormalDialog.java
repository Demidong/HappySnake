package com.dx.demi.snake.View;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dx.demi.snake.R;
import com.dx.demi.snake.util.AndroidUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wentong.chen on 17/5/27.
 * 居中的普通对话框
 */
public class CenterNormalDialog extends CenterDialog {
    private TextView cancelView;
    private TextView confirmView;
    @Bind(R.id.rl_dialog_rool)
    RelativeLayout rl_dialog_rool;
    View view_top_line;
    private int lineColor = ContextCompat.getColor(getContext(), R.color.text_color_gray);
    private View.OnClickListener closeListener;
    private final int dp16 = AndroidUtil.dip2px(getContext(), 16);
    private @ColorInt
    int contentColor = R.color.black;
    private boolean topLineVisible = false;
    private Rect contenMargins;
    private TextView contentTextView;
    private TextView tv_title;
    private final int MATCH = -1;
    private final int WRAP = -2;
    private final @IdRes
    int vertical_line_id = 0x1111;
    private final @IdRes
    int content_view_id = 0x2222;
    private View view_vertical_line;
    private View contentView;
    private int contentLineSpace = 3;

    public CenterNormalDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_center_nomal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (tv_title != null) {
            topLineVisible = true;
            rl_dialog_rool.addView(tv_title);
        }
        if (getContentLayoutRes() != -1) {
            contentView = getContentView();
            rl_dialog_rool.addView(contentView);
        }
        if (contentTextView != null && getContentLayoutRes() == -1) {
            contentTextView.setLineSpacing(dp2px(contentLineSpace), 1);
            contentView = contentTextView;
            rl_dialog_rool.addView(contentTextView);
        }
        if (contentView != null) {
            contentView.setId(content_view_id);
            contentView.setMinimumHeight(dp2px(100));
            setContentMargin();
        }
        if (topLineVisible) {
            if (view_top_line == null) {
                view_top_line = getHorizontalLine();
                RelativeLayout.LayoutParams rlParams = getRlParams(getSize(MATCH), getSize(dp2px(1)));
                rlParams.addRule(RelativeLayout.BELOW, R.id.tv_title);
                rl_dialog_rool.addView(view_top_line, rlParams);
            }
        }
        if (view_top_line != null) {
            view_top_line.setVisibility(topLineVisible ? View.VISIBLE : View.GONE);
        }
        if (closeListener != null) {
            addClose();
        }
        if (cancelView != null || confirmView != null) {
            View horizontalLine = getHorizontalLine();
            RelativeLayout.LayoutParams rlParams = getRlParams(getSize(MATCH), dp2px(1));
            rlParams.addRule(RelativeLayout.BELOW, content_view_id);
            rl_dialog_rool.addView(horizontalLine, rlParams);
        }
        if (confirmView != null && cancelView != null) {
            view_vertical_line = getVerticalLineView();
            RelativeLayout.LayoutParams rlParams = getRlParams(dp2px(1), dp2px(50));
            rlParams.addRule(RelativeLayout.BELOW, content_view_id);
            rlParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            rl_dialog_rool.addView(view_vertical_line, rlParams);
        }
        if (cancelView != null) {
            RelativeLayout.LayoutParams cancelParams = getRlParams(getSize(MATCH), dp2px(50));
            cancelParams.addRule(RelativeLayout.BELOW, content_view_id);
            cancelParams.addRule(RelativeLayout.LEFT_OF, vertical_line_id);
            rl_dialog_rool.addView(cancelView, cancelParams);
        }
        if (confirmView != null) {
            RelativeLayout.LayoutParams confirmParams = getRlParams(getSize(MATCH), dp2px(50));
            confirmParams.addRule(RelativeLayout.BELOW, content_view_id);
            confirmParams.addRule(RelativeLayout.RIGHT_OF, vertical_line_id);
            rl_dialog_rool.addView(confirmView, confirmParams);
        }
    }

    private void setContentMargin() {
        if (contenMargins != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) contentView.getLayoutParams();
            layoutParams.setMargins(dp2px(contenMargins.left), dp2px(contenMargins.top),
                    dp2px(contenMargins.right), dp2px(contenMargins.bottom));
        }
    }

    public View getVerticalLineView() {
        View view = new View(getContext());
        view.setBackgroundColor(lineColor);
        view.setId(vertical_line_id);
        return view;
    }

    protected int dp2px(int width) {
        return AndroidUtil.dip2px(getContext(), width);
    }

    protected @LayoutRes
    int getContentLayoutRes() {
        return -1;
    }

    protected View getContentView() {
        if (contentView == null) {
            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            RelativeLayout.LayoutParams rlParams = getRlParams(getSize(MATCH), getSize(WRAP));
            rlParams.addRule(RelativeLayout.BELOW, R.id.tv_title);
            relativeLayout.setLayoutParams(rlParams);
            relativeLayout.setGravity(Gravity.CENTER);
            LayoutInflater.from(getContext()).inflate(getContentLayoutRes(), relativeLayout, true);
            contentView = relativeLayout;
        }
        return contentView;
    }

    public CenterNormalDialog setTitle(String title) {
        if (tv_title == null) {
            tv_title = new TextView(getContext());
            tv_title.setLayoutParams(getRlParams(MATCH, dp2px(50)));
            tv_title.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            tv_title.setGravity(Gravity.CENTER);
            tv_title.setId(R.id.tv_title);
        }
        tv_title.setText(title);
        return this;
    }

    public CenterNormalDialog setContentText(CharSequence contentText) {
        return setContentText(contentText, 16, ContextCompat.getColor(getContext(), R.color.black));
    }

    public CenterNormalDialog setContentText(CharSequence contentText, int gravity) {
        return setContentText(contentText, 16, ContextCompat.getColor(getContext(), R.color.black), gravity);
    }

    public CenterNormalDialog setContentText(CharSequence contentText, int textSize, @ColorInt int textColor) {
        return setContentText(contentText, textSize, textColor, Gravity.CENTER);
    }

    public CenterNormalDialog setContentText(CharSequence contentText, int textSize, @ColorInt int textColor, int gravity) {
        if (contentTextView == null) {
            contentTextView = new TextView(getContext());
            RelativeLayout.LayoutParams rlParams = getRlParams(getSize(MATCH), getSize(WRAP));
            rlParams.addRule(RelativeLayout.BELOW, R.id.tv_title);
            contentTextView.setLayoutParams(rlParams);
            contentTextView.setMinHeight(dp2px(100));
        }
        contentTextView.setTextColor(textColor);
        contentTextView.setText(contentText);
        contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        contentTextView.setGravity(gravity);
        return this;
    }

    /**
     * 获取相对布局
     * @return
     */
    private RelativeLayout.LayoutParams getRlParams(int width, int height) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getSize(width), getSize(height));
        return layoutParams;
    }

    private int getSize(int size) {
        return size == MATCH ? RelativeLayout.LayoutParams.MATCH_PARENT : (size == WRAP ? RelativeLayout.LayoutParams.WRAP_CONTENT : size);
    }

    public CenterNormalDialog setCancelView(String text) {
        return setCancelView(text, null);
    }

    public CenterNormalDialog setCancelView(String text, DialogInterface.OnClickListener listener) {
        return setCancelView(text, ContextCompat.getColor(getContext(), R.color.text_color_middle), listener);
    }

    /**
     * @param text
     * @param listener
     */
    public CenterNormalDialog setCancelView(String text, int textColor, final DialogInterface.OnClickListener listener) {
        if (cancelView == null) {
            cancelView = new TextView(getContext());
        }
        cancelView.setGravity(Gravity.CENTER);
        cancelView.setTextColor(textColor);
        cancelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(CenterNormalDialog.this, 0);
                }
                dismiss();
            }
        });
        cancelView.setText(text);
        return this;
    }

    public CenterNormalDialog setConfirmView(String text, int textColor, final DialogInterface.OnClickListener listener) {
        if (confirmView == null) {
            confirmView = new TextView(getContext());
        }
        confirmView.setText(text);
        confirmView.setGravity(Gravity.CENTER);
        confirmView.setTextColor(textColor);
        confirmView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(CenterNormalDialog.this, 0);
                }
                dismissCenterDialog();
            }
        });
        return this;
    }

    public CenterNormalDialog setClose(View.OnClickListener closeListener) {
        if (closeListener == null) {
            closeListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            };
        }
        this.closeListener = closeListener;
        return this;
    }

    private void addClose() {
//        ImageView imageView = new ImageView(getContext());
//        imageView.setImageResource(R.drawable.close);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dp16, dp16);
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        params.rightMargin = dp16;
//        params.topMargin = dp16;
//        rl_dialog_rool.addView(imageView, params);
//        imageView.setOnClickListener(closeListener);
    }

    public CenterNormalDialog setTopLineVisible(boolean visible) {
        this.topLineVisible = visible;
        return this;
    }

    public CenterNormalDialog setContentMargin(Rect margins) {
        this.contenMargins = margins;
        if (contentView != null) {
            setContentMargin();
        }
        return this;
    }

    public CenterNormalDialog setContentLineSpace(int contentLineSpace) {
        this.contentLineSpace = contentLineSpace;
        return this;
    }

    /**
     * 可以由子类控制是否dimiss，默认点击按钮就dimiss
     */
    protected void dismissCenterDialog() {
        if (isShowing()) {
            dismiss();
        }
    }

    private View getHorizontalLine() {
        View horizontalLineView = new View(getContext());
        horizontalLineView.setBackgroundColor(lineColor);
        return horizontalLineView;
    }

    public CenterNormalDialog setWindowMargins(int left, int top, int right, int bottom) {
        this.setWindowMargins(dp2px(left), dp2px(top), dp2px(right), dp2px(bottom));
        return this;
    }

    public void setContentView(View view) {
        RelativeLayout.LayoutParams rlParams = getRlParams(getSize(MATCH), getSize(WRAP));
        rlParams.addRule(RelativeLayout.BELOW, R.id.tv_title);
        view.setLayoutParams(rlParams);
        contentView = view;
    }

    public void setRootBgColor(int color) {
        if (rl_dialog_rool == null) {
            return;
        }
        rl_dialog_rool.setBackgroundColor(color);
    }
}
