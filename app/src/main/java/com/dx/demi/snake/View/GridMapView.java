package com.dx.demi.snake.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dx.demi.snake.bean.BlockPoint;
import com.dx.demi.snake.bean.BodyPoint;
import com.dx.demi.snake.bean.FoodPoint;
import com.dx.demi.snake.listener.GameOverListner;
import com.dx.demi.snake.task.SnakeMoveTask;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

/**
 * Created by demi on 2017/10/31.
 * 绘制最底层网格地图背景
 */

public class GridMapView extends View {
    private static final int UP = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;
    private static final int DEFAULT_BODYNUM = 4;
    private static final int DEFAULT_NUM = 25;
    private float oldHeadX = 0;
    private float oldHeadY = 0;
    private int mWidth; //地图的宽度
    private Paint mBackgroundPaint; //画笔
    private Paint mSnakePaint; //画笔
    private int num = DEFAULT_NUM;  //网格的份数
    private int gap = 0;  //每个网格的宽度，即间隙
    private Timer timer;
    private ArrayList<BodyPoint> mBodyPoints;  //snake 的身体
    private ArrayList<BlockPoint> mBlockPoints;  //障碍物
    private int bodyNum = DEFAULT_BODYNUM;  // snake 的身体个数 初始值为4
    private BodyPoint headpoint;
    private FoodPoint foodpoint;
    private int currentDirection = DOWN; //snake当前朝向
    private int userDirection = DOWN; //用户操作方向
    private GameOverListner mGameOverListner;
    private SnakeMoveTask snakeMoveTask;

    public GridMapView(Context context) {
        super(context, null);
    }

