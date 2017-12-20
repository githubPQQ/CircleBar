package com.example.pqq.circlebar;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by pqq on 2017/12/19.
 */

public class CircleBarView extends View {
    public CircleBarView(Context context) {
        super(context);
        init(context, null);
    }

    public CircleBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private float circleArcWidth;
    private Paint mPaint;
    private Paint txtPaint;
    private int manColor, womenColor, gayColor;
    private float mWidth, mHeight;
    private RectF manRectF, womenRectF, gayRectF;
    private RectF manTtxRectF, womenTxtRectF, gayTxtRectF;
    private int manNum, womanNum, gayNum;
    private int MAX_NUM = 360;
    private Path mTextPath;

    private void init(Context context, @Nullable AttributeSet attrs) {
        circleArcWidth = 40;

        manColor = getResources().getColor(R.color.manColor);
        womenColor = getResources().getColor(R.color.womanColor);
        gayColor = getResources().getColor(R.color.gayColor);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circleArcWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        txtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        txtPaint.setStyle(Paint.Style.FILL);
        txtPaint.setTextAlign(Paint.Align.LEFT);
        txtPaint.setTextSize(40);

        manNum = 70;
        womanNum = 120;
        gayNum = 140;

        mTextPath = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        float centX = mWidth / 2;
        float centY = mHeight / 2;

        float manRadius = 200;
        float womanRadius = manRadius + circleArcWidth;
        float gayRadisu = womanRadius + circleArcWidth;
        float delta = circleArcWidth / 2;
        manRectF = new RectF(centX - manRadius, centY - manRadius, centX + manRadius, centY + manRadius);
        manTtxRectF = new RectF(centX - manRadius + delta, centY - manRadius + delta,
                centX + manRadius - delta, centY + manRadius - delta);
        womenRectF = new RectF(centX - womanRadius, centY - womanRadius, centX + womanRadius, centY + womanRadius);
        womenTxtRectF = new RectF(centX - womanRadius + delta, centY - womanRadius + delta,
                centX + womanRadius - delta, centY + womanRadius - delta);
        gayRectF = new RectF(centX - gayRadisu, centY - gayRadisu, centX + gayRadisu, centY + gayRadisu);
        gayTxtRectF = new RectF(centX - gayRadisu + delta, centY - gayRadisu + delta,
                centX + gayRadisu - delta, centY + gayRadisu - delta);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(manColor);
        txtPaint.setColor(manColor);
        canvas.drawArc(manRectF, 180, manNum * animValue, false, mPaint);
        mTextPath.reset();
        mTextPath.addArc(manTtxRectF, 180 + manNum * animValue + 10, 90);
        String manNumTxt = manNum + "";
        canvas.drawTextOnPath(manNumTxt, mTextPath, 0, manNumTxt.length(), txtPaint);

        mPaint.setColor(womenColor);
        txtPaint.setColor(womenColor);
        canvas.drawArc(womenRectF, 180, womanNum * animValue, false, mPaint);
        mTextPath.reset();
        mTextPath.addArc(womenTxtRectF, 180 + womanNum * animValue + 10, 90);
        String womanNumTxt = womanNum + "";
        canvas.drawTextOnPath(womanNumTxt, mTextPath, 0, womanNumTxt.length(), txtPaint);

        mPaint.setColor(gayColor);
        txtPaint.setColor(gayColor);
        canvas.drawArc(gayRectF, 180, gayNum * animValue, false, mPaint);
        mTextPath.reset();
        mTextPath.addArc(gayTxtRectF, 180 + gayNum * animValue + 10, 90);
        String gayNumTxt = gayNum + "";
        canvas.drawTextOnPath(gayNumTxt, mTextPath, 0, gayNumTxt.length(), txtPaint);

    }


    private ValueAnimator mAnimator;
    private float animValue = 1f;
    private boolean isAniming = false;
    private TimeInterpolator interpolator = new BounceInterpolator();

    public void startAnim() {
        if (mAnimator != null) {
            if (mAnimator.isRunning()) {
                return;
            }
            mAnimator.start();
        } else {
            mAnimator = ValueAnimator.ofFloat(0f, 1f)
                    .setDuration(1000);
            mAnimator.setInterpolator(interpolator);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAniming = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isAniming = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isAniming = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mAnimator.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isAniming)
                    startAnim();
                break;
        }
        return true;
    }
}
