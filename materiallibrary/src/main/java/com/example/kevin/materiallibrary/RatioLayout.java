package com.example.kevin.materiallibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by kevin on 5/17/15.
 */
public class RatioLayout extends FrameLayout {
    
    public static final int HEIGHT = 0;
    public static final int WIDTH = 1;

    private float mRatio;
    private final int mAdjustableDimension;

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.RatioLayout,0,0);
        mRatio = a.getFloat(R.styleable.RatioLayout_ratio, 1);
        mAdjustableDimension = a.getInt(R.styleable.RatioLayout_adjustableDimension, HEIGHT);
        a.recycle();
    }

    public void setRatio(float ratio) {
        mRatio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        
        int selectedHeight = heightSize;
        int selectedWidth = widthSize;

        if (mAdjustableDimension == HEIGHT) {
            selectedHeight = getSelectedHeight(heightMode, heightSize, widthSize);
            selectedWidth = widthSize;
        } else if (mAdjustableDimension == WIDTH) {
            selectedHeight =  heightSize;
            selectedWidth = getSelectedHeight(widthMode, widthSize, heightSize);
        }

        super.onMeasure(MeasureSpec.makeMeasureSpec(selectedWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(selectedHeight, MeasureSpec.EXACTLY));
    }

    private int getSelectedHeight(int scaledMode, int scaledSize, int staticSize) {
        int desiredSize = (int)(staticSize * mRatio);
        int selectedSize;


        if ( scaledMode == MeasureSpec.EXACTLY) {
            selectedSize = scaledSize;
        } else if (scaledMode == MeasureSpec.AT_MOST){
            selectedSize = Math.min(scaledSize, desiredSize);
        } else {
            selectedSize = desiredSize;
        }
        return selectedSize;
    }

    public float getRatio() {
        return mRatio;
    }

    public int getAdjustableDimension() {
        return mAdjustableDimension;
    }
}
