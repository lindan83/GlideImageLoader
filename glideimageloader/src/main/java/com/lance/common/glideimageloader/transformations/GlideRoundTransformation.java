package com.lance.common.glideimageloader.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import static android.R.attr.radius;

/**
 * Created by lindan on 16-10-18.
 */

public class GlideRoundTransformation extends BitmapTransformation {
    private float mRadius;
    private int mBorderColor;
    private int mBorderWidth;
    private int mBorderAlpha;

    private Paint mBorderPaint;

    /**
     * 构造函数 默认圆角半径 0
     *
     * @param context Context
     */
    public GlideRoundTransformation(Context context) {
        this(context, 0);
    }

    /**
     * 构造函数
     *
     * @param context Context
     * @param radius  圆角半径
     */
    public GlideRoundTransformation(Context context, int radius) {
        super(context);
        mRadius = radius;
    }

    /**
     * @param context     Context
     * @param radius      圆角半径
     * @param borderColor 边框颜色
     * @param borderWidth 边框大小
     * @param borderAlpha 边框透明度 0-255
     */
    public GlideRoundTransformation(Context context, int radius, int borderColor, int borderWidth, int borderAlpha) {
        super(context);
        mRadius = radius;
        mBorderAlpha = borderAlpha;
        mBorderColor = borderColor;
        mBorderWidth = borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setDither(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setAlpha(mBorderAlpha);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(mBorderWidth, mBorderWidth, source.getWidth() - mBorderWidth, source.getHeight() - mBorderWidth);
        canvas.drawRoundRect(rectF, mRadius - mBorderWidth / 2, mRadius - mBorderWidth / 2, paint);

        if (mBorderPaint != null && mBorderWidth > 0) {
            canvas.drawRoundRect(mBorderWidth / 2, mBorderWidth / 2, source.getWidth() - mBorderWidth / 2, source.getHeight() - mBorderWidth / 2, mRadius, mRadius, mBorderPaint);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}