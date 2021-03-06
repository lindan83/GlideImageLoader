package com.lance.common.glideimageloader.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by lindan on 16-10-18.
 * <p>
 * 圆形GlideBitmapTransform，支持边框大小、颜色、透明度
 */

public class GlideCircleTransformation extends BitmapTransformation {
    private int borderColor;
    private int borderWidth;
    private int borderAlpha;

    private Paint borderPaint;

    public GlideCircleTransformation(Context context) {
        super(context);
    }

    /**
     * @param context     Context
     * @param borderColor 边框颜色
     * @param borderWidth 边框大小
     * @param borderAlpha 边框透明度 0-255
     */
    public GlideCircleTransformation(Context context, int borderColor, int borderWidth, int borderAlpha) {
        super(context);
        this.borderAlpha = borderAlpha;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setDither(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(this.borderColor);
        borderPaint.setAlpha(this.borderAlpha);
        borderPaint.setStrokeWidth(this.borderWidth);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight()) - (borderWidth / 2);
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r - borderWidth, paint);
        //画边框
        if (borderPaint != null && borderWidth > 0) {
            float borderRadius = r - borderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, borderPaint);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}