    public GridMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSnakePaint = new Paint();
        mBackgroundPaint = new Paint();
        mBodyPoints = new ArrayList<>();
        mBlockPoints = new ArrayList<>();
        startMove(1000);
    }

    public void startMove(long seconds) {
        timer = new Timer();
        snakeMoveTask = new SnakeMoveTask(this);
        timer.schedule(snakeMoveTask, 0, seconds);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawSnake(canvas);
        drawBlockPoint(canvas);
        drawPoint(canvas);
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        mBackgroundPaint.setColor(Color.CYAN);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(4);
        for (int i = 0; i <= num; i++) {
            canvas.drawLine(0, gap * i, mWidth, gap * i, mBackgroundPaint);
            canvas.drawLine(gap * i, 0, gap * i, mWidth, mBackgroundPaint);
        }
        mBackgroundPaint.setColor(Color.RED);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(10);
        canvas.drawLine(0, 0, mWidth, 0, mBackgroundPaint);
        canvas.drawLine(0, mWidth, mWidth, mWidth, mBackgroundPaint);
        canvas.drawLine(0, 0, 0, mWidth, mBackgroundPaint);
        canvas.drawLine(mWidth, 0, mWidth, mWidth, mBackgroundPaint);

    }

    public void drawPoint(Canvas canvas) {
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(Color.BLUE);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setStrokeWidth(gap * 0.8f);
        if (foodpoint.getX() == headpoint.getX() && foodpoint.getY() == headpoint.getY()) {
            foodpoint = addFood();
            bodyNum++;
            mBodyPoints.add(mBodyPoints.get(mBodyPoints.size() - 1));
        }
        if (foodpoint != null) {
            canvas.drawPoint(foodpoint.getX() * gap, foodpoint.getY() * gap, mBackgroundPaint);
        }
    }

    //画障碍物
    public void drawBlockPoint(Canvas canvas) {
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(Color.RED);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setStrokeWidth(gap);
        if (mBlockPoints.size() == 0) {
            addBlock();
        }
        for (int i = 0; i < mBlockPoints.size(); i++) {
            BlockPoint blockpoint = mBlockPoints.get(i);
            if (blockpoint != null) {
                canvas.drawPoint(blockpoint.getX() * gap, blockpoint.getY() * gap, mBackgroundPaint);
            }
        }

    }

    public void drawSnake(final Canvas canvas) {
        drawSnakeHead(canvas);
        drawSnakeBody(canvas);
    }

    public void drawSnakeBody(final Canvas canvas) {
        mSnakePaint.setAntiAlias(true);
        mSnakePaint.setColor(Color.GREEN);
        mSnakePaint.setStyle(Paint.Style.FILL);
        if (mBodyPoints.size() > 0) {
            mBodyPoints.remove(mBodyPoints.size() - 1);
            canvas.drawCircle(oldHeadX * gap, oldHeadY * gap, gap / 2, mSnakePaint);
            for (int j = 0; j < mBodyPoints.size(); j++) {
                BodyPoint bodyPoint = mBodyPoints.get(j);
                canvas.drawCircle(bodyPoint.getX() * gap, bodyPoint.getY() * gap, gap / 2, mSnakePaint);
            }
            mBodyPoints.add(0, new BodyPoint(oldHeadX, oldHeadY));
        } else {
            for (int i = bodyNum; i > 0; i--) {
                BodyPoint bodyPoint = new BodyPoint(1, i);
                canvas.drawCircle(bodyPoint.getX() * gap, bodyPoint.getY() * gap, gap / 2, mSnakePaint);
                mBodyPoints.add(bodyPoint);
            }
        }

    }

    public void drawSnakeHead(final Canvas canvas) {
        mSnakePaint.setAntiAlias(true);
        mSnakePaint.setColor(Color.YELLOW);
        mSnakePaint.setStrokeWidth(gap);
        if (mBodyPoints.size() > 0) {
            oldHeadX = headpoint.getX();
            oldHeadY = headpoint.getY();
            if (userDirection + currentDirection == 3) {
                userDirection = currentDirection;
            }
            currentDirection = userDirection;
            switch (currentDirection) {
                case UP:
                    headpoint.setX(oldHeadX);
                    headpoint.setY(oldHeadY - 1);
                    break;
                case DOWN:
                    headpoint.setX(oldHeadX);
                    headpoint.setY(oldHeadY + 1);
                    break;
                case LEFT:
                    headpoint.setX(oldHeadX - 1);
                    headpoint.setY(oldHeadY);
                    break;
                case RIGHT:
                    headpoint.setX(oldHeadX + 1);
                    headpoint.setY(oldHeadY);
                    break;
            }
            if (isGameOver()) {
                timer.cancel();
                timer.purge();
                snakeMoveTask.cancel();
                if (mGameOverListner != null) {
                    mGameOverListner.gameOver(bodyNum + "");
                }
            }
        }
        canvas.drawPoint(headpoint.getX() * gap, headpoint.getY() * gap, mSnakePaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = wideSize < heightSize ? wideSize : heightSize;
        gap = (mWidth) / num;
        foodpoint = new FoodPoint(num / 2, num / 2);
        headpoint = new BodyPoint(1, bodyNum + 1);
        setMeasuredDimension(mWidth, mWidth);

    }

    private boolean isGameOver() {
        for (int i = 0; i < mBodyPoints.size(); i++) {
            if (headpoint.getX() == mBodyPoints.get(i).getX() && headpoint.getY() == mBodyPoints.get(i).getY()) {
                return true;
            }
        }
        for (int i = 0; i < mBlockPoints.size(); i++) {
            if (headpoint.getX() == mBlockPoints.get(i).getX() && headpoint.getY() == mBlockPoints.get(i).getY()) {
                return true;
            }
        }
        if (headpoint.getX() >= num || headpoint.getY() >= num || headpoint.getX() == 0 || headpoint.getY() == 0) {
            return true;
        }
        return false;
    }

    private FoodPoint addFood() {
        Random foodRandom = new Random();
        int foodNumX = foodRandom.nextInt(num);
        int foodNumY = foodRandom.nextInt(num);
        if (foodNumX == 0) {
            foodNumX = 1;
        }
        if (foodNumY == 0) {
            foodNumY = 1;
        }
        for (int i = 0; i < mBodyPoints.size(); i++) {
            if (foodNumX == mBodyPoints.get(i).getX() && foodNumY == mBodyPoints.get(i).getY()) {
                addFood();
            }
        }
        for (int i = 0; i < mBlockPoints.size(); i++) {
            if (foodNumX == mBlockPoints.get(i).getX() && foodNumY == mBlockPoints.get(i).getY()) {
                addFood();
            }
        }
        if (foodNumX == headpoint.getX() && foodNumY == headpoint.getY()) {
            addFood();
        }
        FoodPoint point = new FoodPoint(foodNumX, foodNumY);
        return point;
    }

    private void addBlock() {
        for (int i = 0; i < 0; i++) {
            Random foodRandom = new Random();
            int foodNumX = foodRandom.nextInt(num);
            int foodNumY = foodRandom.nextInt(num);
            if (foodNumX == 0) {
                foodNumX = 1;
            }
            if (foodNumY == 0) {
                foodNumY = 1;
            }
            BlockPoint blockpoint = new BlockPoint(foodNumX, foodNumY);
            mBlockPoints.add(blockpoint);
        }
    }

    public int getUserDirection() {
        return userDirection;
    }

    public void setUserDirection(int userDirection) {
        this.userDirection = userDirection;
        invalidate();
    }

    public void setGameOverListner(GameOverListner gameOverListner) {
        mGameOverListner = gameOverListner;
    }

    public void clearDatas() {
        bodyNum = 4;
        mBodyPoints.clear();
        foodpoint = new FoodPoint(num / 2, num / 2);
        headpoint = new BodyPoint(1, bodyNum + 1);
        userDirection = DOWN;
        currentDirection = DOWN;
        startMove(1000);
    }

}
