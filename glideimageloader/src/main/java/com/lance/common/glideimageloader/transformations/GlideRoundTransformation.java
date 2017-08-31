package com.lance.common.glideimageloader.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by lindan on 16-10-18.
 */

public class GlideRoundTransformation extends BitmapTransformation {
    private float radius;
    private int borderColor;
    private int borderWidth;
    private int borderAlpha;

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
        this.radius = radius;
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
        this.radius = radius;
        this.borderAlpha = borderAlpha;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setDither(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(this.borderColor);
        mBorderPaint.setAlpha(this.borderAlpha);
        mBorderPaint.setStrokeWidth(this.borderWidth);
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
        RectF rectF = new RectF(borderWidth, borderWidth, source.getWidth() - borderWidth, source.getHeight() - borderWidth);
        canvas.drawRoundRect(rectF, radius - borderWidth / 2, radius - borderWidth / 2, paint);

        if (mBorderPaint != null && borderWidth > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(borderWidth / 2, borderWidth / 2, source.getWidth() - borderWidth / 2, source.getHeight() - borderWidth / 2, radius, radius, mBorderPaint);
            } else {
                canvas.drawRoundRect(new RectF(borderWidth / 2, borderWidth / 2, source.getWidth() - borderWidth / 2, source.getHeight() - borderWidth / 2), radius, radius, mBorderPaint);
            }
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(android.R.attr.radius);
    }
}