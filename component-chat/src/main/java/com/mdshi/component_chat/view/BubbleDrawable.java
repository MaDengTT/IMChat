package com.mdshi.component_chat.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by MaDeng on 2018/9/6.
 */
public class BubbleDrawable extends Drawable {

    private RectF mRect;
    private Path mPath = new Path();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //箭头参数 宽、高、角度
    private float mArrowWidth;
    private float mArrowHeight;
    private float mAngle;
    private ArrowLocation mArrowLocation;

    private int bubbleColor;
    private float mArrowPosition;
    private boolean mArrowCenter;

    public BubbleDrawable(Builder builder) {
        this.mRect = builder.mRect;
        this.mAngle = builder.mAngle;
        this.mArrowHeight = builder.mArrowHeight;
        this.mArrowWidth = builder.mArrowWidth;
        this.mArrowPosition = builder.mArrowPosition;
        this.bubbleColor = builder.mBubbleColor;
        this.mArrowLocation = builder.mArrowLocation;
        this.mArrowCenter = builder.mArrowCenter;
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        setUp(canvas);
    }

    private void setUp(Canvas canvas) {
        mPaint.setColor(bubbleColor);
        setUpPath(mArrowLocation, mPath);
        canvas.drawPath(mPath,mPaint);
    }

    private void setUpPath(ArrowLocation mArrowLocation, Path mPath) {
        switch (mArrowLocation) {
            case LEFT:
                setLeftPath(mRect,mPath);
                break;
            case TOP:
                setTopPath(mRect,mPath);
                break;
            case BOTTOM:
                setBottomPath(mRect,mPath);
                break;
            case RIGHT:
                setRightPath(mRect,mPath);
                break;
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mRect.height();
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mRect.width();
    }

    private void setLeftPath(RectF rect, Path path) {

        if (mArrowCenter) {
            mArrowPosition = (rect.bottom-rect.top)/2-mArrowWidth/2;
        }

        path.moveTo(mArrowWidth+rect.left+mAngle,rect.top);
        path.lineTo(rect.width()-mAngle,rect.top);
        path.arcTo(new RectF(rect.right-mAngle,rect.top,rect.right,mAngle+rect.top),270,90);
        path.lineTo(rect.right,rect.bottom-mAngle);
        path.arcTo(new RectF(rect.right-mAngle,rect.bottom-mAngle,rect.right,rect.bottom),0,90);
        path.lineTo(rect.left+mArrowWidth+mAngle,rect.bottom);
        path.arcTo(new RectF(rect.left+mArrowWidth,rect.bottom-mAngle,mAngle+rect.left+mArrowWidth,rect.bottom),90,90);
        path.lineTo(rect.left+mArrowWidth,mArrowHeight+mArrowPosition);
        path.lineTo(rect.left,mArrowPosition+mArrowHeight/2);
        path.lineTo(rect.left+mArrowWidth,mArrowPosition);
        path.lineTo(rect.left+mArrowWidth,rect.top+mAngle);
        path.arcTo(new RectF(rect.left+mArrowWidth,rect.top,mAngle+rect.left+mArrowWidth,mAngle+rect.top),180,90);
        path.close();
    }
    private void setRightPath(RectF rect, Path path) {

        if (mArrowCenter) {
            mArrowPosition = (rect.bottom-rect.top)/2-mArrowWidth/2;
        }

        path.moveTo(rect.left + mAngle, rect.top);
        path.lineTo(rect.width() - mAngle - mArrowWidth, rect.top);
        path.arcTo(new RectF(rect.right - mAngle - mArrowWidth,
                rect.top, rect.right - mArrowWidth, mAngle + rect.top), 270, 90);
        path.lineTo(rect.right - mArrowWidth, mArrowPosition);
        path.lineTo(rect.right, mArrowPosition + mArrowHeight / 2);
        path.lineTo(rect.right - mArrowWidth, mArrowPosition + mArrowHeight);
        path.lineTo(rect.right - mArrowWidth, rect.bottom - mAngle);

        path.arcTo(new RectF(rect.right - mAngle - mArrowWidth, rect.bottom - mAngle,
                rect.right - mArrowWidth, rect.bottom), 0, 90);
        path.lineTo(rect.left + mArrowWidth, rect.bottom);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle,
                mAngle + rect.left, rect.bottom), 90, 90);

