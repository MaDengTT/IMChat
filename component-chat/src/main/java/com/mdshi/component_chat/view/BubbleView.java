package com.mdshi.component_chat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mdshi.component_chat.R;

/**
 * Created by MaDeng on 2018/9/6.
 */
public class BubbleView extends android.support.v7.widget.AppCompatTextView {
    private BubbleDrawable bubbleDrawable;
    private BubbleDrawable.ArrowLocation mArrowLocation;
    private float mArrowWidth;
    private float mArrowHeight;
    private float mAngle;
    private int mBubbleColor;
    private float mArrowPosition;
    private boolean mArrowCenter;

    public BubbleView(Context context) {
        super(context);
        initView(null);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ChatBubbleView);
            mArrowWidth = a.getDimension(R.styleable.ChatBubbleView_ChatArrowWidth, 25);
            mArrowHeight = a.getDimension(R.styleable.ChatBubbleView_ChatArrowHeight, 25);
            mAngle = a.getDimension(R.styleable.ChatBubbleView_ChatAngle, 20);
            mArrowPosition = a.getDimension(R.styleable.ChatBubbleView_ChatArrowPosition, 50);
            mBubbleColor = a.getColor(R.styleable.ChatBubbleView_ChatBubbleColor, Color.WHITE);
            int location = a.getInt(R.styleable.ChatBubbleView_ChatArrowLocation, 0);
            mArrowLocation = BubbleDrawable.ArrowLocation.mapIntToValue(location);
            mArrowCenter = a.getBoolean(R.styleable.ChatBubbleView_ChatArrowCenter, false);
            a.recycle();
        }
        setUpPadding();
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        setUp();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setUp(w,h);
        }
    }

    private void setUp() {
        setUp(getWidth(),getHeight());
    }

    private void setUp(int width, int height) {
        setUp(0,width,0,height);
    }

    private void setUp(int left, int right, int top, int bottom) {
        RectF rectF = new RectF(left, top, right, bottom);
        bubbleDrawable = new BubbleDrawable.Builder()
                .setRect(rectF)
                .setArrowLocation(mArrowLocation)
                .setAngle(mAngle)
                .setArrowHeight(mArrowHeight)
                .setArrowWidth(mArrowWidth)
                .setBubbleColor(mBubbleColor)
                .setArrowPosition(mArrowPosition)
                .setArrowCenter(mArrowCenter)
                .build();
    }

    private void setUpPadding() {
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        switch (mArrowLocation) {
            case LEFT:
                left += mArrowWidth;
                break;
            case RIGHT:
                right += mArrowWidth;
                break;
            case TOP:
                top += mArrowHeight;
            case BOTTOM:
                bottom += mArrowHeight;
                break;
        }
        setPadding(left,top,right,bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bubbleDrawable != null) {
            bubbleDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }
}