        path.arcTo(new RectF(rect.left, rect.top, mAngle
                + rect.left, mAngle + rect.top), 180, 90);
        path.close();
    }

    private void setTopPath(RectF rect, Path path) {

        if (mArrowCenter) {
            mArrowPosition = (rect.right - rect.left) / 2 - mArrowWidth / 2;
        }

        path.moveTo(rect.left + Math.min(mArrowPosition, mAngle), rect.top + mArrowHeight);
        path.lineTo(rect.left + mArrowPosition, rect.top + mArrowHeight);
        path.lineTo(rect.left + mArrowWidth / 2 + mArrowPosition, rect.top);
        path.lineTo(rect.left + mArrowWidth + mArrowPosition, rect.top + mArrowHeight);
        path.lineTo(rect.right - mAngle, rect.top + mArrowHeight);

        path.arcTo(new RectF(rect.right - mAngle,
                rect.top + mArrowHeight, rect.right, mAngle + rect.top + mArrowHeight), 270, 90);
        path.lineTo(rect.right, rect.bottom - mAngle);

        path.arcTo(new RectF(rect.right - mAngle, rect.bottom - mAngle,
                rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mAngle, rect.bottom);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle,
                mAngle + rect.left, rect.bottom), 90, 90);
        path.lineTo(rect.left, rect.top + mArrowHeight + mAngle);
        path.arcTo(new RectF(rect.left, rect.top + mArrowHeight, mAngle
                + rect.left, mAngle + rect.top + mArrowHeight), 180, 90);
        path.close();
    }

    private void setBottomPath(RectF rect, Path path) {
        if (mArrowCenter) {
            mArrowPosition = (rect.right - rect.left) / 2 - mArrowWidth / 2;
        }
        path.moveTo(rect.left + mAngle, rect.top);
        path.lineTo(rect.width() - mAngle, rect.top);
        path.arcTo(new RectF(rect.right - mAngle,
                rect.top, rect.right, mAngle + rect.top), 270, 90);

        path.lineTo(rect.right, rect.bottom - mArrowHeight - mAngle);
        path.arcTo(new RectF(rect.right - mAngle, rect.bottom - mAngle - mArrowHeight,
                rect.right, rect.bottom - mArrowHeight), 0, 90);

        path.lineTo(rect.left + mArrowWidth + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mArrowPosition + mArrowWidth / 2, rect.bottom);
        path.lineTo(rect.left + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + Math.min(mAngle, mArrowPosition), rect.bottom - mArrowHeight);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle - mArrowHeight,
                mAngle + rect.left, rect.bottom - mArrowHeight), 90, 90);
        path.lineTo(rect.left, rect.top + mAngle);
        path.arcTo(new RectF(rect.left, rect.top, mAngle
                + rect.left, mAngle + rect.top), 180, 90);
        path.close();
    }
    public static class Builder{
        private RectF mRect;
        private float mArrowWidth = 25;
        private float mArrowHeight = 25;
        private float mAngle = 20;
        private float mArrowPosition = 50;
        private int mBubbleColor = Color.WHITE;
        private ArrowLocation mArrowLocation = ArrowLocation.LEFT;
        private boolean mArrowCenter;

        public Builder setRect(RectF mRect) {
            this.mRect = mRect;
            return this;
        }

        public Builder setArrowWidth(float mArrowWidth) {
            this.mArrowWidth = mArrowWidth;
            return this;
        }

        public Builder setArrowHeight(float mArrowHeight) {
            this.mArrowHeight = mArrowHeight;
            return this;
        }

        public Builder setAngle(float mAngle) {
            this.mAngle = mAngle;
            return this;
        }

        public Builder setArrowPosition(float mArrowPosition) {
            this.mArrowPosition = mArrowPosition;
            return this;
        }

        public Builder setBubbleColor(int bubberColor) {
            this.mBubbleColor = bubberColor;
            return this;
        }

        public Builder setArrowLocation(ArrowLocation mArrowLocation) {
            this.mArrowLocation = mArrowLocation;
            return this;
        }

        public Builder setArrowCenter(boolean mArrowCenter) {
            this.mArrowCenter = mArrowCenter;
            return this;
        }

        public BubbleDrawable build() {
            if (mRect == null) {
                throw new IllegalArgumentException("BubbleDrawable Rect can not be null");
            }
            return new BubbleDrawable(this);
        }
    }

    //箭头方向
    public enum ArrowLocation{
        LEFT(0x00),
        RIGHT(0x01),
        TOP(0x02),
        BOTTOM(0x03);

        private int mValue;
        ArrowLocation(int value){
            this.mValue = value;
        }

        public static ArrowLocation mapIntToValue(int stateInt) {
            for (ArrowLocation value : ArrowLocation.values()) {
                if (stateInt == value.getInValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        private int getInValue() {
            return mValue;
        }

        private static ArrowLocation getDefault() {
            return LEFT;
        }
    }
}
